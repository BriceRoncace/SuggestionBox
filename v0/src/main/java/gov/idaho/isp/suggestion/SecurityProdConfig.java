package gov.idaho.isp.suggestion;

import gov.idaho.isp.suggestion.security.CsrfExceptionAccessDeniedHandler;
import gov.idaho.isp.suggestion.security.CustomDatabaseUserDetailsService;
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

@Profile("prod")
@Configuration
@EnableWebSecurity
public class SecurityProdConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomDatabaseUserDetailsService customDatabaseUserDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/assets/**").permitAll()
      .anyRequest().authenticated()
      .and().formLogin().loginPage("/login").permitAll()
      .and().logout().logoutUrl("/logout").permitAll()
      .and().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customDatabaseUserDetailsService).passwordEncoder(getPasswordEncoder());
  }

  @Bean
  public DaoAuthenticationProvider getDaoAuthProvider(CustomDatabaseUserDetailsService customDatabaseUserDetailsService ) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customDatabaseUserDetailsService);
    provider.setPasswordEncoder(getPasswordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private AccessDeniedHandler getAccessDeniedHandler() {
    return new CsrfExceptionAccessDeniedHandler();
  }
}
