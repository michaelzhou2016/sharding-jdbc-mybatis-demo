package com.vcredit.sharding.sharding;

import com.vcredit.sharding.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    List<Order> selectByUserId(Integer userId);

    List<Order> selectByOrderIdBetween(@Param("startOrderId") Long startOrderId, @Param("endOrderId") Long endOrderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}