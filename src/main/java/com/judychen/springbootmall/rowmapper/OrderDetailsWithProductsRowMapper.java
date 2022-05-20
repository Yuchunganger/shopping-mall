package com.judychen.springbootmall.rowmapper;

import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.model.OrderDetailsWithProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsWithProductsRowMapper implements RowMapper<OrderDetailsWithProduct> {

    @Override
    public OrderDetailsWithProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetailsWithProduct orderDetailsWithProduct = new OrderDetailsWithProduct();

        orderDetailsWithProduct.setOrderDetailsId(rs.getInt("order_details_id"));
        orderDetailsWithProduct.setOrderId(rs.getInt("order_id"));
        orderDetailsWithProduct.setProductId(rs.getInt("product_id"));
        orderDetailsWithProduct.setProductName(rs.getString("product_name"));
        orderDetailsWithProduct.setCategory(ProductCategory.valueOf(rs.getString("category")));
        orderDetailsWithProduct.setImageUrl(rs.getString("image_url"));
        orderDetailsWithProduct.setPrice(rs.getInt("price"));
        orderDetailsWithProduct.setQuantity(rs.getInt("quantity"));
        orderDetailsWithProduct.setDescription(rs.getString("description"));

        return orderDetailsWithProduct;
    }
}
