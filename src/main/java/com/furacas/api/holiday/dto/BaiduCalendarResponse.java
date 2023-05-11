package com.furacas.api.holiday.dto;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SUNDAY;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.furacas.api.holiday.entity.Calendar;
import com.furacas.api.holiday.entity.HolidayStatus;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;

@Data
public class BaiduCalendarResponse implements Serializable {

  private static final int CN_SATURDAY = 6;
  private static final int CN_SUNDAY = 7;

  private String status;

  private List<BaiDuDate> data;

  @Data
  public static class BaiDuDate implements Serializable {

    private List<Almanac> almanac;
  }

  @Data
  public static class Almanac implements Serializable {
    private String month;

    private String year;

    private String day;

    private String term;

    private AlmanacStatus status;

    @SneakyThrows
    public Calendar convertToCalendar() {
      Calendar calendar = new Calendar();
      calendar.setDay(Integer.valueOf(day));
      calendar.setMonth(Integer.valueOf(month));
      calendar.setYear(Integer.valueOf(year));
      calendar.setStatus(convertStatus(status));
      calendar.setWeek(calculateWeek());
      calendar.setName(term);

      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      calendar.setDate(df.parse(year + "-" + month + "-" + day));

      if (Objects.isNull(calendar.getStatus())) {
        if ((calendar.getWeek() == CN_SATURDAY || calendar.getWeek() == CN_SUNDAY)) {
          calendar.setStatus(HolidayStatus.WEEKEND);
        } else {
          calendar.setStatus(HolidayStatus.WORKDAY);
        }
      }
      return calendar;
    }

    private Integer calculateWeek() {
      java.util.Calendar calendar =
          new java.util.Calendar.Builder()
              .setDate(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day))
              .build();

      if (calendar.get(DAY_OF_WEEK) == SUNDAY) {
        return CN_SUNDAY;
      }

      return calendar.get(DAY_OF_WEEK) - 1;
    }

    private HolidayStatus convertStatus(AlmanacStatus status) {
      if (Objects.isNull(status)) {
        return null;
      }
      for (HolidayStatus item : HolidayStatus.values()) {
        if (item.name().equals(status.name())) {
          return item;
        }
      }
      return null;
    }
  }

  @AllArgsConstructor
  @Getter
  public enum AlmanacStatus {
    HOLIDAY("1"),
    MAKE_UP("2");

    @JsonValue private String code;

    @JsonCreator
    public static AlmanacStatus fromCode(String code) {
      for (AlmanacStatus item : values()) {
        if (item.getCode().equals(code)) {
          return item;
        }
      }
      return null;
    }
  }

  public List<Almanac> getAlmanac() {
    if (!CollectionUtils.isEmpty(data)) {
      return Optional.ofNullable(data.get(0))
          .map(BaiDuDate::getAlmanac)
          .orElse(Collections.emptyList());
    }
    return Collections.emptyList();
  }
}
