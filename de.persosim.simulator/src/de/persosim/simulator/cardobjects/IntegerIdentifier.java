package de.persosim.simulator.cardobjects;

import de.persosim.simulator.utils.HexString;
import de.persosim.simulator.utils.Utils;

/**
 * An {@link CardObjectIdentifier} that basically relies on an integer
 * representation of its value.
 * <p/>
 * In addition to simple matching to equal values the provided {@link #matches}
 * implementation allows to match any value by providing {@link #MATCHES_ALWAYS}
 * as identifier.
 * 
 * @author slutters
 * 
 */
public abstract class IntegerIdentifier extends AbstractCardObjectIdentifier {
	protected static final int MATCHES_ALWAYS = Integer.MIN_VALUE;
	
	int integer;
	
	public IntegerIdentifier(int integer) {
		this.integer = integer;
	}
	
	public IntegerIdentifier() {
		this(MATCHES_ALWAYS);
	}
	
	public IntegerIdentifier(byte[] idBytes) {
		this(Utils.getIntFromUnsignedByteArray(idBytes));
	}

	@Override
	public boolean matches(CardObject obj) {
		for (CardObjectIdentifier curIdentifier : obj.getAllIdentifiers()) {
			if (curIdentifier == null) continue;
			
			if (this.getClass().isAssignableFrom(curIdentifier.getClass())) {
				if (integer == MATCHES_ALWAYS) {
					return true;
				}
				
				int otherInteger = ((IntegerIdentifier) curIdentifier).getInteger();
				if ((otherInteger == integer)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return integer + " (0x" + HexString.encode(Utils.toUnsignedByteArray(integer)) + ")";
	}

	public int getInteger() {
		return integer;
	}
	
	public abstract String getNameOfIdentifiedObject();
	
}
