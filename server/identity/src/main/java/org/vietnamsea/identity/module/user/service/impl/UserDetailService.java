package org.vietnamsea.identity.module.user.service.impl;

import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.exception.AuthException;
import org.vietnamsea.identity.infra.persistence.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var userEntity = userRepository.findById(UUID.fromString(username))
        .orElseThrow(() -> new AuthException("username is not founded"));
    return User.builder()
        .username(userEntity.getUsername())
        .build();
  }

}
