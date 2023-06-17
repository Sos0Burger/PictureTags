package com.sosoburger.back.imagga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    List<ImaggaTagDTO> tags;
}