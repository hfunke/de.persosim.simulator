package de.persosim.simulator.protocols.auxVerification;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.globaltester.logging.InfoSource;

import de.persosim.simulator.apdu.ResponseApdu;
import de.persosim.simulator.cardobjects.AuxDataObject;
import de.persosim.simulator.cardobjects.CardObject;
import de.persosim.simulator.cardobjects.CardObjectUtils;
import de.persosim.simulator.cardobjects.MasterFile;
import de.persosim.simulator.cardobjects.OidIdentifier;
import de.persosim.simulator.exception.AccessDeniedException;
import de.persosim.simulator.exception.VerificationException;
import de.persosim.simulator.platform.CardStateAccessor;
import de.persosim.simulator.platform.Iso7816;
import de.persosim.simulator.platform.PlatformUtil;
import de.persosim.simulator.processing.ProcessingData;
import de.persosim.simulator.protocols.GenericOid;
import de.persosim.simulator.protocols.Oid;
import de.persosim.simulator.protocols.Protocol;
import de.persosim.simulator.protocols.SecInfoPublicity;
import de.persosim.simulator.protocols.ca.ChipAuthenticationMechanism;
import de.persosim.simulator.protocols.ta.AuthenticatedAuxiliaryData;
import de.persosim.simulator.protocols.ta.TerminalAuthenticationMechanism;
import de.persosim.simulator.secstatus.SecMechanism;
import de.persosim.simulator.secstatus.SecStatus.SecContext;
import de.persosim.simulator.tlv.TlvConstants;
import de.persosim.simulator.tlv.TlvDataObject;
import de.persosim.simulator.tlv.TlvDataObjectContainer;

public class AuxProtocol implements Protocol, Iso7816, InfoSource, TlvConstants {
	CardStateAccessor cardState;
	
	@Override
	public String getProtocolName() {
		return "AUX";
	}

	@Override
	public void setCardStateAccessor(CardStateAccessor cardState) {
		this.cardState = cardState;
	}

	@Override
	public Collection<TlvDataObject> getSecInfos(SecInfoPublicity publicity, MasterFile mf) {
		return Collections.emptySet();
	}
	
	@Override
	public void process(ProcessingData processingData) {
		if ((processingData.getCommandApdu().getCla() == (byte) 0x80) && (processingData.getCommandApdu().getIns() == INS_20_VERIFY)){
			//check for ca
			HashSet<Class<? extends SecMechanism>> previousMechanisms = new HashSet<>();
			previousMechanisms.add(ChipAuthenticationMechanism.class);
			Collection<SecMechanism> currentMechanisms = cardState.getCurrentMechanisms(SecContext.APPLICATION, previousMechanisms);
			if (currentMechanisms.size() == 0){
				ResponseApdu resp = new ResponseApdu(PlatformUtil.SW_4982_SECURITY_STATUS_NOT_SATISFIED);
				processingData.updateResponseAPDU(this, "The AUX protocol can not be executed without a previous CA", resp);
				/* there is nothing more to be done here */
				return;
			}
			
			TlvDataObjectContainer commandData = processingData.getCommandApdu().getCommandDataObjectContainer();
			if (commandData.containsTlvDataObject(TlvConstants.TAG_06)){
				try{
					Oid oid = new GenericOid(commandData.getTlvDataObject(TlvConstants.TAG_06).getValueField());
					processOid(processingData, oid);
					
					ResponseApdu resp = new ResponseApdu(Iso7816.SW_9000_NO_ERROR);
					processingData.updateResponseAPDU(this, "Auxiliary data verification successfull", resp);
					/* there is nothing more to be done here */
					return;
					
				} catch (IllegalArgumentException e){
					ResponseApdu resp = new ResponseApdu(PlatformUtil.SW_4A80_WRONG_DATA);
					processingData.updateResponseAPDU(this, "The given OID is not valid", resp);
					/* there is nothing more to be done here */
					return;
				} catch (FileNotFoundException e) {
					ResponseApdu resp = new ResponseApdu(PlatformUtil.SW_4A88_REFERENCE_DATA_NOT_FOUND);
					processingData.updateResponseAPDU(this, "The referenced data could not be found", resp);
					/* there is nothing more to be done here */
					return;
				} catch (VerificationException e) {
					ResponseApdu resp = new ResponseApdu(SW_6300_AUTHENTICATION_FAILED);
					processingData.updateResponseAPDU(this, "Auxiliary data verification failed", resp);
					/* there is nothing more to be done here */
					return;
				} catch (AccessDeniedException e) {
					ResponseApdu resp = new ResponseApdu(SW_6982_SECURITY_STATUS_NOT_SATISFIED);
					processingData.updateResponseAPDU(this, "Auxiliary data verification failed - Access to data not allowed", resp);
					/* there is nothing more to be done here */
					return;
				}
			} else {
				ResponseApdu resp = new ResponseApdu(PlatformUtil.SW_4A80_WRONG_DATA);
				processingData.updateResponseAPDU(this, "Missing an OID", resp);
				/* there is nothing more to be done here */
				return;
			}
		}
	}

	private void processOid(ProcessingData processingData, Oid oid) throws VerificationException, FileNotFoundException, AccessDeniedException {
		
		//get necessary information stored in TA
		Collection<Class<? extends SecMechanism>> previousMechanisms = new HashSet<>();
		previousMechanisms.add(TerminalAuthenticationMechanism.class);
		Collection<SecMechanism> currentMechanisms = cardState.getCurrentMechanisms(SecContext.APPLICATION, previousMechanisms);
		TerminalAuthenticationMechanism taMechanism = null;
		if (currentMechanisms.size() > 0){
			taMechanism = (TerminalAuthenticationMechanism) currentMechanisms.toArray()[0];
			Collection<AuthenticatedAuxiliaryData> auxDataFromTa = taMechanism.getAuxiliaryData();
	
			CardObject auxDataCandidate = CardObjectUtils.getSpecificChild(cardState.getMasterFile(), new OidIdentifier(oid));
			AuxDataObject auxDataObject = null;
			
			if (auxDataCandidate instanceof AuxDataObject){
				auxDataObject = (AuxDataObject) auxDataCandidate;
			} else {
				throw new FileNotFoundException("The card object using the OID " + oid.toString() + " is not a AUX data object");
			}
			
			if (auxDataFromTa != null){
				AuthenticatedAuxiliaryData expectedAuxData = null;
				
				for (AuthenticatedAuxiliaryData current : auxDataFromTa){
					if(oid.equals(current.getObjectIdentifier())) {
						expectedAuxData = current;
						break;
					}
					
				}
				
				if(expectedAuxData == null) {
					throw new FileNotFoundException("No auxiliary data was stored during TA matching the provided OID");
				} else {
					if (auxDataObject.verify(expectedAuxData)){
						return;
					}
				}
			} else {
				throw new FileNotFoundException("No auxiliary data was stored during TA");
			}
			throw new VerificationException("no auxiliary data verified successfully");
		}
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getIDString() {
		return "AUX protocol";
	}
	
	@Override
	public boolean isMoveToStackRequested() {
		return false;
	}

}
