package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping ("/cart")
public class ShoppingCartController {
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        try {
            // get the currently logged-in username
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            if (user == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");


            return shoppingCartDao.getByUserId(userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    @GetMapping("/empty")
    public ShoppingCart getEmptyCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return shoppingCartDao.getEmptyCart(userId);

        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    // add a POST method to add a product to the cart - the url should be
    @PostMapping("/products/{productId}")
    public ShoppingCart addProductToCart(@PathVariable int productId, @RequestBody ShoppingCartItem item,
                                         Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();
            Product product = productDao.getById(productId);

            if (product == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }

            int quantity = item.getQuantity();
            return shoppingCartDao.createOrder(userId, productId, quantity);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add product to cart.");
        }
    }

    @PostMapping("/{productId}/")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCart addAnotherProduct(@PathVariable int productId, Principal principal) {
        try {

                String userName = principal.getName();
                User user = userDao.getByUserName(userName);

                if (productDao.getById(productId) == null)
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

                return shoppingCartDao.addAnotherProductToCart(user.getId(), productId);

            } catch(Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to increment product.");
            }
        }


        // add a PUT method to update an existing product in the cart - the url should be
        // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
        // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
        @PutMapping("/{productId}")
        public void updateProductQuantity ( @PathVariable int productId,
                                            @RequestBody ShoppingCartItem item,
                                                          Principal principal){
            try {
                User user = userDao.getByUserName(principal.getName());
                int userId = user.getId();

                shoppingCartDao.updateProductQuantity(userId, productId, item.getQuantity());

            } catch (ResponseStatusException e) {
                throw e;
            }
        }


        // add a DELETE method to clear all products from the current users cart
        // https://localhost:8080/cart
        @DeleteMapping("/products/{productId}")
        public void removeProduct ( @PathVariable int productId, Principal principal){
            try {
                String userName = principal.getName();
                User user = userDao.getByUserName(userName);

                int userId = user.getId();

                if (productDao.getById(productId) == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.");
                }

                shoppingCartDao.removeProductById(userId, productId);
            } catch (ResponseStatusException e) {
                throw e;
            }
        }

        @DeleteMapping
        public ShoppingCart clearCart (Principal principal){
            try {
                String userName = principal.getName();
                User user = userDao.getByUserName(userName);

                int userId = user.getId();
                return shoppingCartDao.clearCart(userId);

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to clear cart.");
            }

        }

    }

