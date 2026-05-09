package com.example.AutoScreenshotApplication.model;

import com.example.AutoScreenshotApplication.utility.UserSex;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserSex sex;

    @OneToMany(mappedBy = "owner" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Attachment> archiveAttachments = new ArrayList<>();
}
