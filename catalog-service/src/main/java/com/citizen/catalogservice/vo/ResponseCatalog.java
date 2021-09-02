package com.citizen.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ResponseCatalog {

    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;

    private LocalDate createAt;

}
