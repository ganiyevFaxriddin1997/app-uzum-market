package org.example.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int id;
    private int userId;
    private List<Integer> productIds;
}
