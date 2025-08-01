package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }




    @Override
    public ShoppingCart createOrder(int userId, int productId, int quantity) {
        String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)"+
                "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create order", e);
        }

        return getByUserId(userId);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();

        String sql = "SELECT  p.*, s.quantity " +
                "FROM shopping_cart s " +
                "JOIN products p ON s.product_id = p.product_id " +
                "WHERE s.user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Product product = MySqlProductDao.mapRow(resultSet);
                    int quantity = resultSet.getInt("quantity");
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setProduct(product);         // ← sets all product fields
                    item.setQuantity(quantity);

                    shoppingCart.add(item);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingCart;
    }


    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
//        ShoppingCart cart = new ShoppingCart();
//        String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
//
//        try (Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, userId);
//            statement.setInt(2, productId);
//            statement.setInt(3, 1);
//            statement.executeUpdate();
//
//            ShoppingCartItem item = new ShoppingCartItem();
//            item.setProduct(productId);
//            item.setQuantity(1);
//
//            cart.getItems().put(productId, item);
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return getByUserId(userId);
        return null;
    }


   @Override
    public ShoppingCart addAnotherProductToCart(int userId, int productId) {
//        String checkSql = "SELECT product_id, quantity FROM shopping_cart WHERE user_id = ? AND product_id = ?";
//        String updateSql = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";
//        String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
//
//        try (Connection connection = getConnection()) {
//            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
//            checkStmt.setInt(1, userId);
//            checkStmt.setInt(2, productId);
//            ResultSet rows = checkStmt.executeQuery();
//
//            ShoppingCartItem item = null;
//            if (rows.next()) {
//
//                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
//                item = mapRowToCartItem(rows);
//                updateStmt.executeUpdate();
//            } else {
//                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
//                insertStmt.setInt(1, userId);
//                insertStmt.setInt(2, productId);
//                insertStmt.setInt(3, 1);
//                insertStmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return getByUserId(userId);
       return null;
    }


    @Override
    public ShoppingCart getCartAfterCheckout(int userId) {
        clearCart(userId);
        ShoppingCart emptyCart = new ShoppingCart();
        emptyCart.setItems(new HashMap<>());
        return emptyCart;
    }


    @Override
    public ShoppingCart updateProductQuantity(int userId, int productId, int quantity) {

        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
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
            e.printStackTrace();
        }
        return getByUserId(userId);
    }


    @Override
    public ShoppingCart clearCart(int userId) {
        ShoppingCart cart = new ShoppingCart();
            String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

            try (Connection connection = getConnection()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
               e.printStackTrace();
            }

        cart.setItems(new HashMap<>());
        return cart;
    }
    @Override
    public ShoppingCart getEmptyCart(int userId) {
        return new ShoppingCart();
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
            e.printStackTrace();
        }

        return getByUserId(userId);
    }



//    private ShoppingCartItem mapRowToCartItem(ResultSet row) throws SQLException {
//        int productId = row.getInt("product_id");
//        int quantity = row.getInt("quantity");
//        ShoppingCartItem item = new ShoppingCartItem();
//        item.setProduct(productId);
//        item.setQuantity(quantity);
//        return item;
//    }
}
