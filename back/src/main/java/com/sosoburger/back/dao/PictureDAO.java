package com.sosoburger.back.dao;

import com.sosoburger.back.dto.Tag;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;

@Entity
@Table(name = "picture")
public class PictureDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tags")
    @JdbcTypeCode(SqlTypes.JSON)
    private ArrayList<Tag> tags;

    @Column(name = "data")
    @Lob
    private byte[] data;

    @Column(name = "confidence")
    private Float confidence;

    public PictureDAO() {
    }
}
