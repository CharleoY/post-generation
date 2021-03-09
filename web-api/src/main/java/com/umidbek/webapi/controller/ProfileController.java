package com.umidbek.webapi.controller;

import com.umidbek.data.access.profile.CreateProfileDto;
import com.umidbek.data.access.profile.ProfileDto;
import com.umidbek.data.access.enums.ProfileRole;
import com.umidbek.data.access.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProfileController(ProfileService profileService, BCryptPasswordEncoder passwordEncoder) {
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody CreateProfileDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Bad request!");
        }
        profileService.add(toProfileDto(dto));

        return ResponseEntity.ok("Profile created!");
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping(value = "/admin")
    public ResponseEntity<String> createAdmin(@Valid @RequestBody ProfileDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Bad request!");
        }
        profileService.add(dto);

        return ResponseEntity.ok("Profile created!");
    }


    private ProfileDto toProfileDto(CreateProfileDto dto) {
        ProfileDto profileDto = new ProfileDto();

        profileDto.setEmail(dto.getEmail());
        profileDto.setUsername(dto.getUsername());
        profileDto.setPassword(passwordEncoder.encode(dto.getPassword()));
        profileDto.setRole(ProfileRole.USER);

        return profileDto;
    }
}
