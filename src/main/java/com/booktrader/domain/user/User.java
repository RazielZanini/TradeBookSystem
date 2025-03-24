package com.booktrader.domain.user;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.dtos.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private UserRole role;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Review> reviews = new ArrayList<>();

    private String email;

    public User(UserDTO data){
        this.name = data.name();
        this.password = data.password();
        this.role = UserRole.USER;
        this.email = data.email();
    }

    public User(String name, String password, UserRole role, String email){
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return name;
    }
}
