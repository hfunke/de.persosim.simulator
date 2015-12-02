package de.persosim.simulator.cardobjects;

import java.util.Collection;
import java.util.Date;


/**
 * This {@link CardObject} stores a date and time.
 * 
 * @author mboonk
 *
 */
public class DateTimeCardObject extends AbstractCardObject {

	Date currentDate;
	DateTimeObjectIdentifier identifier;

	public DateTimeCardObject(){}
	
	public DateTimeCardObject(DateTimeObjectIdentifier identifier, Date date) {
		currentDate = new Date(date.getTime());
		this.identifier = identifier;
	}

	@Override
	public Collection<CardObjectIdentifier> getAllIdentifiers() {
		Collection<CardObjectIdentifier> result = super.getAllIdentifiers();
		result.add(identifier);
		return result;
	}
	
	/**
	 * @return the stored date
	 */
	public Date getDate(){
		return new Date(currentDate.getTime());
	}
	
	/**
	 * @param the new date to store
	 */
	public void update(Date date){
		// XXX MBK check update access rights here
		currentDate = new Date(date.getTime());
	}
	
	@Override
	public String toString() {
		return "DateTimeCardObject (" + currentDate + ")";
	}

}
