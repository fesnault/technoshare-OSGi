package org.phoenix.osgi.bundles.managed.activator;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.phoenix.osgi.bundles.managed.ManagedLogService;
import org.phoenix.osgi.bundles.managed.impl.ManagedLogServiceImpl;

public class Activator implements BundleActivator {
    private static final String PID = "dummy.logging.service";
    private volatile ServiceRegistration serviceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        ManagedLogService service = new ManagedLogServiceImpl();
        Dictionary<Object, Object> props = new Properties();
        props.put("service.pid", PID);
        serviceRegistration = bundleContext.registerService(ManagedLogService.class.getName(), service, props);
        System.out.println("Registered a managed log service under the pid : "+PID);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
    }

}
