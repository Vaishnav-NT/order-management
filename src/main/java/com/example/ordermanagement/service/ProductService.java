package com.example.ordermanagement.service;

import com.example.ordermanagement.entity.Product;
import com.example.ordermanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new Exception("Product with id " + productId + "does not exist");
        }
    }

    public List<Product> getAllProductUsingBuilder(Integer pageNumber, Integer pageSize, String sortBy, Sort.Direction direction, String searchQuery) throws Exception {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Specification<Product> specification = Specification.where(null);
        if (searchQuery != null) {
            specification = specification.and((root, query, builder) -> builder.like(root.get("name"), "%" + searchQuery + "%"));
        }
        return productRepository.findAll(specification, pageable).getContent();
    }
}
