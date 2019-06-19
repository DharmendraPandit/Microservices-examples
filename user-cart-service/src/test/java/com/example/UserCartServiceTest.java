package com.example.user;

import com.example.model.UserCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyObject;

@RunWith(SpringRunner.class)
public class UserCartServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private UserCartService service;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private UserCartRepository repository;

    UserCart userCart = null;
    List<Product> productList = null;

    @Before
    public void setUp() {
        userCart = create();
        productList = productList();
    }

    @Test
    public void update_save() {
        Double amount = userCart.getQuantity() * userCart.getAmount();
        when(productGateway.getProducts()).thenReturn(productList);
        when(repository.findByUsernameAndProductId(userCart.getUsername(), userCart.getProductId())).thenReturn(null);
        when(repository.save(userCart)).thenReturn(userCart);
        UserCart result = service.update(userCart.getUsername(), userCart);
        Assert.assertEquals(result.getProductId(), userCart.getProductId());
        Assert.assertEquals(result.getAmount(), amount);
    }

    @Test
    public void update_update() {
        UserCart alreadyInCart = create();
        alreadyInCart.setQuantity(1);
        int quantity = userCart.getQuantity() + alreadyInCart.getQuantity();
        Double amount = quantity * productList.get(0).getPrice();

        when(productGateway.getProducts()).thenReturn(productList);
        when(repository.findByUsernameAndProductId(userCart.getUsername(), userCart.getProductId())).thenReturn(alreadyInCart);
        when(repository.save(anyObject())).thenReturn(alreadyInCart);
        UserCart result = service.update(userCart.getUsername(), userCart);
        Assert.assertEquals(result.getProductId(), userCart.getProductId());
        Assert.assertEquals(result.getAmount(), amount);
        Assert.assertEquals(result.getQuantity(), quantity);
    }

    private UserCart create() {
        UserCart userCart = new UserCart();
        userCart.setId(UUID.randomUUID().toString());
        userCart.setProductId("5728c37b-43d3-472e-b367-e972ca0030ba");
        userCart.setProductName("Samsung");
        userCart.setQuantity(2);
        userCart.setAmount(28000.00);
        userCart.setUsername("dkpandit.08@gmail.com");
        return userCart;
    }

    private List<Product> productList() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductId("5728c37b-43d3-472e-b367-e972ca0030ba");
        product.setAvailableQuantity(10);
        product.setCategory("Mobile");
        product.setPrice(28000.00);
        product.setProductModel("A-70");
        product.setProductName("Samsung");

        productList.add(product);
        return productList;
    }
}
