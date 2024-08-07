package com.code.recat.genre;

import com.code.recat.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
@Data
@Entity
public class Genre{
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
        @Column(nullable = false, updatable = false)
        @SequenceGenerator(name = "genre_seq", sequenceName = "public.genres_seq", allocationSize = 1)
        private Long genreId;
        private String name;
        @ManyToMany(mappedBy = "genres")
        private Set<Book> books = new HashSet<>();

        @Override
        public String toString(){
                return "Genre{" +
                        "genreId=" + genreId +
                        ", name='" + name + '\'' +
                        '}';
        }
}
