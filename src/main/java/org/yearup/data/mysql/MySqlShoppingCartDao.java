package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Map;
@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Map<Integer, ShoppingCartItem> getCartItemsByUserId(int userId) {
        return Map.of();
    }

    @Override
    public void addProductToCart(int userId, int productId, int quantity) {

    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity) {

    }

    @Override
    public void clearCart(int userId) {

    }

    @Override
    public void getCartTotal(BigDecimal price) {

    }

    @Override
    public void deleteItemOffCart(int productId) {

    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
    }
}
