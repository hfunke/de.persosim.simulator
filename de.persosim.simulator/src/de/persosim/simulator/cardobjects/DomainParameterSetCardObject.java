package de.persosim.simulator.cardobjects;

import java.util.ArrayList;
import java.util.Collection;

import de.persosim.simulator.crypto.DomainParameterSet;
import de.persosim.simulator.exception.AccessDeniedException;
import de.persosim.simulator.secstatus.SecStatus;

/**
 * This object wraps domain parameters for storing them in the object store.
 * They can be retrieved from there using their domain parameter id or additionally associated OIDs.
 * 
 * @author slutters
 *
 */
public class DomainParameterSetCardObject extends AbstractCardObject {
	
	protected DomainParameterSet domainParameterSet;
	protected DomainParameterSetIdentifier primaryIdentifier;
	protected Collection<CardObjectIdentifier> furtherIdentifiers;
	
	public DomainParameterSetCardObject() {
	}
	
	public DomainParameterSetCardObject(DomainParameterSet domainParameterSet, DomainParameterSetIdentifier identifier) {
		this.primaryIdentifier = identifier;
		this.domainParameterSet = domainParameterSet;
		
		this.furtherIdentifiers = new ArrayList<CardObjectIdentifier>();
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
	 * Associating one or more {@link OidIdentifier} objects with a {@link DomainParameterSetCardObject} is the preferred way to indicate that the domain parameters provided by {@link DomainParameterSetCardObject} can be used with a given OID.
	 * Querying the object store for a concrete OID represented by an {@link OidIdentifier} will return all {@link DomainParameterSetCardObject} objects with domain parameters that can be used with the respective OID.
	 * @param oidIdentifier additional {@link OidIdentifier} object identifying objects implementing this interface
	 */
	public void addOidIdentifier(OidIdentifier oidIdentifier) {
		furtherIdentifiers.add(oidIdentifier);
	}
	
	public DomainParameterSetIdentifier getPrimaryIdentifier() {
		return primaryIdentifier;
	}
	
	/**
	 * Remove the given OidIdentifier (if present) from the set of identifiers
	 * @param oidIdentifier
	 * 		
	 * @throws AccessDeniedException
	 */
	public void removeOidIdentifier(OidIdentifier oidIdentifier) throws AccessDeniedException {
		if (!SecStatus.checkAccessConditions(getLifeCycleState())){
			throw new AccessDeniedException("Updating forbidden");
		}
		
		furtherIdentifiers.remove(oidIdentifier);
	}

	public DomainParameterSet getDomainParameterSet() {
		return domainParameterSet;
	}
	
	@Override
	public String toString() {
		return "domain parameter set " + primaryIdentifier.getDomainParameterId();
	}

}
