package com.hsj.util;

import com.hsj.entity.Element;

/*设置URL的组成*/
public class GetURL {
    public static String getURL1(Element element){
         String tag=element.getTag();
         String number=element.getNumber();
         String pageStart=element.getPageStart();
         if(tag==null){tag="";}
         if(number==null){number="";}
         if(pageStart==null){pageStart="";}
         String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=" +tag+
                "&sort=recommend&page_limit="+number+"&page_start="+pageStart;
         return url;
    }
    public static String getURL2(String id){
        if (id==null){
            id="";
        }
        String url = "https://movie.douban.com/subject/"+id;
        return url;
    }
}
