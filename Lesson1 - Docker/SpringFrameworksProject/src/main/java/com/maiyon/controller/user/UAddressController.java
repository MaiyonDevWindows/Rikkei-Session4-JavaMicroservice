package com.maiyon.controller.user;

import com.maiyon.model.dto.request.AddressRequest;
import com.maiyon.model.dto.response.AddressResponse;
import com.maiyon.service.AddressService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user/account/address")
public class UAddressController {
    @Autowired
    private AddressService addressService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ResponseEntity<?> getAllAddresses(
            @RequestParam(defaultValue = "5", name = "limit") Integer limit,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "fullAddress", name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<AddressResponse> addressResponses = addressService.findAll(pageable);
            if(!addressResponses.isEmpty()){
                logger.info("User - Get own addresses at page {} - successful.", page);
                return new ResponseEntity<>(addressResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Addresses page is empty.", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            logger.error("Admin - Get own addresses at page {} - failure.", page);
            return new ResponseEntity<>("Addresses page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable("addressId") Long addressId){
        Optional<AddressResponse> addressResponse = addressService.findById(addressId);
        if(addressResponse.isPresent()){
            logger.info("User - Get address by id: {} - successful.", addressId);
            return new ResponseEntity<>(addressResponse, HttpStatus.OK);
        }
        logger.error("User - Get address by id: {} - failure.", addressId);
        return new ResponseEntity<>("Address is not exists.", HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody @Valid AddressRequest addressRequest){
        Optional<AddressResponse> addressResponse = addressService.save(addressRequest);
        if(addressResponse.isPresent()){
            logger.info("User - Create address - successful.");
            return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
        }
        logger.error("User - Create address - failure.");
        return new ResponseEntity<>("Can not create address.", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("addressId") Long addressId){
        if(addressService.deleteById(addressId)){
            logger.info("User - Delete address - successful.");
            return new ResponseEntity<>("Delete success.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete failure.", HttpStatus.NOT_FOUND);
    }
}
