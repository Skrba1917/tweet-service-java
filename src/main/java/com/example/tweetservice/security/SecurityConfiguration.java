package com.example.tweetservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;

  @Override
  @Bean
  public UserDetailsService userDetailsService() {
      return super.userDetailsService();
  }
    
    public void configureAuthentication(
            AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {

        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean()
            throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter
                .setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        //Naglasavamo browser-u da ne cache-ira podatke koje dobije u header-ima
        //detaljnije: https://www.baeldung.com/spring-security-cache-control-headers
        httpSecurity.headers().cacheControl().disable();
        //Neophodno da ne bi proveravali autentifikaciju kod Preflight zahteva
        httpSecurity.cors();
        //sledeca linija je neophodna iskljucivo zbog nacina na koji h2 konzola komunicira sa aplikacijom
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/tweets").permitAll()
                .antMatchers(HttpMethod.GET,"/api/tweets").permitAll()
                .antMatchers(HttpMethod.GET,"/api//tweetsByUser/{userid}").permitAll()
                .antMatchers(HttpMethod.POST,"/api/likes").permitAll()
                .antMatchers(HttpMethod.GET,"/api/likes/{tweetid}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/likes/names/{tweetid}").permitAll()
                //.antMatchers(HttpMethod.GET, "/api/clubs/{id}/**").access("@webSecurity.checkClubId(authentication,request,#id)")
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);


		//DODATO BUSINESREGISTER
    }
}
