package com.druidelf.novelbackstagemanagement.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NovelTypeEunm {

	NOVEL_KUNG_FU(1,"武侠小说"),
	NOVEL_FANTASY(2,"玄幻小说"),
	NOVEL_CITY_LOVE(3,"都市言情,现代言情"),
	NOVEL_TERRORIST_PSYCHICS(4,"恐怖灵异"),
	NOVEL_MODERN_LITERATURE(5,"现代文学"),
	NOVEL_DETECTIVE(6,"侦探推理"),
	NOVEL_SCIENCE(7,"科幻小说"),
	NOVEL_THROUGH(8,"穿越架空"),
	NOVEL_CLASSICAL(9,"古典名著"),
	NOVEL_MILITARY (10,"历史军事"),
	NOVEL_ONLINE(11,"网游小说"),
	NOVEL_TIME_TRAVEL(12,"穿越小说"),
	NOVEL_REBIRTH(13,"重生小说"),
	NOVEL_PRESIDENT_GIANTS(14,"总裁豪门"),
	NOVEL_XIANXIA(15,"仙侠小说"),
	NOVEL_FAN_FICTION(16,"同人小说"),
	NOVEL_HOMOEROTIC(17,"耽美小说"),
	NOVEL_URBAN_POWER(18,"都市异能,都市娱乐"),
	NOVEL_THRILLER_SUSPENSE(19,"惊悚悬疑"),
	NOVEL_OTHER(100,"其他");


	public Integer statusCode;
	public String name;

}
