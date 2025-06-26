package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartDao
{
    ShoppingCart addProductToCart(int userId, int productId, int quantity);
    ShoppingCart addProductToCart(int userId, int productId);
    ShoppingCart updateProductQuantity(int userId, int productId, int quantity);
    ShoppingCart clearCart(int userId);
    ShoppingCart removeProductById(int userId, int productId);
   ShoppingCart getByUserId(int userId);
    // add additional method signatures here
}
