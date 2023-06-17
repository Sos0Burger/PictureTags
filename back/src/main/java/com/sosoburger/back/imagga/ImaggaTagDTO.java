package com.sosoburger.back.imagga;

import com.sosoburger.back.dao.TagDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImaggaTagDTO {
    private Float confidence;
    private ImaggaTag tag;

    public TagDAO toTagDAO(){
        return new TagDAO(null, confidence, tag.getRu());
    }
}
