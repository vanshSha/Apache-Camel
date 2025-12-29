package com.service;

import com.dto.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private List<Order> list = new ArrayList<>();

    @PostConstruct // if I want to execute some code after bean construction then we use this annotation
    void  initDB(){
        list.add(new Order(67, "Mobile", 5000));
        list.add(new Order(68, "MAC", 6000));
        list.add(new Order(69, "Xbox", 4500));
        list.add(new Order(70, "IMAC", 15000));
    }

    public Order addOrder(Order order){
        list.add(order);
        return order;
    }

    public List<Order> getOrders(){
        return list;
    }


}
