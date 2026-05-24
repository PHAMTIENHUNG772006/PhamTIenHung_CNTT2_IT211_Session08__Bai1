package com.re.session8.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockRequest {
    @NotBlank
    private String sku;

    @Positive
    private int quantity;
}
