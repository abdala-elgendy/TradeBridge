package com.global.demo.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
    private String role;
    private boolean status;

}
