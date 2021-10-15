package com.byp.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserSession {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotEmpty
  private Long userId;
  @NotEmpty
  private String userToken;
  private String expiryTime;

  public UserSession(@NotEmpty Long userId, @NotEmpty String userToken, String expiryTime) {
    this.userId = userId;
    this.userToken = userToken;
    this.expiryTime = expiryTime;
  }
}