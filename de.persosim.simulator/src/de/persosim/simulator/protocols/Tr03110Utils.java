package de.persosim.simulator.protocols;

import java.security.Key;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import de.persosim.simulator.Activator;
import de.persosim.simulator.crypto.DomainParameterSet;
import de.persosim.simulator.crypto.certificates.CvPublicKey;
import de.persosim.simulator.exception.CertificateNotParseableException;
import de.persosim.simulator.exception.NotParseableException;
import de.persosim.simulator.tlv.ConstructedTlvDataObject;
import de.persosim.simulator.tlv.PrimitiveTlvDataObject;
import de.persosim.simulator.tlv.TlvConstants;
import de.persosim.simulator.tlv.TlvDataObjectContainer;
import de.persosim.simulator.tlv.TlvTag;
import de.persosim.simulator.utils.HexString;

/** 
 * 
 * This class contains methods unique to the TR-03110 specification.
 * @author mboonk
 *
 */
public class Tr03110Utils implements TlvConstants {
	public static final int ACCESS_RIGHTS_AT_CAN_ALLOWED_BIT = 4;
	
	static private List<Tr03110UtilsProvider> providers = new ArrayList<>();
	
	static private ServiceTracker<Tr03110UtilsProvider, Tr03110UtilsProvider> serviceTracker;
	
	static {
		if (Activator.getContext() != null){
			ServiceTrackerCustomizer<Tr03110UtilsProvider, Tr03110UtilsProvider> customizer = new ServiceTrackerCustomizer<Tr03110UtilsProvider, Tr03110UtilsProvider>() {
				
				@Override
				public void removedService(
						ServiceReference<Tr03110UtilsProvider> reference,
						Tr03110UtilsProvider service) {
					providers.remove(service);
				}
				
				@Override
				public void modifiedService(
						ServiceReference<Tr03110UtilsProvider> reference,
						Tr03110UtilsProvider service) {
					//Nothing to be done
				}
				
				@Override
				public Tr03110UtilsProvider addingService(
						ServiceReference<Tr03110UtilsProvider> reference) {
					Tr03110UtilsProvider provider = Activator.getContext().getService(reference); 
					providers.add(provider);
					return provider;
				}
			};
			
			serviceTracker = new ServiceTracker<>(Activator.getContext(), Tr03110UtilsProvider.class, customizer);
			serviceTracker.open();
					
		} else {
			BasicLogger.log(Tr03110Utils.class, "No OSGi context is available, no additional TR03110 functionalities are supported", LogLevel.INFO);
		}
		providers.add(new Tr03110UtilsDefaultProvider());

	}
	
	/*
	 * This is a utils class only and does not need to be instantiated
	 */
	private Tr03110Utils() {}
	
	
	
	/**
	 * This method parses a public key encoded within a CV certificate
	 * @param publicKeyData the encoding of a public key
	 * @return a key matching the encoding from a CV certificate
	 */
	public static CvPublicKey parseCvPublicKey(ConstructedTlvDataObject publicKeyData) {
		for (Tr03110UtilsProvider provider : providers) {
			try {
				CvPublicKey key = provider.parseCvPublicKey(publicKeyData);
				if (key != null) {
					return key;
				}
			} catch (Exception e) {
				BasicLogger.logException(Tr03110Utils.class, e, LogLevel.WARN);
			}
		}
		BasicLogger.log(Tr03110Utils.class, "Public Key data could not be parsed.", LogLevel.INFO);
		return null;
	}
	
	/**
	 * This method encodes a public key as described in TR03110 Part 3 Appendix D.3
	 * @param oid the {@link Oid} to store in the encoded Key
	 * @param pk the {@link PublicKey} to encode
	 * @param includeConditionalObjects whether to store conditional data
	 * @return tlv data containing the encoded key
	 */
	public static TlvDataObjectContainer encodePublicKey(Oid oid, PublicKey pk, boolean includeConditionalObjects) {
		for (Tr03110UtilsProvider provider : providers) {
			try {
				TlvDataObjectContainer key = provider.encodePublicKey(oid, pk, includeConditionalObjects);
				if (key != null) {
					return key;
				}
			} catch (Exception e) {
				BasicLogger.logException(Tr03110Utils.class, e, LogLevel.WARN);
			}
		}
		BasicLogger.log(Tr03110Utils.class, "Public Key data could not be encoded.", LogLevel.INFO);
		return null;
	}
	
