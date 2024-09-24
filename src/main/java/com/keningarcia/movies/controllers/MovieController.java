package com.keningarcia.movies.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keningarcia.movies.models.Movie;
import com.keningarcia.movies.repositories.IMovieRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
	
	private final IMovieRepo movieRepo;
	
	@CrossOrigin
	@GetMapping
	public List<Movie> getAllMovies(){
		return movieRepo.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
		Optional<Movie> movie = movieRepo.findById(id);
		return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
		Movie savedMovie = movieRepo.save(movie);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
		if(!movieRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		movieRepo.deleteById(id);
		return ResponseEntity.noContent().build();	
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie){
		if(!movieRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		updatedMovie.setId(id);
		Movie savedMovie = movieRepo.save(updatedMovie);
		return ResponseEntity.ok(savedMovie);
	}
	
	@CrossOrigin
	@GetMapping("/{id}/{rating}")
	public ResponseEntity<Movie> voteMovie(@PathVariable Long id, @PathVariable double rating){
		if(!movieRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Optional<Movie> optional = movieRepo.findById(id);
		Movie movie = optional.get();
		
		double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes() + 1);
		
		movie.setVotes(movie.getVotes() + 1);
		movie.setRating(newRating);
		
		Movie savedMovie = movieRepo.save(movie);
		return ResponseEntity.ok(savedMovie);
	}
}
