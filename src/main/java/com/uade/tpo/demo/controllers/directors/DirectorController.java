package com.uade.tpo.demo.controllers.directors;

import java.util.Optional;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Director;
import com.uade.tpo.demo.service.DirectorService;

@RestController
@RequestMapping("/directors")
public class DirectorController {
    
    @Autowired
    private DirectorService directorService;
    
    @PostMapping
    public ResponseEntity<Object> createDirector(@RequestBody Director director) {
        try{
            Director createdDirector = directorService.createDirector(director);
            return ResponseEntity.created(URI.create("/directors/" + createdDirector.getDirectorId())).body(createdDirector);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable Long id) {
        Optional<Director> director = directorService.getDirectorById(id);
        return director.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Director> getDirectorByName(@PathVariable String name) {
        Optional<Director> director = directorService.getDirectorByName(name);
        return director.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<Page<Director>> getDirectors(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(directorService.getDirectors(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(directorService.getDirectors(PageRequest.of(page, size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyDirector(@PathVariable Long id, @RequestBody Director directorDetails) {
        try{
            Director modifiedDirector = directorService.modifyDirector(id, directorDetails);
            if (modifiedDirector != null) {
                return ResponseEntity.ok().location(URI.create("/orders/" + modifiedDirector.getDirectorId())).body(directorDetails);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }   
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDirectorById(@PathVariable Long id) {
        try {
            boolean isDeleted = directorService.deleteDirectorById(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllDirectors() {
        try {
            directorService.deleteAllDirectors();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
