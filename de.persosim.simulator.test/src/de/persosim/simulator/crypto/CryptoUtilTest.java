package de.persosim.simulator.crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.KeySpec;

import org.junit.Test;

import de.persosim.simulator.test.PersoSimTestCase;
import de.persosim.simulator.tlv.Asn1;
import de.persosim.simulator.utils.HexString;
import de.persosim.simulator.utils.Utils;

/**
 * @author slutters
 *
 */
public class CryptoUtilTest extends PersoSimTestCase {

	/**
	 * Positive test case: multiply EC point with scalar (with performance optimization).
	 */
	@Test
	public void testScalarPointMultiplicationEllipticCurveBigIntegerECPointBigInteger() {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		
		// values originate from successful PACE test run and are checked against the implementation of scalar point multiplication by Bouncy Castle
		BigInteger px        = new BigInteger(1, HexString.toByteArray("8BD2AEB9CB7E57CB2C4B482FFC81B7AFB9DE27E1E3BD23C23A4453BD9ACE3262"));
		BigInteger py        = new BigInteger(1, HexString.toByteArray("547EF835C3DAC4FD97F8461A14611DC9C27745132DED8E545C1D54C72F046997"));
		BigInteger mult      = new BigInteger(1, HexString.toByteArray("FA587945E9FE2AEB417DF0ADF951B7CBD9D5E476F8F6EF1B701C59C56B180204"));
		
		BigInteger expectedX = new BigInteger(1, HexString.toByteArray("874F6277A3C0621DFE106B47DA1242C0F7155905B3A1847D59A64494CF5CE0B9"));
		BigInteger expectedY = new BigInteger(1, HexString.toByteArray("3E4EBE21E2E131D5A740037635F823DFB859AC58305CEEC9992CEE437F60A730"));
		
		ECPoint ecPointP = new ECPoint(px, py);
		
		ECPoint expectedEcPoint = new ECPoint(expectedX, expectedY);
		
		ECPoint receivedEcPoint = CryptoUtil.scalarPointMultiplication(domParamsEcdh.getCurve(), domParamsEcdh.getOrder(), ecPointP, mult);
		
		assertEquals("mult x", expectedEcPoint.getAffineX(), receivedEcPoint.getAffineX());
		assertEquals("mult y", expectedEcPoint.getAffineY(), receivedEcPoint.getAffineY());
	}
	
	/**
	 * Positive test case: multiply EC point with scalar (without performance optimization).
	 */
	@Test
	public void testScalarPointMultiplicationEllipticCurveECPointBigInteger() {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		
		// values originate from successful PACE test run and are checked against the implementation of scalar point multiplication by Bouncy Castle
		BigInteger px        = new BigInteger(1, HexString.toByteArray("8BD2AEB9CB7E57CB2C4B482FFC81B7AFB9DE27E1E3BD23C23A4453BD9ACE3262"));
		BigInteger py        = new BigInteger(1, HexString.toByteArray("547EF835C3DAC4FD97F8461A14611DC9C27745132DED8E545C1D54C72F046997"));
		BigInteger mult      = new BigInteger(1, HexString.toByteArray("FA587945E9FE2AEB417DF0ADF951B7CBD9D5E476F8F6EF1B701C59C56B180204"));
		
		BigInteger expectedX = new BigInteger(1, HexString.toByteArray("874F6277A3C0621DFE106B47DA1242C0F7155905B3A1847D59A64494CF5CE0B9"));
		BigInteger expectedY = new BigInteger(1, HexString.toByteArray("3E4EBE21E2E131D5A740037635F823DFB859AC58305CEEC9992CEE437F60A730"));
		
		ECPoint ecPointP = new ECPoint(px, py);
		
		ECPoint expectedEcPoint = new ECPoint(expectedX, expectedY);
		
		ECPoint receivedEcPoint = CryptoUtil.scalarPointMultiplication(domParamsEcdh.getCurve(), ecPointP, mult);
		
		assertEquals("mult x", expectedEcPoint.getAffineX(), receivedEcPoint.getAffineX());
		assertEquals("mult y", expectedEcPoint.getAffineY(), receivedEcPoint.getAffineY());
	}
	
