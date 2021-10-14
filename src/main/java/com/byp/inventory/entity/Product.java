package com.byp.inventory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotEmpty
  private String name;
  @NotEmpty
  private String description;
  private long totalInventory;
  private long pricePerItem;
  private String currency;

  public Product(String name, String description, long totalInventory, long pricePerItem, String currency) {
    this.name = name;
    this.description = description;
    this.totalInventory = totalInventory;
    this.pricePerItem = pricePerItem;
    this.currency = currency;
  }

}