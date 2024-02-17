package com.lux.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="auth_tokens")
public class AuthToken {
    @Id
    @GeneratedValue
    private Integer id;
    private String refreshToken;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
