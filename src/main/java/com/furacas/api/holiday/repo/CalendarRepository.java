package com.furacas.api.holiday.repo;

import com.furacas.api.holiday.entity.Calendar;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

  /**
   * 根据年月日查找日历
   *
   * @param day
   * @param month
   * @param year
   * @return
   */
  Optional<Calendar> findByDayAndMonthAndYear(Integer day, Integer month, Integer year);

  /**
   * 根据date查找
   *
   * @param date
   * @return
   */
  Optional<Calendar> findByDate(Date date);

  /**
   * 获取最近的一个假期
   *
   * @param date
   * @param name
   * @return
   */
  @Query(
      value =
          "select * from calendar where date >= :date and name like concat('%',:name,'%') order by date asc limit 1",
      nativeQuery = true)
  Optional<Calendar> findNextHoliday(Date date, String name);

  /**
   * 获取最近一个假期
   *
   * @param date
   * @return
   */
  @Query(
      value =
          "select * from calendar where date >= :date and status = 'HOLIDAY' order by date asc limit 1",
      nativeQuery = true)
  Optional<Calendar> findNextHoliday(Date date);
}
