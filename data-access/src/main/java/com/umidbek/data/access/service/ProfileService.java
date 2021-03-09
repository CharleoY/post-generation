package com.umidbek.data.access.service;

import com.umidbek.data.access.profile.ProfileDto;
import com.umidbek.data.access.entity.Profile;

import java.util.List;

public interface ProfileService
{
    void add(ProfileDto user);

    void update(Long id, Profile user);

    void delete(Long id);

    void delete(Profile user);

    Profile findById(Long id);

    Profile findByUsername(String username);

    List<Profile> findProfilesByUsername(String username);

    Profile findByEmail(String email);

    List<Profile> findProfilesByEmail(String email);

}
