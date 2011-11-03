package org.phoenix.osgi.bundles.basicimport;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.phoenix.osgi.bundles.basicexport.ExportedClass;

public class Activator implements BundleActivator {

	public void start(BundleContext arg0) throws Exception {
		System.out.println("Importing bundle started.");
		ExportedClass importedClass = new ExportedClass();
		importedClass.setValue(1);
		System.out.println("Created and imported class instance and gave it a value : "+importedClass.getValue());
	}

	public void stop(BundleContext arg0) throws Exception {
		System.out.println("Importing bundle stopping...");
	}

}
