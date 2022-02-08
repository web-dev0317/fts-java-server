package io.berbotworks.mc.security;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
// import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.berbotworks.mc.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import static io.berbotworks.mc.security.filters.SecurityConstants.*;

@Slf4j
public class JWT {
    public static String generateToken(Authentication authentication, MCUserDetails userDetails) {
        return generateToken(authentication, userDetails, null);
    }

    public static String generateToken(User user) {
        return generateToken(null, null, user);
    }

    private static String generateToken(Authentication authentication, MCUserDetails userDetails, User user) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .setIssuer("io.berbotworks")
                .setSubject("JWToken")
                .claim("uid", user != null ? user.getId() : userDetails.getUid())
                .claim("authorities",
                        user != null ? getRolesAsString_(user.getRoles())
                                : getRolesAsString(authentication.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .signWith(key).compact();
        return jwt;
    }

    private static String getRolesAsString(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        String str = String.join(",", authoritiesSet);
        log.debug("authorities string in JWTGen : {}", str);
        return str;
    }

    private static String getRolesAsString_(Collection<String> roles) {
        StringJoiner sj = new StringJoiner(",");
        for (String role : roles) {
            sj.add(role);
        }
        log.debug("roles string in JWTGen getRolesAsString_(): {}", sj);
        return sj.toString();
    }
}
