package com.code.recat.author;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Data
@Entity
public class Author {
    @Id
    int author_id;
    String name;
    Date date_of_birth;
    String gender;
}