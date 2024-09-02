package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class OrdenesComprasRequest {
    private String user;

    private int cantidad;

    private double amount;


}

public class OrderUserRequest {

    private String nombre;
}