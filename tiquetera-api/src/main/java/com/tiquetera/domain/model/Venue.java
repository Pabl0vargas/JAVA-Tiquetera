package com.tiquetera.domain.model;

import lombok.Data;

@Data
public class Venue {
    private Long id;
    private String name;
    private String address;
    private String city;
    private int capacity;
}