package com.example.app.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "emp")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "address")
    String address;

    @Column(name = "phone")
    String phone;


}
