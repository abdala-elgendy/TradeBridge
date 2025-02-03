package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Setter
@Getter
@Table(name="employees")
public class Employee  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long employeeId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private Date hireDate;

    private String title;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


}


