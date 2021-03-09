package com.umidbek.webapi.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final EhCacheBean ehCacheBean;
    private final JwtUserDetailsServiceImpl userDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, EhCacheBean ehCacheBean, JwtUserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.ehCacheBean = ehCacheBean;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationOwner(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest, BindingResult bindingResult) throws AuthenticationException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Please check your input data"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtUser jwtUser = SecurityUtils.getUserDetails();

            if (jwtUser == null) {
                throw new UsernameNotFoundException("User not found!");
            }

            String token = jwtTokenUtil.generateToken(jwtUser);
            ehCacheBean.putUserDetails(jwtUser);

            return ResponseEntity.ok(new JwtAuthenticationResponse(token, jwtUser.getFirstname() + " " + jwtUser.getLastname(), SecurityUtils.getProfileRole()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Username or password incorrect"));
        }
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, jwtUser.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, jwtUser.getFirstname() + " " + jwtUser.getLastname(), SecurityUtils.getProfileRole()));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);

        if (token == null || token.isEmpty()) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            ehCacheBean.removeUser(username);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
