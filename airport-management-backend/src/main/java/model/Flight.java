package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "flights")
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airline;
    private String departure;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;
}
