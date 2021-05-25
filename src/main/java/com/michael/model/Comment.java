package com.michael.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michael.utils.AutoDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends AutoDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @Column(columnDefinition = "TEXT")
    private String body;

}
