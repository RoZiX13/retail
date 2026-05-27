package roz.tan.retail.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import roz.tan.retail.models.enums.ChangeType;
import roz.tan.retail.models.enums.ReferenceType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementInput {

    private Integer productId;
    private Integer warehouseId;
    private Double quantity;
    private ChangeType type;
    private ReferenceType referenceType;

}
