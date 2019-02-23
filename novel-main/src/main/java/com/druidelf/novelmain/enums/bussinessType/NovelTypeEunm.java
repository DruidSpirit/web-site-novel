package com.druidelf.novelmain.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NovelTypeEunm {

	NOVEL_KUNG_FU(1,"武侠小说"),
	NOVEL_FANTASY(2,"玄幻小说"),
	NOVEL_CITY_LOVE(3,"都市言情"),
	NOVEL_TERRORIST_PSYCHICS(4,"恐怖灵异"),
	NOVEL_MODERN_LITERATURE(5,"现代文学"),
	NOVEL_DETECTIVE(6,"侦探推理"),
	NOVEL_SCIENCE(7,"科幻小说"),
	NOVEL_THROUGH(8,"穿越架空"),
	NOVEL_CLASSICAL(9,"古典名著"),
	NOVEL_MILITARY (10,"历史军事"),
	NOVEL_ONLINE(11,"网游小说"),
	NOVEL_OTHER(12,"其他");

	public Integer statusCode;
	public String name;

}
