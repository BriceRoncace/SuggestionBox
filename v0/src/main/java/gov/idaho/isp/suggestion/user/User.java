package gov.idaho.isp.suggestion.user;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {
  public enum Auth {
    USER,
    ADMIN;
  }

  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "USER_AUTHORITIES")
  @Column(name="AUTHORITY")
  @Enumerated(EnumType.STRING)
  private Set<Auth> authorities;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public Set<GrantedAuthority> getAuthorities() {
    return authorities == null ? Collections.emptySet() : authorities.stream().map(Object::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }

  public void setAuthorities(Set<Auth> authorities) {
    this.authorities = authorities;
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
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + Objects.hashCode(this.username);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final User other = (User) obj;
    if (!Objects.equals(this.username, other.username)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "User{" + "username=" + username + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", authorities=" + authorities + '}';
  }
}