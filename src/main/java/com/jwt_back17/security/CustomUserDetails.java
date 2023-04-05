package com.jwt_back17.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import com.jwt_back17.jwt_back.entity.UserEntity;
import com.jwt_back17.jwt_back.repository.UserRepository;

/**
 * 
 * Me permet d'avoir accès aux infos de l'utilisateur dans spring security.
 */
@Service
public class CustomUserDetails implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // T-753 Auto-generated method stub
    UserEntity user = userRepository.findUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("J'ai pas trouvé le username pourtant j'ai essayé hein 🥵");
    }

    // ADD_ROLE_TO_USER_AND_TOKEN
    // Ici, je fais une version simple en passant un string, à vous d'adapter selon
    // comment vous avez gérer vos rôle en base de données
    Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
    roles.add(new SimpleGrantedAuthority("USER"));
    roles.add(new SimpleGrantedAuthority("ADMIN"));
    
    return new User(user.getUsername(), user.getPassword(), roles);
  }
}
