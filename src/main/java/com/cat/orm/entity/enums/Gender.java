package com.cat.orm.entity.enums;

import lombok.Getter;

@Getter
public enum Gender {
	MALE(0, "男"), FEMALE(1, "女"), OTHER(2, "其他");

	private final int index;
	private final String sex;

	Gender(int index, String sex) {
		this.index = index;
		this.sex = sex;
	}

	@Override
	public String toString() {
		return sex.toLowerCase();
	}
}
