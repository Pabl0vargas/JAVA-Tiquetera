package com.tiquetera.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    private String category; // Campo para filtro

    // Relación ManyToOne: Muchos eventos pueden ser en un Venue
    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private VenueEntity venue;
}