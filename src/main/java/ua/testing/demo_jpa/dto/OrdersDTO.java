package ua.testing.demo_jpa.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrdersDTO {
    List<OrderDTO> orders;
}
