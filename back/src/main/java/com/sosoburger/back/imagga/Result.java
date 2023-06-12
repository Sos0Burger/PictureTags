package com.sosoburger.back.imagga;

import com.sosoburger.back.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    List<TagDTO> tags;
}