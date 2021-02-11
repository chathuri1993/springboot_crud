package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
}
