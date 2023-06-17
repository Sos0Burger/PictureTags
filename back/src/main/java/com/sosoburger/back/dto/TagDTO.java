package com.sosoburger.back.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagDTO {
    private Integer id;

    private Float confidence;

    private String tag_name;
}
