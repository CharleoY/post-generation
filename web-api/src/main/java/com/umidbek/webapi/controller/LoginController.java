package com.umidbek.webapi.controller;

import com.umidbek.data.access.service.ProfileService;
import com.umidbek.webapi.component.EhCacheBean;
import com.umidbek.webapi.dto.ErrorResponse;
import com.umidbek.webapi.security.JwtTokenUtil;
import com.umidbek.webapi.security.JwtUser;
import com.umidbek.webapi.security.JwtUserDetailsServiceImpl;
import com.umidbek.webapi.security.dto.JwtAuthenticationRequest;
import com.umidbek.webapi.security.dto.JwtAuthenticationResponse;
import com.umidbek.webapi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.logging.Logger;


@RestController
@RequestMapping
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class.getCanonicalName());

    private final EhCacheBean ehCacheBean;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.header}")
    private String tokenHeader;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, EhCacheBean ehCacheBean, ProfileService profileService, BCryptPasswordEncoder passwordEncoder, JwtUserDetailsServiceImpl userDetailsService) {
        this.ehCacheBean = ehCacheBean;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(value = "/login")
    public ModelAndView getLoginPage(Model model) {
        logger.info("User visited to the login page!");

        return new ModelAndView("login");
    }

}
