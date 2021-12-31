package com.jachai.map.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DistanceMatrixElement {
    private TextValue distance;
    private TextValue duration;
    private String status;
}
