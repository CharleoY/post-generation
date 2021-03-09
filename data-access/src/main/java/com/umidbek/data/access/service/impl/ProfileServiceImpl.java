package com.umidbek.data.access.service.impl;

import com.umidbek.data.access.profile.ProfileDto;
import com.umidbek.data.access.ProfileNotFoundException;
import com.umidbek.data.access.entity.Profile;
import com.umidbek.data.access.exception.ProfileAlreadyRegisteredException;
import com.umidbek.data.access.repository.ProfileRepository;
import com.umidbek.data.access.service.ProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void add(ProfileDto profileDto) {

        Optional<Profile> profile = profileRepository.findByUsername(profileDto.getUsername());

        if (profile.isPresent()) {

            throw new ProfileAlreadyRegisteredException("Profile already registered, please use another username!");
        }

        Optional<Profile> profile1 = profileRepository.findByEmail(profileDto.getEmail());

        if (profile1.isPresent()) {

            throw new ProfileAlreadyRegisteredException("Profile already registered, please use another mail!");
        }

        Profile newProfile = dtoToEntity(profileDto);

        profileRepository.save(newProfile);
    }

    @Override
    public void update(Long id, Profile user) {
        profileRepository.save(user);
    }

    @Override
    public void delete(Long id)
    {
        profileRepository.deleteById(id);
    }

    @Override
    public void delete(Profile user)
    {
        profileRepository.delete(user);
    }

    @Override
    public Profile findById(Long id) {

        if (id == null) {
            throw new NullPointerException("Id is null");
        }

        Optional<Profile> profile = profileRepository.findById(id);

        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("user not found by id: " + id);
        }

        return profile.get();
    }

    @Override
    public Profile findByUsername(String username) {

        if (StringUtils.isBlank(username)) {
            throw new NullPointerException("Username is empty!");
        }

        Optional<Profile> optionalProfile = profileRepository.findByUsername(username);

        if (!optionalProfile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found by username: " + username);
        }

        return optionalProfile.get();
    }

    @Override
    public Profile findByEmail(String email) {

        if (StringUtils.isBlank(email)) {
            throw new NullPointerException("Email is empty!");
        }

        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);

        if (!optionalProfile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found by email:" + email);
        }

        return optionalProfile.get();
    }

    @Override
    public List<Profile> findProfilesByUsername(String username) {

        if (StringUtils.isBlank(username)) {
            throw new NullPointerException("Username is an empty or null!");
        }

        return profileRepository.findAllByUsername(username);
    }

    @Override
    public List<Profile> findProfilesByEmail(String email) {

        if (StringUtils.isBlank(email)) {
            throw new NullPointerException("Email is an empty or null!");
        }

        return profileRepository.findAllByEmail(email);
    }

    private Profile dtoToEntity(ProfileDto dto) {
        Profile profile = new Profile();
        BeanUtils.copyProperties(dto, profile);
        profile.setCreatedDate(new Date());

        return  profile;

    }

}
