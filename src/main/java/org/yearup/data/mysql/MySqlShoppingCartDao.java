package org.yearup.data.mysql;

import org.springframework.stereotype.Component;;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao  {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public ShoppingCart createOrder(int userId, int productId, int quantity) {
        return null;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";

        ShoppingCart cart = new ShoppingCart();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            try(ResultSet resultSet =  statement.executeQuery() ){
                while (resultSet.next()){
                    return cart;
                }

            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return cart;
    }


    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
        ShoppingCart cart = new ShoppingCart();
        String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, 1);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;
    }

    @Override
    public ShoppingCart addAnotherProductToCart(int userId, int productId) {
        return null;
    }

    @Override
    public ShoppingCart getCartAfterCheckout() {
        return null;
    }


    @Override
    public ShoppingCart updateProductQuantity(int userId, int productId, int quantity) {
        ShoppingCart cart = new ShoppingCart();

        String sql = "UPDATE shopping_cart" +
                "SET quantity = ?" +
                "WHERE user_id = ? AND product_id = ?";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected.");
            }

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return cart;
    }


    @Override
    public ShoppingCart clearCart(int userId) {
            String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

            try (Connection connection = getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return null;

    }
    @Override
    public ShoppingCart getEmptyCart() {
        return null;
    }

    @Override
    public ShoppingCart removeProductById(int userId, int productId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


// private ShoppingCartItem mapRowToCartItem(ResultSet row) throws SQLException {
//    ShoppingCartItem item = new ShoppingCartItem();
//    item.setProductID(row.getInt("product_id"));
//    item.setQuantity(row.getInt("quantity"));
//    return item;
//}


}
