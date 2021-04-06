package com.java2nb.novel.core.utils;

import java.util.Random;

/**
 * 随机生成小说信息工具类
 * @author Administrator
 */
public class RandomBookInfoUtil {

    /**
     * 根据评分获取访问人数
     * */
    public static Long getVisitCountByScore(Float score){
        Long visitCount ;

        if(score > 9){
            visitCount = 100000 + new Random(100000).nextLong();
        }else if(score > 8){
            visitCount = 10000 + new Random(10000).nextLong();
        }else if(score > 7){
            visitCount = 1000 + new Random(1000).nextLong();
        }else if(score > 6){
            visitCount = 100 + new Random(100).nextLong();
        }else{
            visitCount = new Random(100).nextLong();
        }


        return  visitCount;

    }

    /**
     * 根据访问人数获取评分
     * */
    public static Float getScoreByVisitCount(Long visitCount){
        Float score;
        if(visitCount>100000) {
            score = 8.9f;
        }else if(visitCount>10000){
            score = 8.0f+(visitCount/10000)*0.1f;
        }else if(visitCount>1000){
            score = 7.0f+(visitCount/1000)*0.1f;
        }else if(visitCount>100){
            score = 6.0f+(visitCount/100)*0.1f;
        }else{
            score = 6.0f;
        }
        return score;
    }
    /**
     * 获取分类名
     * */
    public static String getCatNameById(Integer catId) {
        String catName = "Lainnya";

        switch (catId) {
            case 1: {
                catName = "Fantasi";
                break;
            }
            case 2: {
                catName = "Silat";
                break;
            }
            case 3: {
                catName = "Romansa";
                break;
            }
            case 4: {
                catName = "Militer";
                break;
            }
            case 5: {
                catName = "Sci-fi";
                break;
            }
            case 6: {
                catName = "Horor";
                break;
            }
            case 7: {
                catName = "Kampus";
                break;
            }
            case 8: {
                catName = "Sejarah";
                break;
            }
            case 9: {
                catName = "Aksi";
                break;
            }
            default: {
                break;
            }


        }
        return catName;
    }
}
