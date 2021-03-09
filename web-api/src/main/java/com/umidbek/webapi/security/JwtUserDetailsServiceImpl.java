package com.umidbek.webapi.security;

import com.umidbek.data.access.entity.Profile;
import com.umidbek.data.access.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public JwtUserDetailsServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByUsername(username);
        if (!optionalProfile.isPresent()) {
            throw new UsernameNotFoundException(String.format("No optionalProfile found with username '%s'.", username));
        }

        return JwtUserFactory.create(optionalProfile.get());
    }
}
