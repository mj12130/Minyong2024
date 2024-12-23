package com.springboot.test.data.dto;

import lombok.*;

// 예제 7.9
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {

    private String name;

    private int price;

    private int stock;

}
