package com.furacas.api.holiday.service;

import com.furacas.api.holiday.dto.BaiduCalendarResponse;
import com.furacas.api.holiday.entity.Calendar;
import com.furacas.api.holiday.repo.CalendarRepository;
import com.furacas.api.holiday.utils.HttpUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Service
public class CalendarService implements ApplicationRunner {

    private static final String BAIDU_API = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query={0}&co=&resource_id=39043&t=1617089428269&format=json&tn=wisetpl";
    private static final String QUERY_DATE = "{0}年{1}月";
    private static final int MONTH = 12;

    @Autowired
    private CalendarRepository calendarRepository;

    @SneakyThrows
    public void updateCalendar(Integer year) {
        if (Objects.isNull(year)) {
            year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        }

        for (int i = 1; i <= MONTH; i++) {
            updateCalendar(year,i);
            Thread.sleep(500);
        }
    }

    private void updateCalendar(Integer year, Integer month) {
        String encoderParam = URLEncoder.encode(MessageFormat.format(QUERY_DATE,year.toString(),month.toString()), StandardCharsets.UTF_8);
        String queryUrl = MessageFormat.format(BAIDU_API,encoderParam);

        BaiduCalendarResponse response = HttpUtils.get(queryUrl, BaiduCalendarResponse.class);
        if(Objects.isNull(response)){
            return;
        }
        List<BaiduCalendarResponse.Almanac> almanac = response.getAlmanac();
        for(BaiduCalendarResponse.Almanac at: almanac){
            Calendar calendar = at.convertToCalendar();
            calendarRepository.findByDayAndMonthAndYear(calendar.getDay(),calendar.getMonth(),calendar.getYear())
                    .ifPresentOrElse((exist)->{
                        calendar.setId(exist.getId());
                        calendarRepository.save(calendar);
                    },()->{
                        calendarRepository.save(calendar);
                    });

        }
    }

    @Scheduled(cron = "0 0 3 ? * MON")
    public void updateCalender() {
        int currentYear = Year.now().getValue();
        updateCalendar(currentYear);
        updateCalendar(currentYear+1);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        updateCalender();
    }
}