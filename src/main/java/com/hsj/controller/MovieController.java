package com.hsj.controller;

import com.hsj.entity.Element;
import com.hsj.entity.Movie;
import com.hsj.service.MovieService;
import com.hsj.util.GetURL;
import com.hsj.util.SpiderTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/*控制器*/
@Controller
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping("/")
    public String test(){
        return "getData";
    }

    @GetMapping("/testGet")
    public String addAndShow(Element element, Model model){
        //爬到数据，添加进数据表
        List<Movie> list= SpiderTool.getMovieList(element);
        StringBuffer sb=new StringBuffer();
        sb.append("通过URL:"+ GetURL.getURL1(element)+System.getProperty("line.separator"));
        sb.append("爬取到"+list.size()+"条数据,已存储进数据库。"+list.size()+"条数据如下："+System.getProperty("line.separator"));
        int i=1;
        for (Movie movie:list) {
            sb.append(i+"、"+movie.toString()+System.getProperty("line.separator"));
            i++;
        }
        movieService.addAll(list);
        model.addAttribute("content",sb);
        return "getData";
    }

    @GetMapping("/showAll")
    public String showAll(Model model,HttpServletRequest request,
                          @PageableDefault(size=10, sort={"id"}) Pageable pageable){
        Page<Movie> page=movieService.findAll(pageable);
        String URL=request.getServletPath()+"?";
        model.addAttribute("page",page);
        model.addAttribute("URL",URL);
        return "showRecord";
    }

    @GetMapping("/getMsg")
    @ResponseBody
    public Movie getMsg(Integer id){
         return movieService.findById(id).get();
    }
}
