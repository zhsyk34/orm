package com.cat.orm.kit;

import lombok.Getter;

@Getter
public class Page {

	private int no;
	private int size;

	private Page(int no, int size) {
		this.no = no;
		this.size = size;
	}

	public static Page of(int no, int size) {
		return new Page(no, size);
	}
}
