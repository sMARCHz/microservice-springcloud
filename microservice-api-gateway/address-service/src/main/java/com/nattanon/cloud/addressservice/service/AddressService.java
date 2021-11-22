package com.nattanon.cloud.addressservice.service;

import com.nattanon.cloud.addressservice.entity.Address;
import com.nattanon.cloud.addressservice.repository.AddressRepository;
import com.nattanon.cloud.addressservice.request.CreateAddressRequest;
import com.nattanon.cloud.addressservice.response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
        Address address = new Address();
        address.setCity(createAddressRequest.getCity());
        address.setStreet(createAddressRequest.getStreet());
        addressRepository.save(address);
        return new AddressResponse(address);
    }

    public AddressResponse getById(long id) {
        Address address = addressRepository.getById(id);
        return new AddressResponse(address);
    }
}
