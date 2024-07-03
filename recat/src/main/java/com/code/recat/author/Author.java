package com.code.recat.author;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer author_id;
    private String name;
    private LocalDate date_of_birth;
    private String gender;
}