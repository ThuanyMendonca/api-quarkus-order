package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long customerId;
    private String customerName;
    private Long productId;
    private BigDecimal orderValue;
}

