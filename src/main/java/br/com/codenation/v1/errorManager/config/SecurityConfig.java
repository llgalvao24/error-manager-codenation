package br.com.codenation.v1.errorManager.config;

import br.com.codenation.v1.errorManager.security.JWTAuthenticationFilter;
import br.com.codenation.v1.errorManager.security.JWTAuthorizationFilter;
import br.com.codenation.v1.errorManager.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * This class configures spring-security + jwt token
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment env;

  @Qualifier("userDetailsServiceImpl")
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JWTUtil jwtUtil;

  // add here the endpoits that can be accessed without authentication
  private static final String[] PUBLIC_MATCHERS = {
      "/h2-console/**",
  };

  // add here the endpoits that can be accessed without authentication for reading
  private static final String[] PUBLIC_MATCHERS_GET = {
      "/api/v1/**"
  };

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // it allows h2 to work in test env
    if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
      http.headers().frameOptions().disable();
    }
    http.cors().and().csrf().disable();
    http.authorizeRequests()
        .antMatchers(PUBLIC_MATCHERS).permitAll()
        .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
        .anyRequest().authenticated();
    http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
    http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**");
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
