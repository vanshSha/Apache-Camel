package com.component;

import com.dto.WeatherDto;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.DefaultMessage;


//@Component
// This is not working
public class RestJavaDSl extends RouteBuilder {

    private final WeatherDataProvider weatherDataProvider;

    public RestJavaDSl(WeatherDataProvider weatherDataProvider) {
        this.weatherDataProvider = weatherDataProvider;
    }

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")  //use Spring Boot embedded Tomcat
                .bindingMode(RestBindingMode.json); // auto convert JSON ↔ Java object


        from("rest:get:javaDsl/weather/{city}?produces=application/json")
                .outputType(WeatherDto.class)
                .process(this::getWeatherData);

        // this is also wworking
//        rest("javadsl")
//                .get("/weather/{city}")
//                .to("direct:get-weather");
//
//
//        from("direct:get-weather")
//                .log("BEFORE headers = ${headers}")
//                .process(this::getWeatherData)
//                .log("AFTER headers = ${headers}");



    }


    private  void getWeatherData(Exchange exchange) {

        String city = exchange.getMessage().getHeader("city", String.class);
        System.out.println(city); // from here my code is working

        WeatherDto currentWeather = weatherDataProvider.getCurrentWeather(city);   // from here null
            Message message = new DefaultMessage(exchange.getContext());
            message.setBody(currentWeather);   // from here it is showing null
            exchange.setMessage(message);
//       exchange.getMessage().setBody(currentWeather);

    }
}
