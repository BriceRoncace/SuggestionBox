package gov.idaho.isp.suggestion.security;

import gov.idaho.isp.suggestion.user.User;
import gov.idaho.isp.suggestion.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomDatabaseUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomDatabaseUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameIgnoreCase(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username [" + username + "]");
    }
    return user;
  }
}
