package com.uade.tpo.demo.entity.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {
    private int id;
    private String description;
}

public class OrdenesComprasRequest {
    @NotNull
    @Size(min = 1, max = 100)
    private String user;

    private int cantidad;

    private double amount;


}

public class OrderUserRequest {

    private String nombre;
}