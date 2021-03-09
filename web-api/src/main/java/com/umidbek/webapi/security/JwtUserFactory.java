package com.umidbek.webapi.security;

import com.umidbek.data.access.enums.ProfileRole;
import com.umidbek.data.access.entity.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Profile profile) {
        return new JwtUser(
                profile.getId(),
                profile.getUsername(),
                profile.getFirstname(),
                profile.getLastname(),
                profile.getPassword(),
                mapToGrantedAuthorities(profile.getRole()),
                true,
                profile.getLastUpdatedDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileRole profileRole) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(profileRole.authority()));
        return grantedAuthorities;
    }
}
