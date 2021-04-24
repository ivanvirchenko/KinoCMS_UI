package com.avada.kino.mapper;

import com.avada.kino.dto.MovieDto;
import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    public MovieDto toDto(Movie movie) {
        MovieDto dto = new MovieDto();

        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setImage(movie.getLogo());
        dto.setStartDate(movie.getStartDate());
        dto.setEndDate(movie.getEndDate());
        dto.setVideoLink(movie.getVideoLink());
        dto.setDescription(movie.getDescription());
        dto.setGallery(movie.getGallery());
        dto.setSeo(movie.getSeo());
        dto.setTypeIds(
                movie.getTypes().stream()
                        .map(MovieType::getId)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public Movie toMovie(MovieDto dto, List<MovieType> typesFromDb) {
        Movie movie = new Movie();

        Optional.ofNullable(dto.getId()).ifPresent(movie::setId);
        movie.setName(dto.getName());
        movie.setLogo(
                dto.getImage());
        movie.setStartDate(dto.getStartDate());
        movie.setEndDate(dto.getEndDate());
        movie.setVideoLink(dto.getVideoLink());
        movie.setDescription(dto.getDescription());
        movie.setGallery(dto.getGallery());
        movie.setSeo(dto.getSeo());
        Optional.ofNullable(dto.getTypeIds()).ifPresent(integers -> {
            for (Integer typeId : integers) {
                for (MovieType movieType : typesFromDb) {
                    if (movieType.getId() == typeId) {
                        movie.addType(movieType);
                    }
                }
            }
        });

        return movie;
    }

    public List<MovieDto> toDtoList(List<Movie> movies) {
        return movies.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Movie> toMovies(List<MovieDto> dtos, List<MovieType> allTypes) {
        return dtos.stream()
                .map(dto -> toMovie(dto, allTypes))
                .collect(Collectors.toList());
    }
}
