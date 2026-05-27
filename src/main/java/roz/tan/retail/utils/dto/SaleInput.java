package roz.tan.retail.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleInput {

    private Integer shopId;
    private Integer customerId;
    private Integer employeeId;
    private String saleDate;
    private List<SaleItemInput> items;
    private String status;

}
