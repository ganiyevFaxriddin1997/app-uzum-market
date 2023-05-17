package org.example.model;

import lombok.*;
import org.example.enums.Gender;
import org.example.enums.Role;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id;
    private String name;
    private String phoneNumber;
    private Role role;
    private Gender gender;
}
