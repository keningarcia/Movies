package com.keningarcia.movies.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keningarcia.movies.models.Serie;
import com.keningarcia.movies.repositories.ISerieRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class SerieController {
	
	private final ISerieRepo serieRepo;
	
	@CrossOrigin
	@GetMapping
	public List<Serie> getAllSeries(){
		return serieRepo.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<Serie> getSerieById(@PathVariable Long id){
		Optional<Serie> serie = serieRepo.findById(id);
		return serie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Serie> createSerie(@RequestBody Serie serie){
		Serie savedSerie = serieRepo.save(serie);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSerie);
	}
	
	
}
