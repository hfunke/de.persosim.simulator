package de.persosim.simulator.cardobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class KeyObject extends AbstractCardObject {
	
	protected KeyIdentifier primaryIdentifier;
	
	protected Collection<CardObjectIdentifier> furtherIdentifiers = new ArrayList<>();
	
	protected boolean privilegedOnly = false;
	
	public KeyIdentifier getPrimaryIdentifier() {
		return primaryIdentifier;
	}
	
	public boolean isPrivilegedOnly() {
		return privilegedOnly;
	}
	
	@Override
	public Collection<CardObjectIdentifier> getAllIdentifiers() {
		Collection<CardObjectIdentifier> result = super.getAllIdentifiers();
		result.add(primaryIdentifier);
		result.addAll(furtherIdentifiers);
		return result;
	}

	/**
	 * This method adds an additional {@link OidIdentifier} object identifying objects implementing this interface.
	 * Associating one or more {@link OidIdentifier} objects with a {@link KeyPairObject} is the preferred way to indicate that the key material provided by {@link KeyPairObject} can be used with a given OID.
	 * Querying the object store for a concrete OID represented by an {@link OidIdentifier} will return all {@link KeyPairObject} objects with keys that can be used with the respective OID.
	 * @param oidIdentifier additional {@link OidIdentifier} object identifying objects implementing this interface
	 */
	public void addOidIdentifier(OidIdentifier oidIdentifier) {
		furtherIdentifiers.add(oidIdentifier);
	}
	
	/**
	 * This method adds a {@link Collection} of additional {@linkCardObjectIdentifier} objects identifying objects implementing this interface.
	 * @param cardObjectIdentifiers {@link Collection} of additional {@link CardObjectIdentifier} object(s) identifying objects implementing this interface
	 */
	public void addFurtherIdentifiers(Collection<CardObjectIdentifier> cardObjectIdentifiers) {
		furtherIdentifiers.addAll(cardObjectIdentifiers);
	}
	
	/**
	 * This method adds additional {@linkCardObjectIdentifier} objects identifying objects implementing this interface.
	 * @param cardObjectIdentifiers additional {@link CardObjectIdentifier} object(s) identifying objects implementing this interface
	 */
	public void addFurtherIdentifiers(CardObjectIdentifier... cardObjectIdentifiers) {
		addFurtherIdentifiers(Arrays.asList(cardObjectIdentifiers));
	}
	
}
