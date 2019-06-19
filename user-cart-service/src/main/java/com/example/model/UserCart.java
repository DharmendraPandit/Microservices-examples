package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * @author Dharmendra Pandit.
 */
@Data
@Document(collection = "USER_CARTS")
public class UserCart {
    @Id
    private String id;
    private String productId;
    private String productName;
    private Double amount;
    private int quantity;
    private String username;

}
