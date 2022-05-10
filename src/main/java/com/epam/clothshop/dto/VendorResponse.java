package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {
    private long id;
    private String name;
    private List<ProductResponse> productResponses;
}
