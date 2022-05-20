package com.judychen.springbootmall.rowmapper;

import com.judychen.springbootmall.model.OrderDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDatailsRowMapper implements RowMapper<OrderDetails> {
    @Override
    public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderDetailsId(rs.getInt("order_details_id"));
        orderDetails.setOrderId(rs.getInt("order_id"));
        orderDetails.setProductId(rs.getInt("product_id"));
        orderDetails.setQuantity(rs.getInt("quantity"));

        return orderDetails;
    }
}
