package com.hsj.util;

import com.hsj.entity.Element;
import com.hsj.entity.Movie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/*jsoup爬虫工具类*/
public class SpiderTool {
    //获取电影列表
    public static List<Movie> getMovieList(Element element){
        List<Movie> list = new ArrayList<>();
        try {
            //获取排行榜（JSON数据）
            String url = GetURL.getURL1(element);
            StringBuilder stringBuilder = new StringBuilder();
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine);
            in.close();
            String jsonString = stringBuilder.toString();
            JSONObject json;
            json = new JSONObject(jsonString);
            JSONArray jsonArray = json.getJSONArray("subjects");
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject subject = (JSONObject)jsonArray.get(i);
                Movie movie=new Movie((String)subject.get("id"),(String) subject.get("cover"));
                getMovie(movie);
                list.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    //为每个Movie JavaBean补全具体数据
    private static Movie getMovie(Movie movie){
        String id=movie.getDoubanId();
        String url =GetURL.getURL2(id);
        try {
            Document doc = Jsoup.connect(url).get();
            if(doc == null)
                return null;
            org.jsoup.nodes.Element content = doc.getElementById("content");
            if(content == null)
                return null;
            movie.setDoubanId(id);
            Elements h1 = content.getElementsByTag("h1");
            //获取电影名称和年份
            Elements spans = h1.get(0).getElementsByTag("span");
            movie.setTitle(spans.get(0).text());
            movie.setYear(spans.get(1).text().substring(1, 5));

            org.jsoup.nodes.Element article = content.getElementById("info");
            //获取导演
            Elements directors = article.getElementsByIndexEquals(0);
            spans = directors.get(0).getElementsByClass("attrs");
            movie.setDirector(spans.get(0).getElementsByTag("a").text());
            //获取编剧
            Elements scriptwriter = article.getElementsByIndexEquals(1);
            spans = scriptwriter.get(0).getElementsByClass("attrs");
            Elements as = spans.get(0).getElementsByTag("a");
            String scriptwriterStr = "";
            for(org.jsoup.nodes.Element a : as){
                scriptwriterStr += a.text() + "/";
            }
            scriptwriterStr = scriptwriterStr.substring(0, scriptwriterStr.length()-1);
            movie.setScriptwriter(scriptwriterStr);

            //获取评分
            movie.setScore(Double.valueOf((content.getElementsByClass("rating_num").get(0).text())));

            //获取剧情
            movie.setStory(content.getElementById("link-report").getElementsByTag("span").get(0).text());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return movie;
    }
}
