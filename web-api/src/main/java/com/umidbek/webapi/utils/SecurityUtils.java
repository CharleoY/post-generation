package com.umidbek.webapi.utils;

import com.umidbek.data.access.enums.ProfileRole;
import com.umidbek.webapi.security.JwtUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static Long getUserId() {
        JwtUser user = getUserDetails();

        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public static JwtUser getUserDetails() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof JwtUser) {
            return (JwtUser) authentication.getPrincipal();
        }

        return null;
    }

    public static ProfileRole getProfileRole() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof JwtUser) {
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            String authRole = jwtUser.getAuthorities().iterator().next().getAuthority();
            return ProfileRole.valueOf(authRole);
        }
        return ProfileRole.USER;
    }

}
