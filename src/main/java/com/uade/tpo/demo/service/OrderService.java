package com.uade.tpo.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.controllers.movies.MovieRequest;
import com.uade.tpo.demo.entity.Movie;
import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Throwable.class)
    public Order createOrder(String userEmail, List<String> movieTitles) {
        // Encontrar el usuario por email
        Optional<User> optionalUser = userService.getUserByEmail(userEmail);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get(); // Obtener el usuario
        
        // Encontrar las películas por título y calcular monto final
        double finalAmount = 0;
        List<Movie> movies = new ArrayList<>(); // Lista de películas
        for (String movieTitle : movieTitles) {
            Optional<Movie> optionalMovie = movieService.getMovieByTitle(movieTitle);
            if (!optionalMovie.isPresent() || optionalMovie.get().getStock() == 0) { // Película no encontrada o sin stock
                throw new RuntimeException("Movie not found: " + movieTitle);
            }
            Movie movie = optionalMovie.get();
            finalAmount = finalAmount + movieService.calculateFinalPrice(movie);
            movie.setStock(movie.getStock() - 1); // Disminuir el stock
            
            // Crear un objeto MovieRequest con los detalles de la película
            MovieRequest movieRequest = new MovieRequest();
            movieRequest.setTitle(movie.getTitle());
            movieRequest.setReleaseDate(movie.getReleaseDate());
            movieRequest.setImdbScore(movie.getImdbScore());
            movieRequest.setPrice(movie.getPrice());
            movieRequest.setDiscountPercentage(movie.getDiscountPercentage());
            movieRequest.setStock(movie.getStock());
            movieRequest.setPoster(movie.getPoster());
            movieRequest.setDescription(movie.getDescription());
            movieRequest.setGenre(movie.getGenre().getName());
            movieRequest.setDirector(movie.getDirector().getName());

            // Modificar la película utilizando el objeto MovieRequest
            movieService.modifyMovie(movie.getMovieId(), movieRequest);
            movies.add(movie); // Agregar la película a la lista
        }
        
        // Crear y guardar la orden
        Order order = new Order(); // Crear una nueva orden
        order.setAmount(finalAmount);
        order.setOrderDate(new Date()); // Fecha actual
        order.setUser(user);
        order.setMovies(movies);

        return orderRepository.save(order);
    }
    
    public Page<Order> getOrders(PageRequest pageable) {
        return orderRepository.findAll(pageable);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserUserId(userId);
    }
    
    public List<Order> getOrderByOrderDate(String orderDate) {
        try {
            Date date = convertStringToDate(orderDate);
            return orderRepository.findByOrderDate(date);
        } catch (ParseException e) { // Formato de fecha incorrecto
                        return List.of();
        }
    }
    
    // Método para convertir String a Date
    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha
        return formatter.parse(dateString);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Order updateOrder(Long id, Order orderDetails) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setAmount(orderDetails.getAmount());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setUser(orderDetails.getUser());
            order.setMovies(orderDetails.getMovies());
            return orderRepository.save(order);
        } else { // Orden no encontrada
            return null;
        }
    }

    public boolean deleteOrderById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAllOrders() {
        orderRepository.deleteAll();
        return true;
    }
}
