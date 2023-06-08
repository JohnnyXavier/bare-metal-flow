package com.bmc.flow.modules.utilities;

import io.smallrye.config.ConfigMapping;

import java.util.List;

/**
 * this class is a mapping for the obfuscation properties.
 */
@ConfigMapping(prefix = "obfuscation")
public interface ObfuscationConfig {

    List<String> cities();

    List<String> alphabet();

}
