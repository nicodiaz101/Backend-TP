package com.uade.tpo.demo.controllers.orders;

import java.util.List;

import com.uade.tpo.demo.controllers.movies.MovieRequest;
import com.uade.tpo.demo.controllers.users.UserRequest;

import lombok.Data;

@Data
public class OrderRequest{
    private UserRequest user;
    private List<MovieRequest> movies;
}