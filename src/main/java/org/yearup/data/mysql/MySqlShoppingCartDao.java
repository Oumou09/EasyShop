package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO shopping_cart " +
                "VALUES(?, ?, ?)";
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3,quantity);
            statement.executeQuery();

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart" +
                "SET user_id = ?" +
                ", product_id = ?" +
                "WHERE quantity = ?";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3,quantity);
            statement.executeUpdate();

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void clearCart(int userId) {

    }

    @Override
    public void getCartTotal(BigDecimal price) {

    }

    @Override
    public void deleteItemOffCart(int productId) {
        String sql = "DELETE FROM shopping_cart " +
                " WHERE product_id = ?;";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeQuery();

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }
}
