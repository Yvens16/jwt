package com.jwt_back17.jwt_back.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long amount;

  // DOUBLE ONE TO MANY
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "buyer_id")  
  private UserEntity buyer;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "seller_id")
  private UserEntity seller;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public UserEntity getBuyer() {
    return buyer;
  }

  public void setBuyer(UserEntity buyer) {
    this.buyer = buyer;
  }

  public UserEntity getSeller() {
    return seller;
  }

  public void setSeller(UserEntity seller) {
    this.seller = seller;
  }

}