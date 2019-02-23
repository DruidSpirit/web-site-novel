package com.druidelf.novelmain.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PeriodTypeEnum {

    PERIOD_DAY("day","以天为周期"),
    PERIOD_WEEK("week","以周为周期"),
    PERIOD_MONTH("month","以月为周期"),
    PERIOD_YEAR("year","以年为周期");

    public String statusCode;
    public String name;
}
