package com.hsj.dao;

import com.hsj.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*Spring Data JPA实现JpaRepository类*/
public interface MovieDao extends JpaRepository<Movie, Integer> {
}
