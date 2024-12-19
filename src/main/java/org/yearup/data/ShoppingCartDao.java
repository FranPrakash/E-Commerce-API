package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    public ShoppingCart getCart();
    // add additional method signatures here
    void createCartItem(int userId, ShoppingCartItem shoppingCartItem);
    void updateCartItem(int userId, ShoppingCartItem shoppingCartItem);
    void deleteCart(int userId);


}
