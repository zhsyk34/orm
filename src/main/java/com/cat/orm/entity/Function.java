package com.cat.orm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Function {
	private Long id;
	private String name;

	private Role role;

	@Override
	public String toString() {
		return "Function{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
