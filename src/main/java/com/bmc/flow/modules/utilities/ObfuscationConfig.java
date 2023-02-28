package com.bmc.flow.modules.utilities;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "obfuscation")
public interface ObfuscationConfig {

  List<String> cities();

  List<String> alphabet();

}
