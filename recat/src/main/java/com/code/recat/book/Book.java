package com.code.recat.book;

import com.code.recat.author.Author;
import com.code.recat.comment.Comment;
import com.code.recat.genre.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Book{
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq")
        @Column(nullable = false, updatable = false)
        @SequenceGenerator(name = "books_seq", sequenceName = "public.books_seq", allocationSize = 1)
        private Integer bookId;
        private String title;

        @ManyToOne
        @JoinColumn(name = "author_id", nullable = false)
        private Author author;

        private String blurb;
        private Integer publicationYear;

        @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
        @JoinTable(
                name = "book_genre",
                joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_id")
        )
        private Set<Genre> genres;

        private String isbn;
        private String coverImageUrl;

        @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
        @ToString.Exclude
        private Set<Comment> comments = new HashSet<>();

        @Override
        public String toString(){
                return "Book{" +
                        "bookId=" + bookId +
                        ", title='" + title + '\'' +
                        ", Author='" + author + '\'' +
                        ", blurb='" + blurb + '\'' +
                        ", publicationYear='" + publicationYear + '\'' +
                        ", genres=" + genres +  '\'' +
                        ", isbn=" + isbn +  '\'' +
                        ", coverImageUrl=" + coverImageUrl +  '\'' +
                        ", comments=" + comments +  '\'' +
                        '}';
        }

        @Override
        public final boolean equals(Object o) {
                if (this == o) return true;
                if (o == null) return false;
                Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
                Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
                if (thisEffectiveClass != oEffectiveClass) return false;
                Book book = (Book) o;
                return getBookId() != null && Objects.equals(getBookId(), book.getBookId());
        }

        @Override
        public final int hashCode() {
                return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
        }
}

