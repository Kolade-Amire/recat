package com.code.recat.genre;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Genre{
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
        @Column(nullable = false, updatable = false)
        @SequenceGenerator(name = "genre_seq", sequenceName = "public.genres_seq", allocationSize = 1)
        private Integer genreId;
        private String name;

        @Override
        public String toString(){
                return "Genre{" +
                        "genreId=" + genreId +
                        ", name='" + name + '\'' +
                        '}';
        }

        @Override
        public final boolean equals(Object o) {
                if (this == o) return true;
                if (o == null) return false;
                Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
                Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
                if (thisEffectiveClass != oEffectiveClass) return false;
                Genre genre = (Genre) o;
                return getGenreId() != null && Objects.equals(getGenreId(), genre.getGenreId());
        }

        @Override
        public final int hashCode() {
                return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
        }
}
