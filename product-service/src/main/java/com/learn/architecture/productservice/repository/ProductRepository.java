package com.learn.architecture.productservice.repository;

import com.learn.architecture.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
