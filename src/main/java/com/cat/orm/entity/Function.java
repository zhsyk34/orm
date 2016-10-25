package com.cat.orm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function {
    private Long id;
    private String name;

    private Role role;
}
