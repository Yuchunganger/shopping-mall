package com.judychen.springbootmall.rowmapper;

import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();

        product.setId(resultSet.getInt("id"));
        product.setTeacher(resultSet.getString("teacher"));
        product.setTitle(resultSet.getString("title"));
        product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));
        product.setCoverImageUrl(resultSet.getString("cover_image_url"));
        product.setTeacherImageUrl(resultSet.getString("teacher_image_url"));
        product.setCourseUrl(resultSet.getString("course_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setProposalPrice(resultSet.getInt("proposal_price"));
        product.setProposalDueTime(resultSet.getTimestamp("proposal_due_time"));
        product.setIsDiscount(resultSet.getBoolean("is_discount"));
        product.setDiscount(resultSet.getInt("discount"));
        product.setCriteriaNumSoldTickets(resultSet.getInt("criteria_num_sold_tickets"));
        product.setCurrentNumSoldTickets(resultSet.getInt("current_num_sold_tickets"));
        product.setStatus(resultSet.getString("status"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return product;
    }
}
