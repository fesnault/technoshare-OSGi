package org.phoenix.osgi.bundles.mfactory;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.phoenix.osgi.bundles.mfactory.service.ConfigurableEchoService;
import org.phoenix.osgi.bundles.mfactory.service.impl.ConfigurableEchoServiceImpl;

public class MyServiceFactory implements ManagedServiceFactory {
    private final Map<String, ConfigurableEchoService> services = new HashMap<String, ConfigurableEchoService>();
    private BundleContext context;
    private final Map<String, ServiceRegistration> registrations = new HashMap<String, ServiceRegistration>(); 

    public MyServiceFactory(BundleContext context) {
        this.context = context;
    }
    
    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public void updated(String pid, Dictionary properties)
            throws ConfigurationException {
        String disc = (String)properties.get("discriminator");
        if (services.containsKey(pid)) {
            //((ConfigurableEchoService)services.get(pid)).update(disc);
            System.out.println("Updtated discriminator of service with pid : "+pid+" new value is : "+disc);
        } else {
            ConfigurableEchoService service = new ConfigurableEchoServiceImpl(disc);
            services.put(pid, service);
            // register the new service instance
            properties.put(Constants.SERVICE_PID, pid);
            ServiceRegistration registration = context.registerService(ConfigurableEchoService.class.getName(), service, properties);
            registrations.put(pid, registration);
            System.out.println("Added service with pid : "+pid+" and discriminator : "+disc);
        }
    }

    @Override
    public void deleted(String pid) {
        ConfigurableEchoService service = services.remove(pid);
        ServiceRegistration registration = registrations.remove(pid);
        registration.unregister();
        registration = null;
        service = null;
        System.out.println("Removed service with pid : "+pid);
    }

}
