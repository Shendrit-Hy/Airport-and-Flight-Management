package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lost_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private String description;

    private String location;

    private String contactEmail;

    private LocalDateTime reportedAt;
}