	/**
	 * This method constructs the input data used to compute the authentication token needed e.g. by Pace's Mutual Authenticate or CA's General Authenticate.
	 * @param publicKey the ephemeral public key to be inserted
	 * @param domParamSet the domain parameters matching the provided public key
	 * @return the authentication token input data
	 */
	public static TlvDataObjectContainer buildAuthenticationTokenInput(PublicKey publicKey, DomainParameterSet domParamSet, Oid oidInput) {
		/* construct authentication token object based on OID and public key */
		byte[] ephemeralPublicKeyByteArray = domParamSet.encodePublicKey(publicKey);
		
		TlvTag pubKeyTag = domParamSet.getAuthenticationTokenPublicKeyTag();
		
		PrimitiveTlvDataObject primitive06 = new PrimitiveTlvDataObject(TAG_06, oidInput.toByteArray());
		PrimitiveTlvDataObject primitive84 = new PrimitiveTlvDataObject(pubKeyTag, ephemeralPublicKeyByteArray);
		ConstructedTlvDataObject constructed7F49 = new ConstructedTlvDataObject(TAG_7F49);
		constructed7F49.addTlvDataObject(primitive06);
		constructed7F49.addTlvDataObject(primitive84);
		TlvDataObjectContainer authenticationTokenInput = new TlvDataObjectContainer();
		authenticationTokenInput.addTlvDataObject(constructed7F49);
		
		return authenticationTokenInput;
	}
	
	/**
	 * This method extracts the domain parameter information from DH and EC public and private keys.
	 * @param key a DH/EC public/private key
	 * @return the extracted domain parameter information
	 */
	public static DomainParameterSet getDomainParameterSetFromKey(Key key) {
		for(Tr03110UtilsProvider provider : providers){
			DomainParameterSet domainParameters = provider.getDomainParameterSetFromKey(key);
			if (domainParameters != null){
				return domainParameters;
			}
		}
		throw new IllegalArgumentException("unexpected key format");
	}
	
	/**
	 * Reads the a date encoded in 6 bytes as described in TR-03110 v2.10 D.2.1.3.
	 * @param dateData as described in TR-03110 V2.10 part 3, D
	 * @param lenient use lenient option for parsing dates via java.util.Calendar.
	 * @return a {@link Date} object containing the encoded date
	 * @throws CertificateNotParseableException
	 */
	// IMPL check possible code duplication/overlap with Utils.getDate method
	public static Date parseDate(byte [] dateData, boolean lenient) throws NotParseableException {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setLenient(lenient);
		
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		if (dateData.length == 6){
			for(byte currentByte : dateData){
				if (currentByte < 0 || currentByte > 9){
					throw new NotParseableException("The date could not be parsed, it contains illegal digit " + currentByte);
				}
			}
			calendar.set(dateData[0] * 10 + dateData[1] + 2000, dateData[2] * 10 + dateData[3] - 1, dateData[4] * 10 + dateData[5], 0, 0, 0);
		} else {
			throw new NotParseableException("The date could not be parsed, its length was incorrect");
		}
		return calendar.getTime();
	}

	/**
	 * Reads the a date encoded in 6 bytes as described in TR-03110 v2.10 D.2.1.3.
	 * @param dateData as described in TR-03110 V2.10 part 3, D
	 * @return a {@link Date} object containing the encoded date
	 * @throws CertificateNotParseableException
	 */
	// IMPL check possible code duplication/overlap with Utils.getDate method
	public static Date parseDate(byte [] dateData) throws NotParseableException {
		return parseDate(dateData, true);
	}
	
	/**
	 * Encodes a date as described in TR-03110 v2.10 D.2.1.3.
	 * 
	 * @param date
	 *            the date to encode, only the year, month and day components
	 *            are used
	 * @return the 6 byte long BCD encoding
	 */
	// IMPL check possible code duplication/overlap with Utils.encodeDate method
	public static byte[] encodeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		DecimalFormat formatter = new DecimalFormat("00");
		String tempDate = ""
				+ formatter.format((calendar.get(Calendar.YEAR) - 2000) / 10)
				+ formatter.format((calendar.get(Calendar.YEAR) - 2000) % 10)
				+ formatter.format((calendar.get(Calendar.MONTH) + 1) / 10)
				+ formatter.format((calendar.get(Calendar.MONTH) + 1) % 10)
				+ formatter.format(calendar.get(Calendar.DAY_OF_MONTH) / 10)
				+ formatter.format(calendar.get(Calendar.DAY_OF_MONTH) % 10);
		return HexString.toByteArray(tempDate);
	}

	/**
	 * Allows to register specific Tr03110UtilsProviders to by used.
	 * <br/>
	 * Implemented as public method to allow usage in non-OSGi tests
	 * @param tr03110UtilsProvider
	 */
	public static void addTr03110UtilsProvider(Tr03110UtilsProvider tr03110UtilsProvider) {
		providers.add(tr03110UtilsProvider);
	}
	
}