	/**
	 * Positive test case: add two EC points.
	 */
	@Test
	public void testAddPoint() {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		
		// values originate from successful PACE test run and are checked against the implementation of point addition by Bouncy Castle
		BigInteger qx        = new BigInteger(1, HexString.toByteArray("3EB50DD69CA2E6B0BE8D4C3089DD55F1657273CFC5728012CA346BAE0AF9A7D8"));
		BigInteger qy        = new BigInteger(1, HexString.toByteArray("829F38EB7E87D468BD9A63CEE4CB15DA25D6EAFE1008FD889D3D6B0F5FB04C02"));
		BigInteger px        = new BigInteger(1, HexString.toByteArray("2100DFDFFE149B14E2D9C0BCD71F50B1A96BC6778531FAE793C3AB1BCCF3FD68"));
		BigInteger py        = new BigInteger(1, HexString.toByteArray("4DBEF9BE48DEB0183AA6AB8BD2B51D7870E050993BEBE823A6AA976AC3088611"));
		
		BigInteger expectedX = new BigInteger(1, HexString.toByteArray("5EED472701BF5F15C19A7BA97323DC6BD8DF35C331D78B9EAA57A8864BA00D9E"));
		BigInteger expectedY = new BigInteger(1, HexString.toByteArray("2DD711DA7FF82CA05D0C67D01AFD94210512A908EBF85ADE326F487E05D4C390"));
		
		ECPoint ecPointQ = new ECPoint(qx, qy);
		ECPoint ecPointP = new ECPoint(px, py);
		
		ECPoint expectedEcPoint = new ECPoint(expectedX, expectedY);
		
		ECPoint receivedEcPoint = CryptoUtil.addPoint(domParamsEcdh.getCurve(), ecPointQ, ecPointP);
		
		assertEquals("add x", expectedEcPoint.getAffineX(), receivedEcPoint.getAffineX());
		assertEquals("add y", expectedEcPoint.getAffineY(), receivedEcPoint.getAffineY());
	}
	
	/**
	 * Positive test case: add two EC points and check commutativity of parameters.
	 */
	@Test
	public void testAddPoint_CommutativityOfParameters() {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		
		// values originate from successful PACE test run and are checked against the implementation of point addition by Bouncy Castle
		BigInteger qx        = new BigInteger(1, HexString.toByteArray("3EB50DD69CA2E6B0BE8D4C3089DD55F1657273CFC5728012CA346BAE0AF9A7D8"));
		BigInteger qy        = new BigInteger(1, HexString.toByteArray("829F38EB7E87D468BD9A63CEE4CB15DA25D6EAFE1008FD889D3D6B0F5FB04C02"));
		BigInteger px        = new BigInteger(1, HexString.toByteArray("2100DFDFFE149B14E2D9C0BCD71F50B1A96BC6778531FAE793C3AB1BCCF3FD68"));
		BigInteger py        = new BigInteger(1, HexString.toByteArray("4DBEF9BE48DEB0183AA6AB8BD2B51D7870E050993BEBE823A6AA976AC3088611"));
		
		ECPoint ecPointQ = new ECPoint(qx, qy);
		ECPoint ecPointP = new ECPoint(px, py);
		
		ECPoint receivedEcPointQP = CryptoUtil.addPoint(domParamsEcdh.getCurve(), ecPointQ, ecPointP);
		ECPoint receivedEcPointPQ = CryptoUtil.addPoint(domParamsEcdh.getCurve(), ecPointP, ecPointQ);
		
		assertEquals("add x", receivedEcPointQP.getAffineX(), receivedEcPointPQ.getAffineX());
		assertEquals("add y", receivedEcPointQP.getAffineY(), receivedEcPointPQ.getAffineY());
	}
	
