package com.ra.controller.user;

import com.ra.CustomException;
import com.ra.model.StaticData;
import com.ra.model.dto.response.CommonResponse;
import com.ra.model.entity.Orders;
import com.ra.model.entity.ShoppingCart;
import com.ra.service.IShoppingCartService;
import com.ra.service.Imp.CommonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;


    @Autowired
    private CommonService commonService;


    @GetMapping("")
    public ResponseEntity<?> getAllEnaBle() throws CustomException {
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,shoppingCartService.getAll()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addProductAndQuantity(@Valid @RequestBody ShoppingCart shoppingCart) throws CustomException {
        //Todo:Create function to throw exception for not found user(Optional)
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.CREATED,shoppingCartService.save(shoppingCart)), HttpStatus.CREATED);
    }

    @PutMapping("/{shoppingCartId}")
    public ResponseEntity<?> changeQuantity(@RequestBody Integer quantity,@PathVariable Integer shoppingCartId) throws CustomException {
        ShoppingCart shoppingCart=shoppingCartService.findById(shoppingCartId);
        shoppingCart.setQuantity(quantity);
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.CREATED,shoppingCartService.save(shoppingCart)),HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testGetAllProduct(){
        commonService.updateProductList();
        return new ResponseEntity<>(StaticData.productReceiveList,HttpStatus.OK);
    }

//Todo: create service delete one product and all product of user

//    @DeleteMapping("/{shoppingCartId}")
//    public ResponseEntity<?> delete(@PathVariable Integer shoppingCartId){
//        shoppingCartService.deleteOneProductById(shoppingCartId);
//        return new ResponseEntity<>("Success",HttpStatus.OK);
//    }
//
//    @DeleteMapping("")
//    public ResponseEntity<?> deleteAllShoppingCart() throws CustomException {
//        shoppingCartService.deleteAllProductInShoppingCartOfUser();
//        return new ResponseEntity<>("Success",HttpStatus.OK);
//    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Long userId) throws CustomException {
        Orders orders=shoppingCartService.checkout(userId);
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,orders),HttpStatus.OK);
    }
}
