package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto implements Serializable {
    static int counter = 1;
    private int id = counter++;

    private String city;
    private String temp;
    private String unit;
    private String receivedTime;
}
