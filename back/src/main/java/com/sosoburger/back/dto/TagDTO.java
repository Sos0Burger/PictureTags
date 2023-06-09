package com.sosoburger.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private Float confidence;
    private Tag tag;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class Tag{
        private String ru;
    }
}
