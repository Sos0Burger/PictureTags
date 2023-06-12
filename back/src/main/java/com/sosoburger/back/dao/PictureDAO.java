package com.sosoburger.back.dao;

import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.dto.TagDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Entity
@Table(name = "picture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tags")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<TagDTO> tags;

    @Column(name = "data")
    @Lob
    private byte[] data;

    @Column(name = "type")
    private String type;

    public PictureDTO toDTO() {
        return new PictureDTO(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/picture/data/")
                        .path(id.toString())
                        .toUriString(),
                tags);
    }

}
