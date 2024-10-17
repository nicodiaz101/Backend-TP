package com.uade.tpo.demo.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.Director;
import com.uade.tpo.demo.repository.DirectorRepository;

@Service
public class DirectorService {
    
    @Autowired
    private DirectorRepository directorRepository;

    @Transactional(rollbackFor = Throwable.class)
    public Director createDirector(Director director) {
        return directorRepository.save(director);
    }
    
    public Page<Director> getDirectors(PageRequest pageable) {
        return directorRepository.findAll(pageable);
    }
    
    public Optional<Director> getDirectorByName(String name) {
        return directorRepository.findByName(name);
    }
    
    public Optional<Director> getDirectorById(Long id) {
        return directorRepository.findById(id);
    }
    
    @Transactional(rollbackFor = Throwable.class)
    public Director modifyDirector(Long id, Director directorDetails) {
        Optional<Director> optionalDirector = directorRepository.findById(id);
        if (optionalDirector.isPresent()) {
            Director director = optionalDirector.get();
            director.setName(directorDetails.getName());
            director.setMovies(directorDetails.getMovies());
            return directorRepository.save(director);
        } else { // Director no encontrado
            return null;
        }
    }
    
    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteDirectorByName(String name) {
        try {
            Optional<Director> optionalDirector = directorRepository.findByName(name);
            if (optionalDirector.isPresent()) {
                Director director = optionalDirector.get();
                directorRepository.delete(director);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting director by name: " + e.getMessage(), e);
        }
    }

    public boolean deleteAllDirectors() {
        directorRepository.deleteAll();
        return true;
    }
}
