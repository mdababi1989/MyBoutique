package com.mdababi.myboutique.services;

import com.mdababi.myboutique.dto.AddressDto;
import com.mdababi.myboutique.entities.Address;

public class AddressService {
    public static AddressDto mapToDto(Address address) {
        if (address != null) {
            return new AddressDto(
                    address.getAddress1(),
                    address.getAddress2(),
                    address.getCity(),
                    address.getPostcode(),
                    address.getCountry()
            );
        }
        return null;
    }
}
