package com.lux.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String type;
    private String content;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
