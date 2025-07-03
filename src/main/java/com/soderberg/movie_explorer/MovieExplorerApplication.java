package com.soderberg.movie_explorer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieExplorerApplication implements CommandLineRunner{

	@Value("${themoviedb.api.key}")
	private String themoviedbApiKey;

	public static void main(String[] args) {
		SpringApplication.run(MovieExplorerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
