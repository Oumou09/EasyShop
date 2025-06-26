package org.yearup.data.mysql;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao  {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }



    @Override
    public ShoppingCart addProductToCart(int userId, int productId, int quantity) {
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

        return null;

    }

    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
        String sql = "INSERT INTO shopping_cart " +
                "VALUES(?, ?)";
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public ShoppingCart updateProductQuantity(int userId, int productId, int quantity) {
        ShoppingCart cart = null;

        String sql = "UPDATE shopping_cart" +
                "SET quantity " +
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
        return null;
    }

    @Override
    public ShoppingCart removeProductById(int userId, int productId) {
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

//    protected static ShoppingCart insideCart (ResultSet row) throws SQLException{
//        int userID = row.getInt("user_id");
//        int productID = row.getInt("product_id");
//        int quantity = row.getInt("quantity");
//
//        ShoppingCart shoppingCart = new ShoppingCart()
//        {{
//            User.setId(userID);
//            ShoppingCartItem.setProduct(productID);
//            ShoppingCartItem.setQuantity(quantity);
//
//        }};
//        return shoppingCart;
//
//    }


}
