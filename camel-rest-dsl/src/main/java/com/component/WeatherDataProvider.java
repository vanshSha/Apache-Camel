package com.component;

import com.dto.WeatherDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Component
public class WeatherDataProvider {

    private static Map<String, WeatherDto> weatherData = new HashMap<>();

    public WeatherDataProvider() {
        WeatherDto dto = WeatherDto.builder()
//                .id(1)
                .city("China")
                .temp("10")
                .unit("C")
                .receivedTime(new Date().toString())
                .build();
            weatherData.put("CHINA", dto);
    }

    public WeatherDto getCurrentWeather(String city){
        return weatherData.get(city.toUpperCase());
    }

    public void setCurrentWeather(WeatherDto dto){
        dto.setReceivedTime(new Date().toString());
        weatherData.put(dto.getCity().toUpperCase(), dto);
    }

}
