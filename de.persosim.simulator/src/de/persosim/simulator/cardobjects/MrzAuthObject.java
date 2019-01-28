package de.persosim.simulator.cardobjects;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.globaltester.cryptoprovider.Crypto;

import de.persosim.simulator.documents.Mrz;
import de.persosim.simulator.documents.MrzTD1;

/**
 * This authentication object constructs its returned password from a given MRZ.
 * 
 * @author mboonk
 * 
 */
//IMPL MrzAuthObject, relies on TD1 format
public class MrzAuthObject extends PasswordAuthObject {
	
	protected String mrz;
		
	public MrzAuthObject(AuthObjectIdentifier identifier, String mrz)
			throws NoSuchAlgorithmException,
			IOException {
		super(identifier, constructMrzPassword(mrz), "MRZ");
		this.mrz = mrz;
	}

	/**
	 * This method returns the input String used to compute the common secret
	 * from the MRZ
	 * 
	 * @return the input String used to compute the common secret from the MRZ
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	private static byte[] constructMrzPassword(String machineReadableZone)
			throws NoSuchAlgorithmException, IOException {
		StringBuilder sb;
		Mrz mrz;

		sb = new StringBuilder();
		mrz = new MrzTD1(machineReadableZone);

		/* document number */
		sb.append(mrz.getDocumentNumber());
		/* document number check digit */
		sb.append(mrz.getDocumentNumberCd());

		/* date of birth */
		sb.append(mrz.getDateOfBirth());
		/* date of birth check digit */
		sb.append(mrz.getDateOfBirthCd());

		/* Date of expiry */
		sb.append(mrz.getDateOfExpiry());
		/* Date of expiry check digit */
		sb.append(mrz.getDateOfExpiryCd());

		
		MessageDigest md = MessageDigest.getInstance("SHA-1", Crypto.getCryptoProvider());
		return md.digest(sb.toString().getBytes("UTF-8"));
	}

	public String getMrz() {
		return mrz;
	}
	
}
