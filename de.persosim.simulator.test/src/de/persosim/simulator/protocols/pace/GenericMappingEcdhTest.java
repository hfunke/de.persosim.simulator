package de.persosim.simulator.protocols.pace;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import org.junit.Test;

import de.persosim.simulator.crypto.DomainParameterSetEcdh;
import de.persosim.simulator.crypto.StandardizedDomainParameters;
import de.persosim.simulator.test.PersoSimTestCase;
import de.persosim.simulator.utils.HexString;

public class GenericMappingEcdhTest extends PersoSimTestCase {
	
	/**
	 * Positive test case: perform mapping of domain parameters based on values from valid PACE test run.
	 */
	@Test
	public void testPerformGenericMappingOfDomainParameters() {
		DomainParameterSetEcdh domainParameterSetUnMapped = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		GenericMappingEcdh mapping = new GenericMappingEcdh();
		
		byte[] nonceS = HexString.toByteArray("FA587945E9FE2AEB417DF0ADF951B7CBD9D5E476F8F6EF1B701C59C56B180204");
		byte[] secretOfKeyAgreement = HexString.toByteArray("04326C2CE38AC366142735AFA4317A24BDE8F12AFAEE1575CE9756E3A8849F9AEF30103CF5396CBA2F4678572988513CFC0F0CBE116644A5B9E8C6B229E0C9E2FB");
		
		BigInteger gxExpected = new BigInteger(1, HexString.toByteArray("7D05B6FEB64B5A197BE4D6482F8C81C918095FB4CA771F838473866137916CAC"));
		BigInteger gyExpected = new BigInteger(1, HexString.toByteArray("37297DCFDE4A2D65E3AACE40E7C12EA052958662803F6B8A2D9F7F7F30D7B5C3"));
		
		ECPoint pointExpected = new ECPoint(gxExpected, gyExpected);
		
		DomainParameterSetEcdh domainParametersReceived = (DomainParameterSetEcdh) mapping.performMappingOfDomainParameters(domainParameterSetUnMapped, nonceS, secretOfKeyAgreement);
		
		assertEquals("curve", domainParameterSetUnMapped.getCurve(), domainParametersReceived.getCurve());
		assertEquals("G", pointExpected, domainParametersReceived.getGenerator());
		assertEquals("group order", domainParameterSetUnMapped.getOrder(), domainParametersReceived.getOrder());
		assertEquals("cofactor", domainParameterSetUnMapped.getCofactor(), domainParametersReceived.getCofactor());
	}
	
	/**
	 * Negative test case: perform mapping of domain parameters based on values from valid PACE test run but wrong nonce S.
	 */
	@Test
	public void testPerformGenericMappingOfDomainParametersWrongNonceS() {
		DomainParameterSetEcdh domainParameterSetUnMapped = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		GenericMappingEcdh mapping = new GenericMappingEcdh();
		
		byte[] nonceS = HexString.toByteArray("FA587945E9FE2AEB417DF0ADF951B7CBD9D5E476F8F6EF1B701C59C56B180205"); // wrong nonce
		byte[] secretOfKeyAgreement = HexString.toByteArray("04326C2CE38AC366142735AFA4317A24BDE8F12AFAEE1575CE9756E3A8849F9AEF30103CF5396CBA2F4678572988513CFC0F0CBE116644A5B9E8C6B229E0C9E2FB");
		
		BigInteger gxExpected = new BigInteger(1, HexString.toByteArray("7D05B6FEB64B5A197BE4D6482F8C81C918095FB4CA771F838473866137916CAC"));
		BigInteger gyExpected = new BigInteger(1, HexString.toByteArray("37297DCFDE4A2D65E3AACE40E7C12EA052958662803F6B8A2D9F7F7F30D7B5C3"));
		
		ECPoint pointExpected = new ECPoint(gxExpected, gyExpected);
		
		DomainParameterSetEcdh domainParametersReceived = (DomainParameterSetEcdh) mapping.performMappingOfDomainParameters(domainParameterSetUnMapped, nonceS, secretOfKeyAgreement);
		
		assertEquals("curve", domainParameterSetUnMapped.getCurve(), domainParametersReceived.getCurve());
		assertNotEquals("G", pointExpected, domainParametersReceived.getGenerator());
		assertEquals("group order", domainParameterSetUnMapped.getOrder(), domainParametersReceived.getOrder());
		assertEquals("cofactor", domainParameterSetUnMapped.getCofactor(), domainParametersReceived.getCofactor());
	}
	
