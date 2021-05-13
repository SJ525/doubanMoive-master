package com.hsj.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "movie")
/*电影实体类*/
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;					//序号
	private String doubanId;		//豆瓣id
	private String imgURL;			//图片URL
	private String title;			//电影标题
	private String year;			//年份
	private String scriptwriter;	//作者
	private String director;		//导演
	private String story;			//故事内容
	private Double score;			//豆瓣评分

	public Movie(String doubanId, String imgURL) {
		this.doubanId = doubanId;
		this.imgURL = imgURL;
	}
}
