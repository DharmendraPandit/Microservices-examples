package com.example.user;

import com.example.model.UserCart;
import com.example.model.UserCartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Dharmendra Pandit.
 */
@Service
public class UserCartService {

    private static final Logger log = LoggerFactory.getLogger(UserCartService.class);

    @Autowired
    private UserCartRepository repository;

    @Autowired
    private ProductGateway productGateway;

    public UserCart update(String username, UserCart userCart) {

        List<Product> productList = productGateway.getProducts();
        if(!productList.isEmpty()) {

            Product product = productList.stream().filter(prod -> userCart.getProductId().equalsIgnoreCase(prod.getProductId())).findAny().orElse(null);
            if(product != null) {
                UserCart existingCarts = findByUsernameAndProductId(username, userCart.getProductId());

                if(existingCarts != null) {
                    existingCarts.setQuantity(existingCarts.getQuantity() + userCart.getQuantity());
                    isProductAvailable(product, existingCarts);
                    existingCarts.setAmount(product.getPrice() * existingCarts.getQuantity());
                    return save(existingCarts);
                } else {
                    isProductAvailable(product, userCart);
                    userCart.setId(UUID.randomUUID().toString());
                    userCart.setUsername(username);
                    userCart.setAmount(userCart.getQuantity() * product.getPrice());
                    return save(userCart);
                }

            } else {
                log.error("Requested product does not exit");
                throw new UserCartException(HttpStatus.BAD_REQUEST.value(), "Requested product does not exit");
            }

        } else {
            log.error("Products not avaialble");
            throw new UserCartException(HttpStatus.BAD_REQUEST.value(), "Product not found");
        }
    }

    private void isProductAvailable(Product product, UserCart requestedProduct) {
        if(product.getAvailableQuantity() < requestedProduct.getQuantity()) {
            log.error("Sufficient products not available");
            throw new UserCartException(HttpStatus.BAD_REQUEST.value(), "Sufficient products not available");
        }
    }

    private UserCart findByUsernameAndProductId(String username, String productId) {
         return repository.findByUsernameAndProductId(username, productId);
    }


    public List<UserCart> getAll(String username) {
        return repository.findByUsername(username);
    }

    private UserCart save(UserCart userCart) {
        try {
            return repository.save(userCart);
        } catch (Exception ex) {
            log.error("Exception occurs while saving user cart for productId: {}", userCart.getProductId());
            throw new UserCartException(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }

}
