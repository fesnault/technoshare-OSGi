package org.phoenix.osgi.bundles.mfactory.activator;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;
import org.phoenix.osgi.bundles.mfactory.MyServiceFactory;

public class Activator implements BundleActivator {
    private static final String FACTORY_PID = "my.service.factory";
    private volatile ServiceRegistration serviceRegistration;

    @SuppressWarnings("unchecked")
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        MyServiceFactory factory = new MyServiceFactory(bundleContext);
        Dictionary properties = new Properties();
        properties.put(Constants.SERVICE_PID, FACTORY_PID);
        serviceRegistration = bundleContext.registerService(ManagedServiceFactory.class.getName(), factory, properties);
        System.out.println("Registered a managed service factory under the pid : "+FACTORY_PID);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
    }

}
