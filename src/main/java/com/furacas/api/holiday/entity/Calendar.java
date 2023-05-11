package com.furacas.api.holiday.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(
    name = "calendar",
    indexes = {
      @Index(
          name = "uk_date",
          columnList = "calendar_year,calendar_month,calendar_day",
          unique = true)
    })
@Data
public class Calendar {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "calendar_year")
  private Integer year;

  @Column(name = "calendar_month")
  private Integer month;

  @Column(name = "calendar_day")
  private Integer day;

  @Enumerated(EnumType.STRING)
  private HolidayStatus status;

  private Integer week;

  /** 日期名 */
  private String name;
}
