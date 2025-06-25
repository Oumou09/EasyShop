package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShoppingCartDao
{
    Map<Integer, ShoppingCartItem> getCartItemsByUserId(int userId);
    void addProductToCart(int userId, int productId, int quantity);
    void updateProductQuantity(int userId, int productId, int quantity);
    void clearCart(int userId);
    void getCartTotal(BigDecimal price);
    void deleteItemOffCart(int productId);
   ShoppingCart getByUserId(int userId);
    // add additional method signatures here
}
