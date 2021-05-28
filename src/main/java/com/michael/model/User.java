package com.michael.model;


import com.michael.utils.AutoDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AutoDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String password;

//    @ManyToMany
//    @JoinTable(
//            name = "connections", joinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "connection_id", referencedColumnName = "id")
//    )
//    private List<User> owners;
//
//    @ManyToMany(mappedBy = "owners")
//    private List<User> connections;

}
