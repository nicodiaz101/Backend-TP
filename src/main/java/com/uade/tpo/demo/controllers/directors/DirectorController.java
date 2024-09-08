package com.uade.tpo.demo.controllers.directors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
        @GetMapping("/directors/{id}")
        public ResponseEntity<Director> getDirectorById(@PathVariable Long id) {
            Optional<Director> director = directorService.getDirectorById(id);
            return director.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
        }
    
        @GetMapping("/directors")
        public ResponseEntity<Director> getDirectorByName(@RequestParam String name) {
            Optional<Director> director = directorService.getDirectorByName(name);
            return director.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
        }
}
