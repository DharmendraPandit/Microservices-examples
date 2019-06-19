package com.example.product;

import com.example.model.Product;
import com.example.model.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dharmendra Pandit.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @ExceptionHandler(ProductException.class)
    void handleUserException(ProductException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody Product product) {
        return new ResponseEntity<>(service.update(product), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
