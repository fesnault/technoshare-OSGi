package org.phoenix.osgi.bundles.bundlea.resolutions;

import org.phoenix.osgi.engine.interfaces.ResolutionEnum;

public enum BundleAResolution implements ResolutionEnum{
    BUNDLEA;
    
    public String getPath() {
        return "/bundle/a/";
    }
}
