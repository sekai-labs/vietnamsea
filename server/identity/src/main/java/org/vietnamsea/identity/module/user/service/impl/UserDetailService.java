package org.vietnamsea.identity.module.user.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.exception.AuthException;
import org.vietnamsea.identity.infra.persistence.credential.repository.UserCredentialRepository;
import org.vietnamsea.identity.infra.persistence.user.entity.PermissionEntity;
import org.vietnamsea.identity.infra.persistence.user.entity.RoleEntity;
import org.vietnamsea.identity.infra.persistence.user.entity.UserEntity;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserCredentialRepository userCredentialRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null) {
      throw new UsernameNotFoundException("username shouldn't be empty");
    }
    UserEntity userEntity = null;
    try {
      var uuid = UUID.fromString(username);
      userEntity = userRepository.findById(uuid)
          .orElseThrow(() -> new AuthException("username is not founded"));
    } catch (IllegalArgumentException ex) {
      userEntity = userRepository.findByUsername(username)
          .orElseThrow(() -> new AuthException("username is not founded"));
    }
    var credential = userCredentialRepository
        .findByUserAndChangedAtIsNull(userEntity)
        .orElseThrow(() -> new AuthException("user has no active credential"));
    var userDetail = User.builder()
        .username(userEntity.getUsername())
        .password(credential.getHashPassword())
        .disabled(userEntity.getDisabled())
        .accountLocked(userEntity.getLocked())
        .authorities(getAuthorities(userEntity));
    return userDetail.build();
  }

  private Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
    var roles = user.getRoles().stream().map((r) -> r.getRole()).toList();
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    for (RoleEntity role : roles) {

      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
      var permissions = role.getPermissions().stream().map(p -> p.getPermission()).toList();

      for (PermissionEntity permission : permissions) {
        authorities.add(new SimpleGrantedAuthority(permission.getResource() + "." + permission.getAction()));
      }
    }
    return authorities;
  }
}
