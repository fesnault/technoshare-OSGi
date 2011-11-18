package org.phoenix.osgi.bundles.muser.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.phoenix.osgi.bundles.managed.ManagedLogService;

public class Activator implements BundleActivator {
    private static final String SERVICE_PID = "dummy.logging.service";
    private volatile ServiceReference serviceReference;
    private volatile ManagedLogService managedService;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
       serviceReference = bundleContext.getServiceReference("org.phoenix.osgi.bundles.managed.ManagedLogService");
       if (serviceReference != null) {
           managedService = (ManagedLogService)bundleContext.getService(serviceReference);
           if (managedService == null) {
               throw new RuntimeException("Cannot get managed service, service reference returns a null service pointer.");
           } else {
               managedService.logIt();
           }
       } else {
           throw new RuntimeException("Cannot get managed service, service reference is null.");
       }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        managedService = null;
        bundleContext.ungetService(serviceReference);
    }

}
