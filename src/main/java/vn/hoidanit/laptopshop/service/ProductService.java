package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }
    
    public Product handleSaveProduct (Product laptop){
        return this.productRepository.save(laptop);
    }

    public Optional<Product> getProductById (long id){
        return this.productRepository.findById(id);
    }

    public Optional<Product> fetchProductById(long id){
        return this.productRepository.findById(id);
    }

    public Product getImage(String image){
        return this.productRepository.findByImage(image);
    }

    public void handleDeleteProductById(long id){
        this.productRepository.deleteById(id);
    }
}
