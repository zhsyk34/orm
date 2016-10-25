package com.cat.orm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class Role {
    private Long id;
    private String name;

    private User user;
    private Set<Function> functions;
}
