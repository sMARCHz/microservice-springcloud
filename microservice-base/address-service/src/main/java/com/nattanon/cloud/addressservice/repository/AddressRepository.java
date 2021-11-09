package com.nattanon.cloud.addressservice.repository;

import com.nattanon.cloud.addressservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
