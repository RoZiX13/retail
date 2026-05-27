package roz.tan.retail.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {

    private String sku;
    private String name;
    private String description;
    private Integer categoryId;
    private Integer brandId;
    private Integer unitId;
    private Boolean isActive;
    private Double purchasePrice;
    private Double salePrice;

}
