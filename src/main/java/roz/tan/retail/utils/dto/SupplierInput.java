package roz.tan.retail.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierInput {

    private String name;
    private String inn;
    private String kpp;
    private String contactPerson;
    private String phone;
    private String email;
    private Integer cityId;      // добавлено
    private String address;
    private Boolean isActive;

}