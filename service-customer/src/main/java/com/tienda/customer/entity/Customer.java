package com.tienda.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The document number can't be empty")
    @Size(min = 8, max = 8, message = "The size of the document need to be 8")
    @Column(name = "number_id",unique = true, length = 8, nullable = false)
    private String numberId;

    @NotEmpty(message = "The First Name can't be empty")
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @NotEmpty(message = "The Last Name can't be empty")
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @NotEmpty(message = "The Email can't be empty")
    @Email
    @Column(unique = true ,nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "state")
    private String state;

    @NotNull(message = "The Region can't be empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
}
