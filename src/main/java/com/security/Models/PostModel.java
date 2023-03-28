package com.security.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "xpost")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String postUrl;

    private Instant created;

    private String post;

}
