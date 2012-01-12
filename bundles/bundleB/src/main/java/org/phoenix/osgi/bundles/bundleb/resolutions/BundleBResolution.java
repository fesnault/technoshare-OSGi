package org.phoenix.osgi.bundles.bundleb.resolutions;

import org.phoenix.osgi.engine.interfaces.ResolutionEnum;

public enum BundleBResolution implements ResolutionEnum {
    BUNDLEB;
    
    public String getPath() {
        return "/bundle/b/";
    }

}