	/**
	 * Positive test case: double a single EC point.
	 */
	@Test
	public void testDoublePoint() {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		
		// values originate from successful PACE test run and are checked against the implementation of point doubling by Bouncy Castle
		BigInteger px        = new BigInteger(1, HexString.toByteArray("3EB50DD69CA2E6B0BE8D4C3089DD55F1657273CFC5728012CA346BAE0AF9A7D8"));
		BigInteger py        = new BigInteger(1, HexString.toByteArray("829F38EB7E87D468BD9A63CEE4CB15DA25D6EAFE1008FD889D3D6B0F5FB04C02"));
		
		BigInteger expectedX = new BigInteger(1, HexString.toByteArray("1E2F557EC531A3E47859A238F8CCDB3F646FC6533A02F05359B2200C41BC7F79"));
		BigInteger expectedY = new BigInteger(1, HexString.toByteArray("754D80BA26DD2B844F2C02D26CD693A9F28BBB0B318493C3EB84A20086BFD2B0"));
		
		ECPoint ecPointP = new ECPoint(px, py);
		
		ECPoint expectedEcPoint = new ECPoint(expectedX, expectedY);
		
		ECPoint receivedEcPoint = CryptoUtil.doublePoint(domParamsEcdh.getCurve(), ecPointP);
		
		assertEquals("double x", expectedEcPoint.getAffineX(), receivedEcPoint.getAffineX());
		assertEquals("double y", expectedEcPoint.getAffineY(), receivedEcPoint.getAffineY());
	}
	
