package de.persosim.simulator;

import static de.persosim.simulator.utils.PersoSimLogger.ERROR;
import static de.persosim.simulator.utils.PersoSimLogger.log;

import org.globaltester.cryptoprovider.Cryptoprovider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import de.persosim.simulator.crypto.Crypto;

public class Activator implements BundleActivator {

	public static BundleContext context;
	
	//TODO move this service tracking to the class PersoSimLogger (similar to Crypto)
	private static ServiceTracker<LogService, LogService> logServiceTracker;
	private static Activator plugin;
	private static PersoSim sim;
	private ServiceRegistration<Simulator> simRegistration;
	
	public static LogService getLogservice() {		
		if (logServiceTracker != null){
			return logServiceTracker.getService();
		}
		return null;
	}	

	@Override
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		plugin = this;
		//get LogService
		logServiceTracker = new ServiceTracker<LogService, LogService>(context, LogService.class.getName(), null);
        logServiceTracker.open();
        
        //register service listener for CryptoProvider
        String filter = "(objectclass=" + Cryptoprovider.class.getName() + ")";
		context.addServiceListener(Crypto.getInstance(), filter);
		
}

	@Override
	public void stop(BundleContext context) throws Exception {
		logServiceTracker.close();
		Activator.context = null;
	}

	public static BundleContext getContext() {
		return context;
	}
	
	/**
	 * This enables the {@link Simulator} for the PersoSim simulator.   
	 */
	public void enableService() {
		
		checkNumberOfSimulators();
		sim = new PersoSim();
		simRegistration = context.registerService(Simulator.class, sim, null);
	}
	
	/**
	 * Checks if any other simulator is running and if so throws an RuntimeException
	 * @throws InvalidSyntaxException
	 */
	public void checkNumberOfSimulators() {	
		if (getNumberOfSimulators() > 0) {
			log(this.getClass(), "There is already a simulator running, please stop it before starting another one!", ERROR);
			throw new RuntimeException("There is already a simulator running, please stop it before starting another one!");
		}
	}
	
	/**
	 * Returns the number of current running simulators
	 * @return number of running simulators
	 */
	public int getNumberOfSimulators() {
		int simulatorCnt = 0;
		try {
			return context.getServiceReferences(Simulator.class, null).size();
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		};
		return simulatorCnt;
	}
	
	/**
	 * This disables the {@link Simulator} for the PersoSim simulator. 
	 */
	public void disableService(){
		if (context == null || sim == null){
			return;
		}
		
		if (sim.isRunning()){
			sim.stopSimulator();
		}
		sim = null;
		simRegistration.unregister();
	}
	
	public static Activator getDefault() {
		return plugin;
		
	}
	
	/**
	 * This function returns the current simulator
	 * @return Simulator
	 */
	public Simulator getSim() {
		return sim;
	}
	
}
