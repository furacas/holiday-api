package com.furacas.api.holiday.controller;

import com.furacas.api.holiday.dto.HolidayInfo;
import com.furacas.api.holiday.dto.Response;
import com.furacas.api.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping("/info/{date}")
    public Response<HolidayInfo> info(@PathVariable("date") String date){
        return Response.ok(holidayService.getHolidayInfo(date));
    }

    @GetMapping("/info")
    public Response<HolidayInfo> toDayInfo(){
        return info(null);
    }

    @GetMapping("/next/holiday/info/{name}")
    public Response<HolidayInfo> nextDateInfo(@PathVariable("name") String name){
        return Response.ok(holidayService.getNextHolidayInfo(name));
    }

    @GetMapping("/next/holiday/info")
    public Response<HolidayInfo> nextDateInfo(){
        return nextDateInfo(null);
    }

    @GetMapping("/next/year/holiday/info")
    public Response<List<HolidayInfo>> nextYearHolidayInfo(){
        return Response.ok(holidayService.nextYearHolidayInfo());
    }

}