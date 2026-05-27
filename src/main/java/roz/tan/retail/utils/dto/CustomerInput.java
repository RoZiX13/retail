package roz.tan.retail.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInput {

    private String firstName;
    private String patronymic;
    private String lastName;
    private String email;
    private String phone;
    private Integer cityId;
    private String address;
    private Boolean isActive;

}
