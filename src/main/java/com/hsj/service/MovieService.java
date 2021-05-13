package com.hsj.service;

import com.hsj.dao.MovieDao;
import com.hsj.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*服务器类*/
@Service
public class MovieService {
    @Autowired
    MovieDao movieDao;

    //增加所有数据
    public void addAll(List<Movie> list){
        movieDao.saveAll(list);
    }

    //查找所有数据
    public Page<Movie> findAll(Pageable pageable){
        return movieDao.findAll(pageable);
    }

    //删除一条数据
    public void delete(Movie movie){
        movieDao.delete(movie);
    }

    //清空所有数据
    public void deleteAll(){
        movieDao.deleteAll();
    }

    //查找某条数据
    public Optional<Movie> findById(Integer id){
        return movieDao.findById(id);
    }
}
