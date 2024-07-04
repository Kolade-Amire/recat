package com.code.recat.user;

import com.code.recat.book.Book;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Data
@Entity
public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false, updatable = false)
        private Integer user_id;
        private String name;
        private String username;
        private String password;
        private String email;
        private String gender;
        @Enumerated(EnumType.STRING)
        private Role role;
        private LocalDateTime dateJoined;

        @OneToMany
        @JoinTable(
                name = "user_favorite_books",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "book_id")
        )
        private Set<Book> favoriteBooks = new HashSet<>();
        private boolean isActive;
        private boolean isLocked;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(role.name()));
        }


        @Override
        public String getUsername() {
                return email;
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
