package com.example.user;

import com.example.model.UserCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dharmendra Pandit.
 */
@Repository
public interface UserCartInterface extends MongoRepository<UserCart, String> {
}
