package com.ra.controller.admin;

import com.ra.CustomException;
import com.ra.model.dto.receive.CategoryReceive;
import com.ra.model.dto.receive.CommonReceive;
import com.ra.model.dto.response.CommonResponse;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.model.enums.OrderStatus;
import com.ra.service.ICategoryService;
import com.ra.service.IOrderService;
import com.ra.service.Imp.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/order")
public class OrderController {

    @Autowired
    private ICategoryService ICategoryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IOrderService orderService;


    @GetMapping("/test")
    public ResponseEntity<?> test(){
        CommonReceive<List<CategoryReceive>> stringList= ICategoryService.getAll();
        return new ResponseEntity<>(stringList, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "5",name = "limit") int limit,
                                         @RequestParam(defaultValue = "0",name = "page") int page,
                                         @RequestParam(defaultValue = "id",name = "sortBy") String sort,
                                         @RequestParam(defaultValue = "asc",name = "order") String order){
        Pageable pageable=commonService.pagination(order,page,limit,sort);
        Page<Orders> ordersList=orderService.getAll(pageable);

        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,ordersList.getContent()), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId) throws CustomException {
        Orders orders=orderService.findById(orderId);
        List<OrderDetail> orderDetailList=orders.getListOrderDetail();
        return new ResponseEntity<>(orderDetailList,HttpStatus.OK);

    }

    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<?> getOrderByStatus(@PathVariable String orderStatus) throws CustomException {
        OrderStatus status=commonService.convertToOrderStatus(orderStatus);
        List<Orders> ordersList = orderService.findByProductStatus(status);
        return new ResponseEntity<>(ordersList,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId,@RequestBody String orderStatus) throws CustomException {
        OrderStatus orderStatusConvert=commonService.convertToOrderStatus(orderStatus);
        Orders orders=orderService.updateStatus(orderId,orderStatusConvert);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

}
