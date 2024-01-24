package com.maiyon.service;

import com.maiyon.model.dto.request.AddressRequest;
import com.maiyon.model.dto.response.AddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AddressService {
    Page<AddressResponse> findAll(Pageable pageable);
    Optional<AddressResponse> findById(Long id);
    Optional<AddressResponse> save(AddressRequest addressRequest);
    Boolean deleteById(Long id);
}
