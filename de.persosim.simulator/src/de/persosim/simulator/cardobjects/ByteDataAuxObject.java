package de.persosim.simulator.cardobjects;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import de.persosim.simulator.exception.AccessDeniedException;
import de.persosim.simulator.protocols.RoleOid;
import de.persosim.simulator.protocols.auxVerification.AuxOid;
import de.persosim.simulator.protocols.ta.AuthenticatedAuxiliaryData;
import de.persosim.simulator.protocols.ta.Authorization;
import de.persosim.simulator.protocols.ta.TerminalAuthenticationMechanism;
import de.persosim.simulator.protocols.ta.TerminalType;
import de.persosim.simulator.secstatus.EffectiveAuthorizationMechanism;
import de.persosim.simulator.secstatus.SecMechanism;
import de.persosim.simulator.secstatus.SecStatus.SecContext;
import de.persosim.simulator.utils.Utils;

public class ByteDataAuxObject extends AuxDataObject {
	byte [] data;
	
	public ByteDataAuxObject(OidIdentifier identifier, byte [] data) {
		super(identifier);
		this.data = Arrays.copyOf(data, data.length);
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	@Override
	public boolean verify(AuthenticatedAuxiliaryData current) throws AccessDeniedException {
		//get necessary information stored in TA
		Collection<Class<? extends SecMechanism>> previousMechanisms = new HashSet<>();
		previousMechanisms.add(TerminalAuthenticationMechanism.class);
		previousMechanisms.add(EffectiveAuthorizationMechanism.class);
		Collection<SecMechanism> currentMechanisms = securityStatus.getCurrentMechanisms(SecContext.APPLICATION, previousMechanisms);
		
		TerminalAuthenticationMechanism taMechanism = null;
		EffectiveAuthorizationMechanism authMechanism = null;
		
		if (currentMechanisms.size() >= 2){
			
			for(SecMechanism secmechanism:currentMechanisms) {
				if(secmechanism instanceof TerminalAuthenticationMechanism) {
					taMechanism = (TerminalAuthenticationMechanism) secmechanism;
				}
				
				if(secmechanism instanceof EffectiveAuthorizationMechanism) {
					authMechanism = (EffectiveAuthorizationMechanism) secmechanism;
				}
			}
			
			if((taMechanism == null) || (authMechanism == null)) {
				throw new AccessDeniedException("Community ID verification not allowed");
			}
			
			if (taMechanism.getTerminalType().equals(TerminalType.ST)) {
				throw new AccessDeniedException("Community ID verification not allowed");
			}
			
			if(taMechanism.getTerminalType().equals(TerminalType.AT)) {
				Authorization auth = authMechanism.getAuthorization(RoleOid.id_AT);
				
				if(auth == null) {
					throw new AccessDeniedException("Community ID verification not allowed");
				}
				
				if(!auth.getAuthorization().getBit(1)) {
					throw new AccessDeniedException("Community ID verification not allowed");
				}
			}
			
			if (identifier.getOid().equals(AuxOid.id_CommunityID)){
				return Utils.arrayHasPrefix(data, current.getDiscretionaryData());
			}
			
		}
		return false;
	}
}
