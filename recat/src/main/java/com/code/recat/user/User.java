package com.code.recat.user;

import com.code.recat.book.Book;
import com.code.recat.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Data
@Entity
public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "public.users_seq", allocationSize = 1)
        @Column(nullable = false, updatable = false)
        private Long userId;
        private String name;
        private String username;
        private String password;
        @Column(unique = true)
        private String email;
        private String gender;

        @Enumerated(EnumType.STRING)
        private Role role;

        private LocalDateTime dateJoined;

        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinTable(
                name = "user_favorite_books",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "book_id")
        )
        @JsonIgnore
        private Set<Book> favoriteBooks;
        private boolean isActive;
        private boolean isLocked;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
        @JsonIgnore
        private List<Token> tokens;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return (role.getAuthorities());
        }

        @Override
        public String toString(){
                return "User{" +
                        "userId=" + userId +
                        ", name='" + name + '\'' +
                        ", username='" + username + '\'' +
                        ", email='" + email + '\'' +
                        ", gender='" + gender + '\'' +
                        ", role=" + role +
                        ", dateJoined=" + dateJoined +
                        ", isActive=" + isActive +
                        ", isLocked=" + isLocked +
                        '}';
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
                return !isLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return isActive;
        }
}
