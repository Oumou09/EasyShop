package org.yearup.data;

import org.yearup.models.ShoppingCart;


public interface ShoppingCartDao
{
    ShoppingCart createOrder(int userId, int productId, int quantity);
    ShoppingCart addProductToCart(int userId, int productId);
    ShoppingCart addAnotherProductToCart(int userId, int productId);
    ShoppingCart getCartAfterCheckout(int userId);
    ShoppingCart updateProductQuantity(int userId, int productId, int quantity);
    ShoppingCart clearCart(int userId);
    ShoppingCart removeProductById(int userId, int productId);
   ShoppingCart getByUserId(int userId);
   ShoppingCart getEmptyCart();
    // add additional method signatures here
}
