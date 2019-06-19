package com.example.user;

import com.example.model.UserCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCartRepository extends MongoRepository<UserCart, String> {
    public UserCart findByUsernameAndProductId(String username, String productId);
    public List<UserCart> findByUsername(String username);
}
