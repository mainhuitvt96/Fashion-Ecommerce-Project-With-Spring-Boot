package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;

public interface OrderService {

    void cancelOrder(Long id);
    Order save(ShoppingCart shoppingCart);

}
