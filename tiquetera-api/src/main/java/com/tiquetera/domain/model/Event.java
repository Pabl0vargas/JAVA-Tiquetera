package com.tiquetera.domain.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private String category;
    private Venue venue;
}