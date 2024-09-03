package com.uade.tpo.demo.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Director;
import com.uade.tpo.demo.repository.DirectorRepository;

@Service
public class DirectorService {
    
    @Autowired
    private DirectorRepository directorRepository;

    public Optional<Director> getDirectorByName(String name) {
        return directorRepository.findByName(name);
    }
    public Optional<Director> getDirectorById(Long id) {
        return directorRepository.findById(id);
    }
}
