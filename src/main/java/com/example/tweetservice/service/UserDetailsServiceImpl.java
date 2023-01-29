package com.example.tweetservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.tweetservice.model.AuthControl;



@Service
//Primary je neophodno da bi naglasili Spring Boot-u da zelimo bas ovaj UserDetailService kada budemo koristili
//Autowired pri konfiguraciji security-a
@Primary
public class UserDetailsServiceImpl implements  UserDetailsService {

	
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		RestTemplate restTemplate = new RestTemplate();
	

		String serviceUrl = "http://registracija:8082/users/findByUsername?username={username}";
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("username", username);
		AuthControl user = restTemplate.getForObject(serviceUrl, AuthControl.class, uriParams);
		
		
        if(user == null){
            throw new UsernameNotFoundException("There is no user with username " + username);
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + user.getRole();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername().trim(),
                    user.getPassword().trim(),
                    grantedAuthorities);
        }
    }

	
}
