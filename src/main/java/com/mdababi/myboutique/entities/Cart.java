package com.mdababi.myboutique.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="cart")
public class Cart extends AbstractEntity {

    private static final long serialVersionUID =1;

    @OneToOne
    @JoinColumn(unique=true)
    private Order order;

    @ManyToOne
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public Cart(Customer customer){
        this.customer = customer;
        this.status = CartStatus.NEW;
    }

}
