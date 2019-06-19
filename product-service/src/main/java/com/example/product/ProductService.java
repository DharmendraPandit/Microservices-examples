package com.example.product;

import com.example.model.Product;
import com.example.model.ProductException;
import com.example.model.ProductMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dharmendra Pandit.
 */
@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        log.info("Request got for save product name: {}", product.getProductName());
        isProductValid(product);
        try{
            product.setProductId(UUID.randomUUID().toString());
            return productRepository.save(product);
        } catch (Exception ex) {
            log.error("Exception occurs while saving product for name: {}", product.getProductName());
            throw new ProductException(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }

    public Product update(Product product) {
        log.info("Request got for update product name: {}", product.getProductName());
        isProductValid(product);
        Optional<Product> dbProduct = productRepository.findById(product.getProductId());
        if(dbProduct.isPresent()) {
            try{
                Product updateProduct = dbProduct.get();
                updateProduct.setProductName(product.getProductName());
                updateProduct.setAvailableQuantity(product.getAvailableQuantity());
                updateProduct.setCategory(product.getCategory());
                updateProduct.setPrice(product.getPrice());
                updateProduct.setProductModel(product.getProductModel());
                return productRepository.save(product);
            } catch (Exception ex) {
                log.error("Exception occurs while updating product for name: {}", product.getProductName());
                throw new ProductException(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            }
        } else {
            log.error("Exception occurs while updating product for name: {}", product.getProductName());
            throw new ProductException(HttpStatus.BAD_REQUEST.value(), "Product id is not valid");
        }

    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }


    private void isProductValid(Product product) {
        if(StringUtils.isEmpty(product.getProductName()) || StringUtils.isEmpty(product.getAvailableQuantity()) ||
                StringUtils.isEmpty(product.getPrice()) || StringUtils.isEmpty(product.getProductModel())) {
            log.error("productName/quantity/price/model could not found");
            throw new ProductException(HttpStatus.BAD_REQUEST.value(), ProductMessage.PRODUCT_MANDATORY_FIELDS.getMessage());
        }
    }

}
