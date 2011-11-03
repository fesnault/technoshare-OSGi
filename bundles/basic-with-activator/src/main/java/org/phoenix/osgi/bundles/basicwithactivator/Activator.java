package org.phoenix.osgi.bundles.basicwithactivator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext arg0) throws Exception {
		System.out.println("Basic bundle started.");
	}

	public void stop(BundleContext arg0) throws Exception {
		System.out.println("Basic bundle stopping...");
	}

}
