package com.umidbek.webapi.controller;

import com.umidbek.data.access.enums.ProfileRole;
import com.umidbek.data.access.profile.CreateProfileDto;
import com.umidbek.data.access.profile.ProfileDto;
import com.umidbek.data.access.service.ProfileService;
import com.umidbek.webapi.component.EhCacheBean;
import com.umidbek.webapi.dto.ErrorResponse;
import com.umidbek.webapi.security.JwtTokenUtil;
import com.umidbek.webapi.security.JwtUser;
import com.umidbek.webapi.security.dto.JwtAuthenticationResponse;
import com.umidbek.webapi.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.logging.Logger;

@Valid
@RestController
@RequestMapping("/register")
public class RegisterController {

    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getCanonicalName());

    private final EhCacheBean ehCacheBean;
    private final JwtTokenUtil jwtTokenUtil;
    private final ProfileService profileService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public RegisterController(EhCacheBean ehCacheBean, JwtTokenUtil jwtTokenUtil, ProfileService profileService,
                              BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.ehCacheBean = ehCacheBean;
        this.jwtTokenUtil = jwtTokenUtil;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping
    public ModelAndView getSignUpPage(Model model) {
        LOGGER.info("User visited to the sign up page!");
        return new ModelAndView("register");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateProfileDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.warning(bindingResult.toString());
            return ResponseEntity.badRequest().body("Please, check your data!");
        }

        try {

            if (!dto.getPassword().equals(dto.getReEnterPassword()) || StringUtils.isBlank(dto.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, check your password!");
            }


            profileService.add(toProfileDto(dto));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtUser jwtUser = SecurityUtils.getUserDetails();

            if (jwtUser == null) {
                throw new UsernameNotFoundException("User not found!");
            }

            String token = jwtTokenUtil.generateToken(jwtUser);
            ehCacheBean.putUserDetails(jwtUser);

            return ResponseEntity.ok(new JwtAuthenticationResponse(token, jwtUser.getFirstname() + " " + jwtUser.getLastname(), SecurityUtils.getProfileRole()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
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
