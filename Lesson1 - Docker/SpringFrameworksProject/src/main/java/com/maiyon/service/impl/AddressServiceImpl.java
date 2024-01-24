package com.maiyon.service.impl;

import com.maiyon.model.dto.request.AddressRequest;
import com.maiyon.model.dto.response.AddressResponse;
import com.maiyon.model.entity.Address;
import com.maiyon.model.entity.User;
import com.maiyon.repository.AddressRepository;
import com.maiyon.repository.UserRepository;
import com.maiyon.security.user_principal.UserPrincipal;
import com.maiyon.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    private final Logger logger =
            LoggerFactory.getLogger(this.getClass());
    @Override
    public Page<AddressResponse> findAll(Pageable pageable) {
        List<Address> addresses = addressRepository.findAll();
        List<AddressResponse> addressResponses = addresses.stream().map((this::builderAddressResponse)).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), addressResponses.size());
        return PageableExecutionUtils.getPage(addressResponses.subList(start, end), pageable, addressResponses::size);
    }

    @Override
    public Optional<AddressResponse> findById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if(address.isPresent()){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
                Optional<User> user = userRepository.findUserByUserId(userPrincipal.getUser().getUserId());
                if(user.isPresent()){
                    if(Objects.equals(user.get().getUserId(), address.get().getUser().getUserId()))
                        return address.map(this::builderAddressResponse);
                    logger.error("Address is not owned by user.");
                }
                logger.error("User is not found");
            }
            logger.error("Can not get user data.");
        }
        return Optional.empty();
    }

    @Override
    public Optional<AddressResponse> save(AddressRequest addressRequest) {
        Address address = builderAddress(addressRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            Optional<User> user = userRepository.findUserByUserId(userPrincipal.getUser().getUserId());
            user.ifPresent(address::setUser);
            Address saveAddress = addressRepository.save(address);
            return Optional.ofNullable(builderAddressResponse(saveAddress));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if(address.isEmpty())  return false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            Optional<User> user = userRepository.findUserByUserId(userPrincipal.getUser().getUserId());
            if(user.isPresent()){
                if(Objects.equals(user.get().getUserId(), address.get().getUser().getUserId())){
                    addressRepository.deleteById(id);
                    logger.info("User with user id {} just remove address {}",
                            user.get().getUserId(), address.get().getFullAddress());
                    return true;
                }
                logger.error("Address is not owned by user.");
            }
            logger.error("User is not found");
        }
        logger.error("Can not get user data.");
        return false;
    }
    public AddressResponse builderAddressResponse(Address address){
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .fullAddress(address.getFullAddress())
                .phone(address.getPhone())
                .receiveName(address.getReceiveName())
                .build();
    }
    public Address builderAddress(AddressRequest addressRequest) {
        return Address.builder()
                .addressId(addressRequest.getAddressId())
                .fullAddress(addressRequest.getFullAddress())
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiveName())
                .build();
    }
}
