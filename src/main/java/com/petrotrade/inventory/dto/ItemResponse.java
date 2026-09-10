package com.petrotrade.inventory.dto;

import java.math.BigDecimal;

public class ItemResponse {

    private Long id;
    private String name;
    private String sku;
    private String category;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Integer reorderLevel;

    // Getters and Setters
}