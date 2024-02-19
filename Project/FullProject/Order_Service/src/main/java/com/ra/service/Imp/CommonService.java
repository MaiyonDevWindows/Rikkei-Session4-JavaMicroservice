package com.ra.service.Imp;

import com.ra.CustomException;
import com.ra.model.StaticData;
import com.ra.model.dto.receive.CategoryReceive;
import com.ra.model.dto.receive.CommonReceive;
import com.ra.model.dto.receive.ProductReceive;
import com.ra.model.enums.OrderStatus;
import com.ra.service.ICategoryService;
import com.ra.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommonService {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;
    public void updateProductList(){
        CommonReceive<List<ProductReceive>> stringList= productService.getAll();
        StaticData.productReceiveList=stringList.getBody();
    }

    public void updateCategoryList(){
        CommonReceive<List<CategoryReceive>> stringList= categoryService.getAll();
        StaticData.categoryReceiveList=stringList.getBody();

    }
    public ProductReceive findProductById(Long productId){
        updateProductList();
        ProductReceive productReceiveFinal=new ProductReceive();
        for(ProductReceive productReceive: StaticData.productReceiveList){
            if(productReceive.getId().equals(productId)){
                System.out.println("Product Found ");
                return productReceive;
            }
        }
        System.out.println("No Product Found");
        return productReceiveFinal;
    }
    public Pageable pagination(String order, Integer page, Integer limit, String sort){
        Pageable pageable;
        if(order.equals("asc")){
            pageable= PageRequest.of(page,limit, Sort.by(sort).ascending());
        }else {
            pageable= PageRequest.of(page,limit,Sort.by(sort).descending());
        }
        return pageable;
    }

    public OrderStatus convertToOrderStatus(String status) throws CustomException {
        OrderStatus orderStatusConvert=OrderStatus.valueOf(status);
        if(orderStatusConvert==OrderStatus.CANCEL||orderStatusConvert==OrderStatus.CONFIRM||orderStatusConvert==OrderStatus.DELIVERY||orderStatusConvert==OrderStatus.DENIED || orderStatusConvert==OrderStatus.SUCCESS || orderStatusConvert==OrderStatus.WAITING){
            return orderStatusConvert;
        }else {
            throw new CustomException("No Order Status found for given status");
        }
    }

}
