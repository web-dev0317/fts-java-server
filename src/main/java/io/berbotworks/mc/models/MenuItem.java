package io.berbotworks.mc.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "user")
@Entity
public class MenuItem extends BaseEntity {

    private String iname;
    private int normallyAvailableQty;
    private int available;
    private int quantityOrdered;
    private int placedOrderQuantity;
    private BigDecimal price;

    @JsonBackReference
    @ManyToOne
    private User user;
}
