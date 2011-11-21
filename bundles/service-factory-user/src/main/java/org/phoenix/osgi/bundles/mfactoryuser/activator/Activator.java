package org.phoenix.osgi.bundles.mfactoryuser.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.phoenix.osgi.bundles.mfactory.service.ConfigurableEchoService;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        ServiceReference[] references = bundleContext.getServiceReferences(ConfigurableEchoService.class.getName(), null);
        if (references == null) {
            System.out.println("Found no ConfigurableEchoService reference...");
        } else {
            System.out.println("found "+references.length+" ConfigurableEchoService references.");
            for (int i=0; i<references.length; i++) {
                ServiceReference ref = references[i];
                ConfigurableEchoService instance = (ConfigurableEchoService)bundleContext.getService(ref);
                if (instance != null) {
                    instance.echo();
                } else {
                    System.out.println("Could not get service instance from reference. Got null...");
                }
            }
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }

}