	/**
	 * Positive test case: encode point with same length coordinates.
	 */
	@Test
	public void testEncode() {
		
		String xStr = "CA2E6B0BE8D4C39A";
		String yStr = "DC6BD8DF35C331D7";
		int refLength = 8;
		byte[] exp = HexString.toByteArray("04" + xStr + yStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_UNCOMPRESSED));
	}
	
	/**
	 * Positive test case: encode point with shorter x coordinate.
	 */
	@Test
	public void testEncode_shortX() {
		
		String xStr = "2E6B0BE8D4C39A";
		String yStr = "DC6BD8DF35C331D7";
		int refLength = 8;
		byte[] exp = HexString.toByteArray("0400" + xStr + yStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_UNCOMPRESSED));
	}
	
	/**
	 * Positive test case: encode point with shorter y coordinate.
	 */
	@Test
	public void testEncode_shortY() {
		
		String xStr = "CA2E6B0BE8D4C39A";
		String yStr = "D8DF35C331D7";
		int refLength = 8;
		byte[] exp = HexString.toByteArray("04" + xStr + "0000" + yStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_UNCOMPRESSED));
	}
	
	/**
	 * Positive test case: encode point with shorter x and y coordinates.
	 */
	@Test
	public void testEncode_shortXY() {
		
		String xStr = "2E6B0BE8D4C39A";
		String yStr = "6BD8DF35C331D7";
		int refLength = 8;
		byte[] exp = HexString.toByteArray("0400" + xStr + "00" + yStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_UNCOMPRESSED));
	}
	
	/**
	 * Negative test case: x coordinate larger than reference length.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEncode_largeX() {
		
		String xStr = "ADCA2E6B0BE8D4C39A";
		String yStr = "DC6BD8DF35C331D7";
		int refLength = 8;

		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_UNCOMPRESSED);
	}
	
	/**
	 * Negative test case: y coordinate larger than reference length.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEncode_largeY() {
		
		String xStr = "CA2E6B0BE8D4C39A";
		String yStr = "ADDC6BD8DF35C331D7";
		int refLength = 8;

		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_UNCOMPRESSED);
	}
	
	/**
	 * Positive test case: perform compressed point encoding for y-coordinate's LSB=1.
	 */
	@Test
	public void testEncode_Compressed1() {
		
		String xStr = "42";
		String yStr = "43";
		int refLength = 1;
		byte[] exp = HexString.toByteArray("03" + xStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_COMPRESSED));
	}
	
	/**
	 * Positive test case: perform compressed point encoding for y-coordinate's LSB=0.
	 */
	@Test
	public void testEncode_Compressed0() {
		
		String xStr = "42";
		String yStr = "42";
		int refLength = 1;
		byte[] exp = HexString.toByteArray("02" + xStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_COMPRESSED));
	}
	
	/**
	 * Positive test case: perform hybrid point encoding for y-coordinate's LSB=1.
	 */
	@Test
	public void testEncode_Hybrid1() {
		
		String xStr = "42";
		String yStr = "43";
		int refLength = 1;
		byte[] exp = HexString.toByteArray("07" + xStr + yStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_HYBRID));
	}
	
	/**
	 * Positive test case: perform hybrid point encoding for y-coordinate's LSB=0.
	 */
	@Test
	public void testEncode_Hybrid0() {
		
		String xStr = "42";
		String yStr = "42";
		int refLength = 1;
		byte[] exp = HexString.toByteArray("06" + xStr + yStr);
		
		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		assertArrayEquals(exp, CryptoUtil.encode(ecPoint, refLength, CryptoUtil.ENCODING_HYBRID));
	}
	
	/**
	 * Negative test case: unknown/illegal encoding type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEncode_IllegalEncoding() {
		
		String xStr = "42";
		String yStr = "42";
		int refLength = 1;

		BigInteger x = new BigInteger(1, HexString.toByteArray(xStr));
		BigInteger y = new BigInteger(1, HexString.toByteArray(yStr));

		ECPoint ecPoint = new ECPoint(x, y);
		
		CryptoUtil.encode(ecPoint, refLength, Byte.MAX_VALUE);
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with components' length matching l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_BothComponentsMatching() {
		byte[] uncompressedSignature = HexString
				.toByteArray("300A 0203AABBCC 0203DDEEFF");
		byte[] expectedResult = HexString
				.toByteArray("AABBCCDDEEFF");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 3));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with first component's length shorter than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_FirstComponentShorter() {
		byte[] uncompressedSignature = HexString
				.toByteArray("3009 0202AABB 0203DDEEFF");
		byte[] expectedResult = HexString
				.toByteArray("00AABBDDEEFF");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 3));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with second component's length shorter than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_SecondComponentShorter() {
		byte[] uncompressedSignature = HexString
				.toByteArray("3009 0203AABBCC 0202DDEE");
		byte[] expectedResult = HexString
				.toByteArray("AABBCC00DDEE");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 3));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with both component's length longer than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_BothComponentsLonger() {
		byte[] uncompressedSignature = HexString
				.toByteArray("300A 020300AABB 020300CCDD");
		byte[] expectedResult = HexString
				.toByteArray("AABBCCDD");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 2));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with both component's length longer than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_FirstComponentLonger() {
		byte[] uncompressedSignature = HexString
				.toByteArray("3009 020300AABB 0202CCDD");
		byte[] expectedResult = HexString
				.toByteArray("AABBCCDD");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 2));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with second component's length longer than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_SecondComponentLonger() {
		byte[] uncompressedSignature = HexString
				.toByteArray("3009 0202AABB 020300CCDD");
		byte[] expectedResult = HexString
				.toByteArray("AABBCCDD");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 2));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with first component's length longer than l and second components's length shorter than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_FirstComponentLongerSecondShorter() {
		byte[] uncompressedSignature = HexString
				.toByteArray("3008 020300AABB 0201CC");
		byte[] expectedResult = HexString
				.toByteArray("AABB00CC");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 2));
	}
	
	/**
	 * Positive test case: compress a valid ASN.1 signature with second component's length longer than l and first components's length shorter than l
	 */
	@Test
	public void testCompressAsn1SignatureStructure_SecondComponentLongerFirstShorter() {
		byte[] uncompressedSignature = HexString
				.toByteArray("3008 0201AA 020300BBCC");
		byte[] expectedResult = HexString
				.toByteArray("00AABBCC");

		assertArrayEquals(expectedResult, CryptoUtil.compressAsn1SignatureStructure(uncompressedSignature, 2));
	}
	
	/**
	 * Positive test case: encode a raw representation of a valid signature into
	 * its ASN.1 representation
	 */
	@Test
	public void testRestoreAsn1SignatureStructure() {
		byte[] signature = HexString
				.toByteArray("24B41D25993A96A9CEB75CFB6ACAB615DE0A6124CBE41779B8ECDD804B8A7DB70A09DBFFC745865253BCAEFBBC228AB70C253B1B7E9CD92AB25EA934A8257E1F");
		byte[] expectedResult = HexString
				.toByteArray("3044022024B41D25993A96A9CEB75CFB6ACAB615DE0A6124CBE41779B8ECDD804B8A7DB702200A09DBFFC745865253BCAEFBBC228AB70C253B1B7E9CD92AB25EA934A8257E1F");

		assertArrayEquals(expectedResult, CryptoUtil
				.restoreAsn1SignatureStructure(signature).toByteArray());
	}

	/**
	 * Positive test case: encode a raw representation of a signature consisting
	 * of negative numbers to its ASN.1 representation
	 */
	@Test
	public void testRestoreAsn1SignatureStructureRSAreNegative() {
		byte[] testData = new BigInteger("-20000000000000", 10).toByteArray();

		byte[] signature = Utils.concatByteArrays(testData, testData);
		byte[] expectedResult = Utils.concatByteArrays(
				new byte []{Asn1.SEQUENCE},
				Utils.toUnsignedByteArray((byte) ((testData.length + 3) * 2)),
				new byte []{Asn1.INTEGER},
				Utils.toUnsignedByteArray((byte) (testData.length + 1)),
				HexString.toByteArray("00"), testData,
				new byte []{Asn1.INTEGER},
				Utils.toUnsignedByteArray((byte) (testData.length + 1)),
				HexString.toByteArray("00"), testData);

		assertArrayEquals(expectedResult, CryptoUtil
				.restoreAsn1SignatureStructure(signature).toByteArray());
	}
	
	/**
	 * Negative test case: encode a raw representation of a valid signature into
	 * its ASN.1 representation with uneven input byte length
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRestoreAsn1SignatureStructure_UnevenInputLength() {
		byte[] signature = HexString.toByteArray("AABBCC");

		CryptoUtil.restoreAsn1SignatureStructure(signature);
	}
	
	/**
	 * Positive test case: recreate key pair from minimum representation of
	 * public and private parts.
	 */
	@Test
	public void testKeyConversion() {
		
		byte[] pubKeyBytes = HexString.toByteArray("047D1EA24146C3ADAC11143E7267B4E3EC572534828DB54904877B8D6EFDC5C13123A9E955890447643735C4F0AB9093FAA0C96DEFA1CE9079DA0B3C43BE6A0255");
		byte[] privKeyBytes = HexString.toByteArray("1183F16814B3947D01DAED7F8D236769F5ABD8020FFF53C5E5FE86A8ABAB02D2");
		
		KeyPair keyPair = CryptoUtil.reconstructKeyPair(13, pubKeyBytes, privKeyBytes);
		
		assertTrue(keyPair != null);
	}
	
	/**
	 * Positive test case: get cipher name as string from string containing delimiters
	 */
	@Test
	public void testGetCipherNameAsString_withDelimiters() {
		String input = "DESede/CBC/NoPadding";
		String expected = "DESede";
		
		String received = CryptoUtil.getCipherNameAsString(input);
		
		assertEquals(expected, received);
	}
	
	/**
	 * Positive test case: get cipher name as string from string not containing any delimiters
	 */
	@Test
	public void testGetCipherNameAsString_withoutDelimiters() {
		String input = "DESede";
		String expected = "DESede";
		
		String received = CryptoUtil.getCipherNameAsString(input);
		
		assertEquals(expected, received);
	}
	
	/**
	 * Positive test case: get cipher name as string from empty string
	 */
	@Test
	public void testGetCipherNameAsString_empty() {
		String input = "";
		String expected = "";
		
		String received = CryptoUtil.getCipherNameAsString(input);
		
		assertEquals(expected, received);
	}
	
	/**
	 * Positive test case: get cipher name as string from string starting with delimiter
	 */
	@Test
	public void testGetCipherNameAsString_delimiterFirst() {
		String input = "/";
		String expected = "";
		
		String received = CryptoUtil.getCipherNameAsString(input);
		
		assertEquals(expected, received);
	}
	
	/**
	 * Positive test case: get byte array compressed (TR-03110) encoding of public key.
	 */
	@Test
	public void testComp() throws Exception {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		byte[] xArray = HexString.toByteArray("4DD4D9CCB21EA76850E96699DF3EED2FA65CE0CBB3BF7604E1C458CF71B47F59");
		byte[] yArray = HexString.toByteArray("5AF8C1A214A81761DAA6D134DE0E5EA52D54C3BE3F05944F4460F81158D89DEA");
				
		// point coordinates originate from successful PACE test run, i.e. have been verified to be on the curve
		BigInteger publicPointX = new BigInteger(1, xArray);
		BigInteger publicPointY = new BigInteger(1, yArray);
		
		ECPoint point = new ECPoint(publicPointX, publicPointY);
		
		KeySpec publicKeySpec = new ECPublicKeySpec(point, domParamsEcdh.getKeySpec());
		
		KeyFactory keyFactory = KeyFactory.getInstance("ECDH");
		ECPublicKey ecdhPublicKeyExpected = (ECPublicKey) keyFactory.generatePublic(publicKeySpec);
		
		byte[] publicKeyEncodingPlain = CryptoUtil.compressEcPublicKey(ecdhPublicKeyExpected);
		
		assertArrayEquals("reconstructed encoding", xArray, publicKeyEncodingPlain);
	}
	
	/**
	 * Positive test case: get byte array compressed (TR-03110) encoding of public ECDH key, key is padded with 0-bytes.
	 */
	@Test
	public void testCompShortEcdhKey() throws Exception {
		DomainParameterSetEcdh domParamsEcdh = (DomainParameterSetEcdh) StandardizedDomainParameters.getDomainParameterSetById(13);
		byte[] xArray = HexString.toByteArray("0015A12C49DC3F2985AE44E5EF75AA0A1862527CD9D5B03D17CD1E2FC0290DB7");
		byte[] yArray = HexString.toByteArray("0AF39509F439220E7EEA61D15668BB5D63DD256BD7F4E9E1F9753866C4A6BD59");
				
		// point coordinates originate from successful PACE test run, i.e. have been verified to be on the curve
		BigInteger publicPointX = new BigInteger(1, xArray);
		BigInteger publicPointY = new BigInteger(1, yArray);
		
		ECPoint point = new ECPoint(publicPointX, publicPointY);
		
		KeySpec publicKeySpec = new ECPublicKeySpec(point, domParamsEcdh.getKeySpec());
		
		KeyFactory keyFactory = KeyFactory.getInstance("ECDH");
		ECPublicKey ecdhPublicKeyExpected = (ECPublicKey) keyFactory.generatePublic(publicKeySpec);
		
		byte[] publicKeyEncodingPlain = CryptoUtil.compressEcPublicKey(ecdhPublicKeyExpected);
		
		assertArrayEquals("reconstructed encoding", xArray, publicKeyEncodingPlain);
	}
	
}
