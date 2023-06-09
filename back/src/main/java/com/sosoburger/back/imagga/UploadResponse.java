package com.sosoburger.back.imagga;

import com.sosoburger.back.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {
    private Result result;
    private Status status;
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Result{
        ArrayList<TagDTO> tags;
    }
}
