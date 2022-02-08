package io.berbotworks.mc.security.filters;

public interface SecurityConstants {
    String JWT_KEY = System.getenv().get("JWT_KEY");
    String JWT_HEADER = "Authorization";
}
