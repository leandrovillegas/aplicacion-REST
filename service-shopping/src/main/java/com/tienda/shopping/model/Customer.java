package com.tienda.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Customer {

    private Long id;

    private String numberId;

    private String firstName;

    private String lastName;

    private String email;

    private String photoUrl;

    private String state;

    private Region region;
}
