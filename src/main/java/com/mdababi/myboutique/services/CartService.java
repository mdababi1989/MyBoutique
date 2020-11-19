package com.mdababi.myboutique.services;


import com.mdababi.myboutique.dto.CartDto;
import com.mdababi.myboutique.entities.Cart;
import com.mdababi.myboutique.entities.CartStatus;
import com.mdababi.myboutique.entities.Customer;
import com.mdababi.myboutique.entities.Order;
import com.mdababi.myboutique.repositories.CartRepository;
import com.mdababi.myboutique.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    public List<CartDto> findAll() {
        log.debug("Request to get all Carts");
        return cartRepository.findAll().stream().map(CartService::mapToDto).collect(Collectors.toList());

    }

    public List<CartDto> findAllActiveCarts() {
        return this.cartRepository.findByStatus(CartStatus.NEW).stream().map(CartService::mapToDto).collect(Collectors.toList());
    }

    public CartDto create(Long customerId) {
        if (this.getActiveCart(customerId) == null) {
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("The customer does not exist!!!"));
            Cart cart = new Cart(null, customer, CartStatus.NEW);
            Order order = this.orderService.create(cart);
            cart.setOrder(order);
            return mapToDto(cart);
        }
        throw new IllegalStateException("There is already an active cart");
    }

    public CartDto getActiveCart(Long customerId) {
        List<Cart> carts = this.cartRepository.findByStatusAndCustomerId(CartStatus.NEW, customerId);
        if (carts != null) {
            if (carts.size() == 1)
                return mapToDto(carts.get(0));
            if (carts.size() > 1)
                throw new IllegalStateException("Many active carts detected !!!");
        }
        return null;
    }

    @Transactional
    public CartDto findById(Long id){
        log.debug("Request to get Cart: {}", id);
        return this.cartRepository.findById(id).map(CartService::mapToDto).orElse(null);
    }

    public void delete(Long id){
        log.debug("Request to delete Cart : {}", id);
        Cart cart = this.cartRepository.findById(id).orElseThrow(()->new IllegalStateException("Cannot find card with id "+id));
        cart.setStatus(CartStatus.CANCELED);
        this.cartRepository.save(cart);
    }

    private static CartDto mapToDto(Cart cart) {
        if (cart != null)
            return new CartDto(cart.getId(), cart.getOrder().getId(), CustomerService.mapToDto(cart.getCustomer()), cart.getStatus().name());
        return null;
    }


}
