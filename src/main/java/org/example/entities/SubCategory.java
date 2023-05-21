package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubCategory {
    private int id;
    private String name;
    private int categoryId;
}
