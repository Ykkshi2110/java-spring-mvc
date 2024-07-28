package vn.hoidanit.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product laptop);
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(long id);
    Product findByImage(String image);
    Product deleteById(long id);
}
