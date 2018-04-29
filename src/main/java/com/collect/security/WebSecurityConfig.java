package com.collect.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Heo, Jin Han
 * @since 2018-03-30
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService jwtUserDetailsService;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Value("${jwt.route.authentication.path}")
  private String authenticationPath;

  @Autowired
  public WebSecurityConfig(
      JwtAuthenticationEntryPoint unauthorizedHandler,
      JwtTokenUtil jwtTokenUtil,
      JwtUserDetailsService jwtUserDetailsService
  ) {
    this.unauthorizedHandler = unauthorizedHandler;
    this.jwtTokenUtil = jwtTokenUtil;
    this.jwtUserDetailsService = jwtUserDetailsService;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(jwtUserDetailsService)
        .passwordEncoder(passwordEncoderBean());
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoderBean() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // we don't need CSRF because our token is invulnerable
        .csrf().disable()

        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

        .authorizeRequests()

        // Un-secure H2 Database
        .antMatchers("/h2-console/**/**").permitAll()
        .antMatchers(HttpMethod.POST, "/user/signup").permitAll()
        .antMatchers(HttpMethod.POST, "/user/valid/**").permitAll()
        .antMatchers("/auth/**").permitAll()
        .anyRequest()
        .permitAll()
        .and()
          .oauth2Login()
            .defaultSuccessUrl("/user/signup/social")
            .failureUrl("/")
            .permitAll();

    // Custom JWT based security filter
    JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader);
    httpSecurity
        .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    // disable page caching
    httpSecurity
        .headers()
        .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
        .cacheControl();
  }

  @Override
  public void configure(WebSecurity web) {
    // AuthenticationTokenFilter will ignore the below paths
    web
        .ignoring()
        .antMatchers(
            HttpMethod.POST,
            authenticationPath
        )
        // allow anonymous resource requests
        .and()
        .ignoring()
        .antMatchers(
            HttpMethod.GET,
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
        )

        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");
  }
}