	/**
	 * Negative test case: perform mapping of domain parameters based on values from valid PACE test run but with wrong secret of key agreement.
	 */
	@Test
	public void testPerformGenericMappingOfDomainParametersWrongSecretOfKeyAgreement() {
		DomainParameterSetEcdh domainParameterSetUnMapped = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		GenericMappingEcdh mapping = new GenericMappingEcdh();
		
		byte[] nonceS = HexString.toByteArray("FA587945E9FE2AEB417DF0ADF951B7CBD9D5E476F8F6EF1B701C59C56B180204");
		byte[] secretOfKeyAgreement = HexString.toByteArray("04326C2CE38AC366142735AFA4317A24BDE8F12AFAEE1575CE9756E3A8849F9AEF30103CF5396CBA2F4678572988513CFC0F0CBE116644A5B9E8C6B229E0C9E2FC"); // wrong secret of key agreement
		
		BigInteger gxExpected = new BigInteger(1, HexString.toByteArray("7D05B6FEB64B5A197BE4D6482F8C81C918095FB4CA771F838473866137916CAC"));
		BigInteger gyExpected = new BigInteger(1, HexString.toByteArray("37297DCFDE4A2D65E3AACE40E7C12EA052958662803F6B8A2D9F7F7F30D7B5C3"));
		
		ECPoint pointExpected = new ECPoint(gxExpected, gyExpected);
		
		DomainParameterSetEcdh domainParametersReceived = (DomainParameterSetEcdh) mapping.performMappingOfDomainParameters(domainParameterSetUnMapped, nonceS, secretOfKeyAgreement);
		
		assertEquals("curve", domainParameterSetUnMapped.getCurve(), domainParametersReceived.getCurve());
		assertNotEquals("G", pointExpected, domainParametersReceived.getGenerator());
		assertEquals("group order", domainParameterSetUnMapped.getOrder(), domainParametersReceived.getOrder());
		assertEquals("cofactor", domainParameterSetUnMapped.getCofactor(), domainParametersReceived.getCofactor());
	}
	
	/**
	 * Positive test case: perform key agreement as part of mapping function based on values from valid PACE test run.
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testPerformKeyAgreement() throws InvalidKeySpecException, NoSuchAlgorithmException {
		DomainParameterSetEcdh domainParameterSetUnMapped = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		GenericMappingEcdh mapping = new GenericMappingEcdh();
		
		byte[] privateKeyDataPicc = HexString.toByteArray("7FC3DE0EDE951E6181392527612FF2A50D4E6C6FE00F7A92E66CB3D7B7D23044");
		byte[] publicKeyDataPcd = HexString.toByteArray("0424EF5B5C5D5F085783357C34C01660C6A062005BA1E347EB5E890DC34A305085161950814AE4D7BF20137D5C425E039CCC250835D69E8FEE92E302F468F39394");
		
		ECPrivateKey ecPrivKeyPicc = domainParameterSetUnMapped.reconstructPrivateKey(privateKeyDataPicc);
		ECPublicKey ecPubKeyPcd = domainParameterSetUnMapped.reconstructPublicKey(publicKeyDataPcd);
		
		byte[] commonSecretExpected = HexString.toByteArray("04326C2CE38AC366142735AFA4317A24BDE8F12AFAEE1575CE9756E3A8849F9AEF30103CF5396CBA2F4678572988513CFC0F0CBE116644A5B9E8C6B229E0C9E2FB");
		
		byte[] commonSecretReceived = mapping.performKeyAgreement(domainParameterSetUnMapped, ecPrivKeyPicc, ecPubKeyPcd);
		
		assertArrayEquals("common mapping secret", commonSecretExpected, commonSecretReceived);
	}
	
	/**
	 * Negative test case: perform key agreement as part of mapping function based on values from valid PACE test run but with wrong PCD public key (on curve but not matching).
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testPerformKeyAgreement_wrongPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
		DomainParameterSetEcdh domainParameterSetUnMapped = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		GenericMappingEcdh mapping = new GenericMappingEcdh();
		
		byte[] privateKeyDataPicc = HexString.toByteArray("7FC3DE0EDE951E6181392527612FF2A50D4E6C6FE00F7A92E66CB3D7B7D23044");
		byte[] publicKeyDataPcd = HexString.toByteArray("047307AED59C716B4328E974EC2460104E013E93B9826A47CB9DB8A104F493F685094776A48D5B6746058D2B0FB206B69E4AA16E8E893BB4908285482BC4B82232");
		
		ECPrivateKey ecPrivKeyPicc = domainParameterSetUnMapped.reconstructPrivateKey(privateKeyDataPicc);
		ECPublicKey ecPubKeyPcd = domainParameterSetUnMapped.reconstructPublicKey(publicKeyDataPcd);
		
		byte[] commonSecretExpected = HexString.toByteArray("04326C2CE38AC366142735AFA4317A24BDE8F12AFAEE1575CE9756E3A8849F9AEF30103CF5396CBA2F4678572988513CFC0F0CBE116644A5B9E8C6B229E0C9E2FB");
		
		byte[] commonSecretReceived = mapping.performKeyAgreement(domainParameterSetUnMapped, ecPrivKeyPicc, ecPubKeyPcd);
		
		assertFalse("common mapping secret", Arrays.equals(commonSecretExpected, commonSecretReceived));
	}
	
}
