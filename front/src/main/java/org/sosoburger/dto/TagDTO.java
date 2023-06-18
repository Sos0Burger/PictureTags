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

    private Integer id;

    private Float confidence;
    private String tag_name;
    public String getStringConfidence(){
        return confidence.intValue()+"%";
    }

    public void setConfidence(String confidence){
        this.confidence = Float.parseFloat(confidence.replace("%", ""));
    }
}