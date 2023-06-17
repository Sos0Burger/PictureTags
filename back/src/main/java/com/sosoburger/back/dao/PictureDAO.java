package com.sosoburger.back.dao;

import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.dto.TagDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "picture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureDAO {

    @Id
    @Column(name = "pictureid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pictureid;

    @Column(name = "tags")
    private List<Integer> tags = new ArrayList<>();

    @Column(name = "data")
    @Lob
    private byte[] data;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    public List<Integer> getTags() {
        return tags==null?new ArrayList<>():tags;
    }

    public PictureDTO toDTO(List<TagDAO> tagDAOs) {
        List<TagDTO> tagDTOs = new ArrayList<>();
        for (var item:tagDAOs
             ) {
            tagDTOs.add(item.toDTO());
        }

        return new PictureDTO(
                pictureid,
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/picture/data/")
                        .path(pictureid.toString())
                        .toUriString(),
                name,
                tagDTOs);
    }

}
