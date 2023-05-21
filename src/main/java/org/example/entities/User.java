package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.Gender;
import org.example.enums.Role;

@Getter
@Setter
@Builder
public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private Role role;
    private Gender gender;
}
