package com.example.demo.service;

import com.example.demo.core.model.MovieGroup;
import com.example.demo.core.model.Movie;
import com.example.demo.core.repository.MovieRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ExceptionMessage;
import com.example.demo.provider.MovieRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepositoryImpl movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findByQuery(final String query) {

        return getMovieGroup(query).getList();
    }

    public List<Movie> findByQueryOrderRating(final String query) {

        if (StringUtils.isEmpty(query)) {
            throw new BadRequestException(ExceptionMessage.BAD_REQUEST_QUERY_EMPTY);
        }

        return getMovieGroup(query).getListOrderRating();
    }

    public double calculateAverageUserRating(final String query) {

        return getMovieGroup(query).calculateAverageUserRating().getAsDouble();
    }

    private MovieGroup getMovieGroup(final String query) {

        return new MovieGroup(findByQueryImpl(query));
    }

    //TODO: 메서드 네이밍 고민 중
    private List<Movie> findByQueryImpl(final String query) {

        return movieRepository.findByQuery(query);
    }
}
