package de.persosim.simulator.cardobjects;


/**
 * This class implements an identifier for key objects using their key reference.
 * 
 * @author slutters
 *
 */
public class KeyIdentifier extends IntegerIdentifier {

	
	public KeyIdentifier(byte[] idBytes) {
		super(idBytes);
	}
	
	public KeyIdentifier(int keyReference) {
		super(keyReference);
	}
	
	public KeyIdentifier() {
		super();
	}
	
	public int getKeyReference() {
		return getInteger();
	}

	@Override
	public String getNameOfIdentifiedObject() {
		return "key reference";
	}
	
}
