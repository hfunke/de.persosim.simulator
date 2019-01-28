package de.persosim.simulator.securemessaging;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.globaltester.cryptoprovider.Crypto;

import de.persosim.simulator.crypto.CryptoSupport;
import de.persosim.simulator.crypto.CryptoUtil;
import de.persosim.simulator.crypto.SendSequenceCounter;
import de.persosim.simulator.exception.CryptoException;
import de.persosim.simulator.processing.UpdatePropagation;
import de.persosim.simulator.utils.HexString;

public class SmDataProviderTr03110 implements SmDataProvider {
	
	@Override
	public Class<? extends UpdatePropagation> getKey() {
		return SmDataProvider.class;
	}

	private Cipher cipher;
	private SecretKeySpec keyEnc;

	private Mac mac;
	private SecretKeySpec keyMac;
	
	private boolean pendingCommandApdu;
	
	/**
	 * SendSequenceCounter according to BSI TR-03110
	 */
	private SendSequenceCounter ssc;

	public SmDataProviderTr03110(SecretKeySpec cipherKey, SecretKeySpec macKey, SendSequenceCounter newSsc) {
		keyEnc = cipherKey;
		keyMac = macKey;
		
		try {
			cipher = getCipher(keyEnc.getAlgorithm());
			
			// According to developer consens we want to create wrapper objects that
			// encapsulate Cipher or Mac resp. together with the relevant key. These
			// objects can be passed as method parameters and encapsulate all
			// different behavior by overloading. Thus protocols dont need to switch
			// on objecttype while the OID classes work as factory for these
			// wrappers.
			// The differentiation between Cipher and Mac wrappers is intended to be
			// according to the JavaCryptoApi. 
			// {@link CryptoSupport}
			if (CryptoUtil.getCipherNameAsString(cipher.getAlgorithm()).equals(
					"DESede")) {
				// 3DES
				mac = Mac.getInstance("ISO9797ALG3", Crypto.getCryptoProvider());
			} else {
				//AES
				mac = Mac.getInstance(keyMac.getAlgorithm(), Crypto.getCryptoProvider());
			}
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException(e);
		}
		
		ssc = newSsc;
		
		pendingCommandApdu = false;
	}
	
	public SmDataProviderTr03110(SecretKeySpec cipherKey, SecretKeySpec macKey) {
		this(cipherKey, macKey, new SendSequenceCounter(getCipher(cipherKey.getAlgorithm()).getBlockSize()));
	}
	
	private static Cipher getCipher(String algorithm) {
		try {
			return Cipher.getInstance(algorithm, Crypto.getCryptoProvider());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public void init(SmDataProvider prev) {
		// nothing to be done here
		// complete initialization done within constructor, no need to know the predecessor
	}

	@Override
	public void nextIncoming() {
		if(!pendingCommandApdu) {
			ssc.increment();
			pendingCommandApdu = true;
		}
	}

	@Override
	public void nextOutgoing() {
		if(pendingCommandApdu) {
			ssc.increment();
			pendingCommandApdu = false;
		}
	}

	@Override
	public Cipher getCipher() {
		return cipher;
	}

	@Override
	public IvParameterSpec getCipherIv() {
		byte[] cipherIvPlain;
		if (CryptoUtil.getCipherNameAsString(cipher.getAlgorithm()).equals("DESede")) {
			//3DES
			cipherIvPlain = new byte[8];
		} else {
			//AES
			cipherIvPlain = CryptoSupport.encryptWithIvZero(cipher, ssc.toByteArray(), keyEnc);
		}
		return new IvParameterSpec(cipherIvPlain);
	}

	@Override
	public SecretKeySpec getKeyEnc() {
		return keyEnc;
	}

	@Override
	public Mac getMac() {
		return mac;
	}

	@Override
	public byte[] getMacAuxiliaryData() {
		// incrementing is already done in #nextIncoming() and #nextOutgoing()
		return ssc.toByteArray();
	}

	@Override
	public SecretKeySpec getKeyMac() {
		return keyMac;
	}

	@Override
	public Integer getMacLength() {
		return 8;
	}

	@Override
	public SmDataProviderTr03110Generator getSmDataProviderGenerator() {
		return new SmDataProviderTr03110Generator(this);
	}
	
	public SendSequenceCounter getSsc() {
		return ssc;
	}

	public boolean isPendingCommandApdu() {
		return pendingCommandApdu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyEnc == null) ? 0 : keyEnc.hashCode());
		result = prime * result + ((keyMac == null) ? 0 : keyMac.hashCode());
		result = prime * result + (pendingCommandApdu ? 1231 : 1237);
		result = prime * result + ((ssc == null) ? 0 : ssc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmDataProviderTr03110 other = (SmDataProviderTr03110) obj;
		if (keyEnc == null) {
			if (other.keyEnc != null)
				return false;
		} else if (!keyEnc.equals(other.keyEnc))
			return false;
		if (keyMac == null) {
			if (other.keyMac != null)
				return false;
		} else if (!keyMac.equals(other.keyMac))
			return false;
		if (pendingCommandApdu != other.pendingCommandApdu)
			return false;
		if (ssc == null) {
			if (other.ssc != null)
				return false;
		} else if (!ssc.equals(other.ssc))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SM data provider:");
		sb.append("\n--------");
		sb.append("\ncipher:");
		sb.append("\nalgorithm: " + getCipher().getAlgorithm());
		sb.append("\nIV:  " + HexString.encode(getCipherIv().getIV()));
		sb.append("\nkey: " + HexString.encode(getKeyEnc().getEncoded()));
		sb.append("\n--------");
		sb.append("\nmac:");
		sb.append("\nalgorithm: " + getMac().getAlgorithm());
		sb.append("\nlength:   " + getMacLength());
		sb.append("\naux data: " + HexString.encode(getMacAuxiliaryData()));
		sb.append("\nkey:      " + HexString.encode(getKeyMac().getEncoded()));
		sb.append("\n--------");
		sb.append("\nSSC: " + getSsc());
		
		return sb.toString();
	}

}
