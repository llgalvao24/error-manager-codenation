package br.com.codenation.v1.errorManager.security;

import br.com.codenation.v1.errorManager.enums.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSecurity implements UserDetails {

  private Long id;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSecurity(){
  }

  public UserSecurity(Long id, String username, String password, Set<Profile> profiles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = profiles
        .stream()
        .map(p -> new SimpleGrantedAuthority(
            p.getDescription())).collect(Collectors.toList()
        );
  }

  public Long getId(){
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public boolean hasRole(Profile profile) {
    return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
  }
}
