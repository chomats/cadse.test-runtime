package fr.imag.adele.cadse.test.runtime;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	static BundleContext cxt;

	@Override
	public void start(BundleContext context) throws Exception {
		cxt = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		cxt = null;
	}

}
