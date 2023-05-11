package com.furacas.api.holiday.dto;

import com.furacas.api.holiday.entity.HolidayStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Data;

@Data
public class HolidayInfo {

  private Integer year;

  private Integer month;

  private Integer day;

  private Integer week;

  private HolidayStatus status;

  private String name;

  private Integer rest;

  public Long getRest() {
    return ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(year, month, day));
  }
}
