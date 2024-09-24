package com.keningarcia.movies.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

	private Long id;
	private String title;
	private int year;
	private double rating;
	private int votes;
	
	@Column(name = "image_url", length = 100)
	private String imageUrl;
}
