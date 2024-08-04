package vn.hoidanit.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    public static Specification<Product> minPrice (double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE).as(Double.class), price);
    }

    public static Specification<Product> maxPrice (double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE).as(Double.class), price);
    }

    public static Specification<Product> factoryName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(Product_.FACTORY), name);
        };
    }

    public static Specification<Product> matchFactory (List<String>factory){

        // thuộc tính value của .in() dùng để thêm 1 danh sách vào cần kiểm tra 
        // .in() kiểm tra xem 1 biểu thức có nằm trong 1 tập hợp giá trị cụ thể hay không
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
    }

    public static Specification<Product> matchTarget (List<String>target){

        // thuộc tính value của .in() dùng để thêm 1 danh sách vào cần kiểm tra 
        // .in() kiểm tra xem 1 biểu thức có nằm trong 1 tập hợp giá trị cụ thể hay không
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
    }

    public static Specification<Product> rangePrice(double minPrice, double maxPrice){
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.ge(root.get(Product_.PRICE).as(Double.class), minPrice), criteriaBuilder.le(root.get(Product_.PRICE).as(Double.class), maxPrice));
    }

    public static Specification<Product> matchMultiplePrice(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), minPrice, maxPrice);
    }

    
}
