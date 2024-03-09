package com.sora.service;

import com.sora.dto.ShoppingCartDTO;
import com.sora.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();

    void cleanShoppingCart();
}
