package com.judychen.springbootmall.dao.impl;

import com.judychen.springbootmall.dao.ProductDao;
import com.judychen.springbootmall.dto.ProductQueryParams;
import com.judychen.springbootmall.dto.ProductRequest;
import com.judychen.springbootmall.model.Product;
import com.judychen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO course (teacher, title, category, cover_image_url, teacher_image_url," +
                " course_url, price, proposal_price, proposal_due_time, is_discount," +
                " discount, criteria_num_sold_tickets, current_num_sold_tickets," +
                " status, description, created_date, last_modified_date) VALUES (:teacher, :title, :category, :coverImageUrl, :teacherImageUrl," +
                " :courseUrl, :price, :proposalPrice, :proposalDueTime, :isDiscount," +
                " :discount, :criteriaNumSoldTickets, :currentNumSoldTickets," +
                " :status, :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("teacher", productRequest.getTeacher());
        map.put("title", productRequest.getTitle());
        map.put("category", productRequest.getCategory().toString());
        map.put("coverImageUrl", productRequest.getCoverImageUrl());
        map.put("teacherImageUrl", productRequest.getTeacherImageUrl());
        map.put("courseUrl", productRequest.getCourseUrl());
        map.put("price", productRequest.getPrice());
        map.put("proposalPrice", productRequest.getProposalPrice());
        map.put("proposalDueTime", productRequest.getProposalDueTime());
        map.put("isDiscount", productRequest.getIsDiscount());
        map.put("discount", productRequest.getDiscount());
        map.put("criteriaNumSoldTickets", productRequest.getCriteriaNumSoldTickets());
        map.put("currentNumSoldTickets", productRequest.getCurrentNumSoldTickets());
        map.put("status", productRequest.getStatus());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer id = keyHolder.getKey().intValue();

        return id;
    }

    @Override
    public void updateProductById(Integer id,
                                     ProductRequest productRequest) {
        String sql = "UPDATE course SET teacher = :teacher, title = :title, category = :category," +
                " cover_image_url = :coverImageUrl, teacher_image_url = :teacherImageUrl," +
                " course_url = :courseUrl, price = :price, proposal_price = :proposalPrice," +
                " proposal_due_time = :proposalDueTime, is_discount = :isDiscount," +
                " discount = :discount, criteria_num_sold_tickets = :criteriaNumSoldTickets," +
                " current_num_sold_tickets = :currentNumSoldTickets, status = :status," +
                " description = :description, last_modified_date = :lastModifiedDate" +
                " WHERE id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("teacher", productRequest.getTeacher());
        map.put("title", productRequest.getTitle());
        map.put("category", productRequest.getCategory().toString());
        map.put("coverImageUrl", productRequest.getCoverImageUrl());
        map.put("teacherImageUrl", productRequest.getTeacherImageUrl());
        map.put("courseUrl", productRequest.getCourseUrl());
        map.put("price", productRequest.getPrice());
        map.put("proposalPrice", productRequest.getProposalPrice());
        map.put("proposalDueTime", productRequest.getProposalDueTime());
        map.put("isDiscount", productRequest.getIsDiscount());
        map.put("discount", productRequest.getDiscount());
        map.put("criteriaNumSoldTickets", productRequest.getCriteriaNumSoldTickets());
        map.put("currentNumSoldTickets", productRequest.getCurrentNumSoldTickets());
        map.put("status", productRequest.getStatus());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }


    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM course WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件 Filtering
        sql = addFilteringSql(sql, map, productQueryParams);

        // 取得總筆數
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT id, teacher, title, category, cover_image_url, teacher_image_url," +
                " course_url, price, proposal_price, proposal_due_time, is_discount," +
                " discount, criteria_num_sold_tickets, current_num_sold_tickets," +
                " status, description, created_date, last_modified_date " +
                "FROM course WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件 Filtering
        sql = addFilteringSql(sql, map, productQueryParams);

        // 排序 Sorting
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        // 分頁 Pagination
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> products = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return products;
    }

    private String addFilteringSql(String sql, Map<String,Object> map, ProductQueryParams productQueryParams){

        // 查詢條件 Filtering
        if(productQueryParams.getCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category", productQueryParams.getCategory().toString());
        }

        if(productQueryParams.getSearch() != null){
            sql = sql + " AND title LIKE :search";
            map.put("search", '%' + productQueryParams.getSearch() + '%');
        }

        return sql;
    }

    @Override
    public Product getProductById(Integer id) {
        String sql = "SELECT id, teacher, title, category, cover_image_url, teacher_image_url," +
                        " course_url, price, proposal_price, proposal_due_time, is_discount," +
                        " discount, criteria_num_sold_tickets, current_num_sold_tickets," +
                        " status, description, created_date, last_modified_date " +
                        "FROM course WHERE id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<Product> query = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (query.size() > 0){
            return query.get(0);
        }else {
            return null;
        }


    }

    @Override
    public void deleteProduct(Integer id) {
        String sql = "DELETE FROM course WHERE id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
