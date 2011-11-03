package org.phoenix.osgi.bundles.basicexport;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext arg0) throws Exception {
		System.out.println("Exporting bundle started.");
	}

	public void stop(BundleContext arg0) throws Exception {
		System.out.println("Exporting bundle stopping...");
	}

}
