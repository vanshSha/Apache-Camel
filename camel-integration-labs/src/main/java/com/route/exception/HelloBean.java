package com.route.exception;

import org.w3c.dom.css.Counter;

import javax.xml.crypto.Data;

import java.util.Date;

import static com.route.exception.ErrorHandlerRoute.counter;

public class HelloBean {

    public HelloBean() {
        System.out.println("HelloBean Constructor");
    }

    public String callGood() {
        System.out.println("Good Call for" + counter.get());
        return "Good:" + new Date();
    }

    public String callBad() {
        System.out.println("Bad Call for" + counter.get());
        throw new RuntimeException("Exception for " + counter.get());
    }
}
