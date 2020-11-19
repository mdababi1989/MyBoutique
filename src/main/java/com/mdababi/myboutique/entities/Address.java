package com.mdababi.myboutique.entities;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Embeddable
public class Address {
    @Column(name = "address_1")
    private String address1;

    @Column(name="address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "postcode", length = 10, nullable = false)
    private String postcode;

    @NotNull
    @Column(name = "country", length = 2, nullable = false)
    private String country;
}
