package org.phoenix.osgi.bundles.uses.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.phoenix.osgi.bundles.uses.bar.Bar;
import org.phoenix.osgi.bundles.uses.foo.Foo;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		Bar bar = new Bar();
		System.out.println("Bar created.");
		Foo foo = bar.getFoo();
		foo.printIt();
	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
