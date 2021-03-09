package com.umidbek.webapi.config;

import com.umidbek.webapi.security.JwtAuthenticationEntryPoint;
import com.umidbek.webapi.security.JwtAuthenticationTokenFilter;
import com.umidbek.webapi.security.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] RESOURCES = new String[]{
            "/resources/**",
            "/static/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/img/**",
            "/vendor/**",
            "/fonts/**",
            "/templates/**",
    };

    private static final String[] unfilteredUrls = new String[]{
            "/auth*",
            "/auth/login",
            "/auth/logout",
            "/auth/refresh",
            "/profile",
            "/h2-console/**",
            "/login/**",
            "/search/**",
            "/register/**",
            "/"
    };
    private static final String[] swaggerUrls = new String[]{
            "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**"
    };

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private final JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebApiSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, JwtUserDetailsServiceImpl userDetailsService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
                .failureUrl("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .and()
                .authorizeRequests()
                .antMatchers(unfilteredUrls).permitAll()
                .antMatchers(swaggerUrls).permitAll()
                .antMatchers(RESOURCES).permitAll()
                .anyRequest()
                .authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}