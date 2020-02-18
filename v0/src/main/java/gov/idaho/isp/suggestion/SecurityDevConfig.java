package gov.idaho.isp.suggestion;

import gov.idaho.isp.suggestion.security.CsrfExceptionAccessDeniedHandler;
import gov.idaho.isp.suggestion.security.CustomDatabaseUserDetailsService;
import gov.idaho.isp.suggestion.security.CustomInMemoryUserDetailsManager;
import gov.idaho.isp.suggestion.user.User;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Profile("dev")
@Configuration
@EnableWebSecurity
public class SecurityDevConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomDatabaseUserDetailsService customDatabaseUserDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/assets/**").permitAll()
      .antMatchers("/h2-console/**").permitAll()
      .antMatchers("/**").permitAll()
      .anyRequest().authenticated()
      .and().formLogin().loginPage("/login").permitAll()
      .and().logout().logoutUrl("/logout").permitAll()
      .and().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());

    allowAdminAccessToH2Console(http);
  }

  private void allowAdminAccessToH2Console(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/h2-console/**");
    http.headers().frameOptions().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    configureDatabaseAuthentication(auth);
    configureInMemoryAuthentication(auth);
  }
  
  private void configureDatabaseAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customDatabaseUserDetailsService).passwordEncoder(getPasswordEncoder());
  }
  
  private void configureInMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(new CustomInMemoryUserDetailsManager(buildInMemoryUser()));
  }
  
  @Bean
  public DaoAuthenticationProvider getDaoAuthProvider(CustomDatabaseUserDetailsService customDatabaseUserDetailsService ) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customDatabaseUserDetailsService);
    provider.setPasswordEncoder(getPasswordEncoder());
    return provider;
  }
  
  private User buildInMemoryUser() {
    User user = new User();
    user.setAuthorities(Set.of(User.Auth.USER));
    user.setFirstName("User");
    user.setLastName("Local");
    user.setEmail("blank-email@email.com");
    user.setUsername("user");
    user.setPassword(getPasswordEncoder().encode("u"));
    return user;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private AccessDeniedHandler getAccessDeniedHandler() {
    return new CsrfExceptionAccessDeniedHandler();
  }
}
