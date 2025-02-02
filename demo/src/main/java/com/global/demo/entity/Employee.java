package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Setter
@Getter
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Integer employeeId;

    private String firstName;
    private String lastName;
    private String title;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


}


