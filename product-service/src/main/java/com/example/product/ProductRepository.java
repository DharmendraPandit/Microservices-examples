package com.example.product;

import com.example.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dharmendra Pandit.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
