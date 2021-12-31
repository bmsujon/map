package com.jachai.map.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FareResponse {
    private String name;
    private BigDecimal amount;
    private Long estimateTime;
    private Long distance;
}
