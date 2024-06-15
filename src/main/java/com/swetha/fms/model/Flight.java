package com.swetha.fms.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "flightName")
    @NotBlank(message = "{flight.name.notBlank}")
    private String name;

    @Column(name = "fromPlace")
    @NotBlank(message = "{flight.from.notBlank}")
    private String from;

    @Column(name = "destinyPlace")
    @NotBlank(message = "{flight.destiny.notBlank}")
    private String destiny;


    @Column(name = "price")
    @NotNull(message = "{flight.price.notBlank}")
    private Double price;



    @Column(name = "departure_on")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{flight.departure.notNull}")
    private LocalDate departureOn;

    @Column(name = "arrival_on")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{flight.arrival.notNull}")
    private LocalDate arrivalOn;

    @Column(name = "enabled")
    private boolean enabled;

    public Flight() {
        this.enabled = true;
    }

    public Flight(Long id, String name, String from, String destiny, Double price, LocalDate departureOn, LocalDate arrivalOn) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.destiny = destiny;
        this.price = price;
        this.departureOn = departureOn;
        this.arrivalOn = arrivalOn;
        this.enabled=true;
    }

    public Flight(Long id, String name, String from, String destiny, Double price, LocalDate departureOn, LocalDate arrivalOn, boolean enabled) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.destiny = destiny;
        this.price = price;
        this.departureOn = departureOn;
        this.arrivalOn = arrivalOn;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDepartureOn() {
        return departureOn;
    }

    public void setDepartureOn(LocalDate departureOn) {
        this.departureOn = departureOn;
    }

    public LocalDate getArrivalOn() {
        return arrivalOn;
    }

    public void setArrivalOn(LocalDate arrivalOn) {
        this.arrivalOn = arrivalOn;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
