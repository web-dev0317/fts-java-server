package io.berbotworks.mc.security.filters;

import java.util.Map;

public interface SecurityConstants {
    String JWT_KEY = Key.getKey();
    String JWT_HEADER = "Authorization";
}

class Key {
    static String getKey() {
        Map<String, String> env = System.getenv();
        String key = null;
        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("jwt_key")) {
                key = env.get(envName);
            }
        }
        return key;
    }
}
