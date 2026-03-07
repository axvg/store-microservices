package com.example.app.micro.orderservice.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClientDto {
    private Long id;
    private String name;
    private String email;
}
