package com.maiyon.service;

import com.maiyon.model.dto.request.WishListRequest;
import com.maiyon.model.dto.response.WishListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WishListService {
    Page<WishListResponse> findAll(Pageable pageable);
    Optional<WishListResponse> save(WishListRequest wishListRequest);
    Boolean delete(Long id);
}
