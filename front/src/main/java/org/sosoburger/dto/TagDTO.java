package org.sosoburger.dto;

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

    public void setTagRu(String tagRu){
        tag.setRu(tagRu);
    }

    public String getTagRu(){
        return tag.getRu();
    }

    public String getStringConfidence(){
        return confidence.intValue()+"%";
    }

    public void setConfidence(String confidence){
        this.confidence = Float.parseFloat(confidence.replace("%", ""));
    }
}