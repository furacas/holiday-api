package com.furacas.api.holiday.service;


import com.furacas.api.holiday.dto.HolidayInfo;
import com.furacas.api.holiday.entity.Calendar;
import com.furacas.api.holiday.repo.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final CalendarRepository calendarRepository;

    @SneakyThrows
    public HolidayInfo getHolidayInfo(String date) {
        Date queryDate;
        if(Objects.isNull(date)){
            queryDate = new Date();
        }else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            queryDate = sf.parse(date);
        }
        return calendarRepository.findByDate(queryDate)
                .map(this::convertToInfo)
                .orElse(null);
    }

    public HolidayInfo getNextHolidayInfo(String name) {
        if(Objects.isNull(name)){
            return calendarRepository.findNextHoliday(new Date())
                    .map(this::convertToInfo)
                    .orElse(null);
        }
        return calendarRepository.findNextHoliday(new Date(),name)
                .map(this::convertToInfo)
                .orElse(null);
    }

    public List<HolidayInfo> nextYearHolidayInfo() {
        List<HolidayInfo> holidayInfo = new ArrayList<>();
        getNextHoliday(holidayInfo,"元旦");
        getNextHoliday(holidayInfo,"春节");
        getNextHoliday(holidayInfo,"清明");
        getNextHoliday(holidayInfo,"劳动节");
        getNextHoliday(holidayInfo,"端午节");
        getNextHoliday(holidayInfo,"中秋节");
        getNextHoliday(holidayInfo,"国庆节");
        Collections.sort(holidayInfo, new Comparator<HolidayInfo>(){
            @Override
            public int compare(HolidayInfo o1, HolidayInfo o2) {
                return (int) (o1.getRest() - o2.getRest());
            }
        } );
        return holidayInfo;
    }

    private void getNextHoliday(List<HolidayInfo> holidayInfo,String name) {
        HolidayInfo holiday = getNextHolidayInfo(name);
        if(Objects.nonNull(holiday)){
            holidayInfo.add(holiday);
        }
    }


    private HolidayInfo convertToInfo(Calendar calendar){
        HolidayInfo info = new HolidayInfo();
        info.setName(calendar.getName());
        info.setYear(calendar.getYear());
        info.setMonth(calendar.getMonth());
        info.setDay(calendar.getDay());
        info.setWeek(calendar.getWeek());
        info.setStatus(calendar.getStatus());
        return info;
    }
}