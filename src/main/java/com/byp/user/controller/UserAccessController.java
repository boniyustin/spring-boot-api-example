package com.byp.user.controller;

import com.byp.user.entity.UserProfile;
import com.byp.user.entity.UserSession;
import com.byp.user.repository.UserProfileRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserAccessController {
  private final UserProfileRepository userProfileRepository;
  private static final int EXPIRED_TIME_MILISECONDS = 600 * 1000;

  private BCryptPasswordEncoder encoder;

  public UserAccessController(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
    this.encoder = new BCryptPasswordEncoder();
  }

  @PostMapping("user")
  public UserSession login(@RequestParam("user") String username, @RequestParam("password") String password) {
    //String encryptedPassword = encoder.encode(pwd);
    // todo byp: find how to encrypt password
    UserProfile userProfile = userProfileRepository.findByUserNameAndPassword(username, password);
    if (userProfile == null) {
      return null;
    }
    
    String token = getJWTToken(username);
    UserSession user = new UserSession();
    user.setUserId(1L);
    user.setUserToken(token);
    return user;

  }

  private String getJWTToken(String username) {
    String secretKey = "mySecretKey";
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
      .commaSeparatedStringToAuthorityList("ROLE_USER");

    String token = Jwts
      .builder()
      .setId("softtekJWT")
      .setSubject(username)
      .claim("authorities",
        grantedAuthorities.stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList()))
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_TIME_MILISECONDS))
      .signWith(SignatureAlgorithm.HS512,
        secretKey.getBytes()).compact();

    return "Bearer " + token;
  }
}
