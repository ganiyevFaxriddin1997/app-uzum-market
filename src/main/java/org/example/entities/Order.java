package org.example.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private int userId;
    private int productId;
    private int amountProduct;
}
