package roz.tan.retail.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {

    private String firstName;
    private String lastName;
    private Integer positionId;
    private String email;
    private String phone;

}
