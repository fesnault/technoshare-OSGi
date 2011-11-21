package org.phoenix.osgi.bundles.mfactory.service.impl;

import org.phoenix.osgi.bundles.mfactory.service.ConfigurableEchoService;

public class ConfigurableEchoServiceImpl implements ConfigurableEchoService {
    private String discriminator = null;
    
    public ConfigurableEchoServiceImpl(String discriminator) {
        this.discriminator = discriminator;
    }

    @Override
    public void echo() {
        System.out.println("ConfigurableEchoService : Echo ["+discriminator+"]");
    }
    
    @Override
    public void update(String disc) {
        this.discriminator = disc;
    }

//    @Override
//    public void updated(Dictionary properties) throws ConfigurationException {
//        String discriminator = (String)properties.get("discriminator");
//        if (discriminator != null && discriminator.length() > 0) {
//            this.discriminator = discriminator;
//        } else {
//            System.out.println("Could not update service configuration. Discriminator value is either null or empty.");
//        }
//    }

}
