package com.demo.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A custom converter for Spring Security's Opaque Token introspection.
 *
 * This class is responsible for taking the attributes returned from the
 * Introspection endpoint and converting them into a full `Authentication`
 * object, complete with the user's roles (authorities). This is used when the
 * resource server receives an opaque token, which must be validated by calling
 * the authorization server.
 */
public class OpaqueTokenRolesConverter implements OpaqueTokenAuthenticationConverter {

    /**
     * Converts the result of a successful token introspection into an `Authentication` object.
     *
     * @param introspectedToken      The original opaque token string (not used in this implementation).
     * @param authenticatedPrincipal The principal object containing the attributes (claims) returned from the introspection endpoint.
     * @return An `Authentication` object (specifically, a `UsernamePasswordAuthenticationToken`) populated with the user's principal, credentials nulled out, and authorities.
     */
    @Override
    public Authentication convert(String introspectedToken, OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
        // Step 1: Get the "realm_access" attribute from the principal. In the opaque token flow,
        // these attributes are fetched from the introspection endpoint, not from the token itself.
        Map<String, Object> realmAccess = authenticatedPrincipal.getAttribute("realm_access");

        // Step 2: Extract the roles, prepend the "ROLE_" prefix, and map them tp SimpleGrantedAuthority objects.
        Collection<GrantedAuthority> roles = ((List<String>) realmAccess.get("roles"))
                .stream().map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Step 3: Create and return the final Authentication object for the Security Context.
        // We use UsernamePasswordAuthenticationToken as a standard container for this information.
        return new UsernamePasswordAuthenticationToken(
                // The principal: The user's identifier, typically their username from the 'sub' claim.
                authenticatedPrincipal.getName(),
                // The credentials: Set to null because the authentication is already verified
                // by the introspection endpoint. Storing the token here is unnecessary.
                null,
                // The authorities: The collection of roles we just extracted.
                roles);
    }

}
