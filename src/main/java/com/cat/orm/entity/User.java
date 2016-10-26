package com.cat.orm.entity;

import com.cat.orm.entity.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class User {
	private Long id;
	private String name;
	private Date birthday;
	private String phone;
	private Gender gender;

	private Set<Role> roles;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", birthday=" + birthday +
				", phone='" + phone + '\'' +
				'}';
	}
}
