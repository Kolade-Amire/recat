package com.code.recat.author;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name = "author_seq", sequenceName = "public.authors_author_id_seq", allocationSize = 1)
    private Integer authorId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;

    //TODO: reference book entity here too!

    @Override
    public String toString(){
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth.toString() + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Author author = (Author) o;
        return getAuthorId() != null && Objects.equals(getAuthorId(), author.getAuthorId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}