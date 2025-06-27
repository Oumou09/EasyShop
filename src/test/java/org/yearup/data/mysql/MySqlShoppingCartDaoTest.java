package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MySqlShoppingCartDaoTest extends BaseDaoTestClass {
    private MySqlShoppingCartDao dao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;

    @BeforeEach
    public void setUp() throws Exception{
        dao = spy(new MySqlShoppingCartDao(mock(DataSource.class)));
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);

        doReturn(mockConnection).when(dao).getConnection();
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

    }

    @Test
    void createOrder() throws SQLException {

        //Arrange
        int userId = 1;
        int productId = 10;
        int quantity = 3;

        ShoppingCart fakeCart = new ShoppingCart();
        ShoppingCartItem item = new ShoppingCartItem();

        item.getProductId();
        item.setQuantity(quantity);
        fakeCart.add(item);
        doReturn(fakeCart).when(dao).getByUserId(userId);

        //Act
        ShoppingCart result = dao.createOrder(userId, productId, quantity);

        // Assert
        verify(mockStatement).setInt(1, userId);
        verify(mockStatement).setInt(2, productId);
        verify(mockStatement).setInt(3, quantity);
        verify(mockStatement).executeUpdate();
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(productId, result.getItems().get(0).getProductId());
        assertEquals(quantity, result.getItems().get(0).getQuantity());
    }

    @Test
    void addProductToCart() {
    }

    @Test
    void addAnotherProductToCart() {
    }

    @Test
    void updateProductQuantity() {
    }
}