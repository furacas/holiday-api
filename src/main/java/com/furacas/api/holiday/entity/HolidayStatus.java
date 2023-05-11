package com.furacas.api.holiday.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HolidayStatus {

  /** 工作日 */
  WORKDAY(1),

  /** 补班 */
  MAKE_UP(2),

  /** 假期 */
  HOLIDAY(3),

  /** 周末， 补班优先级高于周末 */
  WEEKEND(4);

  @JsonValue private Integer code;
}
