package org.phoenix.osgi.bundles.bundlea;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.phoenix.osgi.engine.interfaces.PocInterface;

public class Activator implements BundleActivator {

	
    private volatile ServiceRegistration serviceRegistration;

    @SuppressWarnings("unchecked")
    public void start(BundleContext bundleContext) throws Exception {
        BundleAPocImpl pocImpl = new BundleAPocImpl();
        pocImpl.setBundleContext(bundleContext);
        Dictionary properties = new Properties();
        properties.put("site", "a");
        serviceRegistration = bundleContext.registerService(PocInterface.class.getName(), pocImpl, properties);
        System.out.println("Bundle A started.");
    }

    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
        System.out.println("Bundle A stopping...");
    }
}
