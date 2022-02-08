package io.berbotworks.mc.models;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class PlacedOrder extends BaseEntity {
    private Long uid;
    private Long itemId;
    private String iname;
    private int quantityOrdered;
}
