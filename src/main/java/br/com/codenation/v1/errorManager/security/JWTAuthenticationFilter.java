package br.com.codenation.v1.errorManager.security;

import br.com.codenation.v1.errorManager.user.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private JWTUtil jwtUtil;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {

    try {
      User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          creds.getUsername(), creds.getPassword(), new ArrayList<>()
      );

      return authenticationManager.authenticate(authToken);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String username = ((User) auth.getPrincipal()).getUsername();
    String token = jwtUtil.generateToken(username);
    res.addHeader("Authorization", "Bearer " + token);
  }

  private static class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().append(json());
    }

    private String json() {
      long date = new Date().getTime();
      return "{\"timestamp\": " + date + ", "
          + "\"status\": 401, "
          + "\"error\": \"Unauthorized\", "
          + "\"message\": \"Invalid email or password\", "
          + "\"path\": \"/login\"}";
    }
  }
}
