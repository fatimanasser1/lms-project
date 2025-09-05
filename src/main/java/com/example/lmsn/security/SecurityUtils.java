package com.example.lmsn.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static String currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            var jwt = jwtAuth.getToken();
            String sub = jwt.getSubject();
            String pref = jwt.getClaimAsString("preferred_username");
            return Optional.ofNullable(sub).orElse(pref);
        }
        // fallback to auth name
        return auth == null ? "anonymous" : auth.getName();
    }
}
