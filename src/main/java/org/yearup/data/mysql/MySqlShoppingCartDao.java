package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
    }

    @Override
    public ShoppingCart getCart() {
        return null;
    }

    @Override
    public void createCartItem(int userId, ShoppingCartItem shoppingCartItem) {

    }

    @Override
    public void updateCartItem(int userId, ShoppingCartItem shoppingCartItem) {

    }

    @Override
    public void deleteCart(int userId) {

    }

}
