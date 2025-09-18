package com.demo.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A custom Spring Security Converter to extract roles from a Keycloak JWT.
 * This class is responsible for mapping the roles found within the "realm_access"
 * claim of a JWT to a collection of Spring Security's GrantedAuthority objects.
 */
public class JwtTokenRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Converts the given JWT into a collection of GrantedAuthority objects.
     *
     * @param source The JWT token provided by the Spring Security framework.
     * @return A collection of GrantedAuthority objects representing the user's roles.
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // Step 1: Get the "realm_access" claim from the JWT. This claim is specific to Keycloak and contains realm-level roles.
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        // Step 2: Perform a safety check. If the claim is missing or empty, return an empty list of authorities to prevent NullPointerExceptions.
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        // Step 3: Extract, transform, and collect the roles. The roles are expected to be in a list of strings under the "roles" key.
        Collection<GrantedAuthority> authorities = ((List<String>) realmAccess.get("roles"))
                .stream()
                // Step 3a: Prepend "ROLE_" to each role string.
                .map(role -> "ROLE_" + role)
                // Step 3b: Convert each prefixed role string into a SimpleGrantedAuthority object.
                .map(SimpleGrantedAuthority::new)
                // Step 3c: Collect the resulting authorities into a list.
                .collect(Collectors.toList());

        return authorities;
    }

}
