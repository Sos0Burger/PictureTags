package com.sosoburger.back.dao;

import com.sosoburger.back.dto.TagDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDAO {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tag_id;

    @Column(name = "confidence")
    private Float confidence;

    @Column(name = "tag_name")
    private String tag_name;
    public TagDTO toDTO(){
        return new TagDTO(tag_id, confidence, tag_name);
    }
}
