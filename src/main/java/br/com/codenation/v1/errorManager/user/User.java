package br.com.codenation.v1.errorManager.user;

import br.com.codenation.v1.errorManager.enums.Profile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable, UserDetails {
  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Email
  @NotNull
  @Size(max = 254)
  @Column(name = "email")
  private String email;

  @NotNull
  @Size(max = 64)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  @Column(name = "created_at", columnDefinition = "timestamp default now()")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PROFILES")
  private Set<Integer> profiles = new HashSet<>();

  @Transient
  private Collection<? extends GrantedAuthority> authorities;

  public User() {
    addProfile(Profile.USER);
    addProfile(Profile.ADMIN);
  }

  public User(String email, String password, Set<Profile> profiles){
    super();
    this.email = email;
    this.password = password;
    this.authorities = profiles
        .stream()
        .map(p -> new SimpleGrantedAuthority(
            p.getDescription())).collect(Collectors.toList()
        );
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password= password;
  }

  public Set<Profile> getProfile() {
    return profiles.stream()
        .map(Profile::toEnum)
        .collect(Collectors.toSet());
  }

  public void addProfile(Profile profile) {
    profiles.add(profile.getCode());
  }

  public boolean hasRole(Profile profile) {
    return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
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

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
