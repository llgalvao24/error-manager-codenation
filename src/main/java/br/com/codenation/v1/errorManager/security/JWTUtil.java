package br.com.codenation.v1.errorManager.security;

import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.exception.OwnershipException;
import br.com.codenation.v1.errorManager.exception.TokenNotValidOrNotInformedException;
import br.com.codenation.v1.errorManager.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  @Autowired
  private UserRepository userRepository;

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, secret.getBytes())
        .compact();
  }

  public boolean validToken(String token) {
    Claims claims = getClaims(token);
    if (claims != null){
      String username  = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      Date now = new Date(System.currentTimeMillis());

      return username != null && expirationDate != null && now.before(expirationDate);
    }

    return false;
  }


  public String getUsername(String token) {
    Claims claims = getClaims(token);

    if (claims != null){
      return claims.getSubject();
    }

    return null;
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    } catch (Exception e){
      return null;
    }
  }

  public User getAuthenticatedUser(){
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    if (username.equals("anonymousUser")){
      throw new TokenNotValidOrNotInformedException();
    }
    return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public void isAuthorized(User user){
    User authenticatedUser = this.getAuthenticatedUser();
    if (!user.getId().equals(authenticatedUser.getId())){
      throw new OwnershipException();
    }
  }

}
