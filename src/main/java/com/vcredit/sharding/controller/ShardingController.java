package com.vcredit.sharding.controller;

import com.vcredit.sharding.model.Order;
import com.vcredit.sharding.model.OrderItem;
import com.vcredit.sharding.mapper.OrderItemMapper;
import com.vcredit.sharding.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author zhouliliang
 * @Description:
 * @Date: Created in 2018/8/13 17:49
 */
@RestController
public class ShardingController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @GetMapping(path = "queryOrderByUserId")
    public List<Order> queryOrder(@RequestParam("userId") Integer userId){
        return orderMapper.selectByUserId(userId);
    }

    @GetMapping(path = "queryOrderIdBetween")
    public List<Order> queryOrder(@RequestParam("startOrderId") Long startOrderId, @RequestParam("endOrderId") Long endOrderId){
        return orderMapper.selectByOrderIdBetween(startOrderId, endOrderId);
    }

    @GetMapping(path = "insertData")
    public String createData(){
        Order order = new Order(1L, 1, "1");
        OrderItem orderItem1 = new OrderItem(1L, 1L, 1);
        OrderItem orderItem2 = new OrderItem(2L, 1L, 1);
        orderMapper.insert(order);
        orderItemMapper.insert(orderItem1);
        orderItemMapper.insert(orderItem2);
        Order order2 = new Order(2L, 1, "1");
        OrderItem orderItem21 = new OrderItem(3L, 2L, 1);
        OrderItem orderItem22 = new OrderItem(4L, 2L, 1);
        orderMapper.insert(order2);
        orderItemMapper.insert(orderItem21);
        orderItemMapper.insert(orderItem22);
        Order order3 = new Order(3L, 2, "1");
        OrderItem orderItem31 = new OrderItem(5L, 3L, 2);
        OrderItem orderItem32 = new OrderItem(6L, 3L, 2);
        orderMapper.insert(order3);
        orderItemMapper.insert(orderItem31);
        orderItemMapper.insert(orderItem32);
        Order order4 = new Order(4L, 2, "1");
        OrderItem orderItem41 = new OrderItem(7L, 4L, 2);
        OrderItem orderItem42 = new OrderItem(8L, 4L, 2);
        orderMapper.insert(order4);
        orderItemMapper.insert(orderItem41);
        orderItemMapper.insert(orderItem42);
        return "success";
    }
}
