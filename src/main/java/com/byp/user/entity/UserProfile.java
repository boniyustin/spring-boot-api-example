package com.byp.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class UserProfile {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotEmpty
  private String userName;
  @NotEmpty
  private String password;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String address;
  private Date birthDate;
  private long lastLoginTimestamp;

  public UserProfile(String userName, String password, String fullName, String email,
                     String phoneNumber, String address, Date birthDate, long lastLoginTimestamp) {
    this.userName = userName;
    this.password = password;
    this.fullName = fullName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.birthDate = birthDate;
    this.lastLoginTimestamp = lastLoginTimestamp;
  }
}