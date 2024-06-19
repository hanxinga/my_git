package com.example.drugsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    private int id;
    private String name;
    private double price;
    private int purchaseStatus;
//    private Date Production;
//    private String manufacturer;
//    private int inventory;
//    private Date validity;
}
