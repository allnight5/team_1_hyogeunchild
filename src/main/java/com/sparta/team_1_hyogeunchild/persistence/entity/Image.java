package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_ID")
    private Long id;
    @Column(nullable = false)
    private String mimetype;
    @Column(nullable = false)
    private String original_name;
    @Column(nullable = false)
    private byte[] data;
    @Column(nullable = false)
    private String created;
}