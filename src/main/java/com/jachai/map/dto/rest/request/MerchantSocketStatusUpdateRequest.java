package com.jachai.map.dto.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSocketStatusUpdateRequest {
    private String shopId;
    private String status;
    private String socketUsername;
}
