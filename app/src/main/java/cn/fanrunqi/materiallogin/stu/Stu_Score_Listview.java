package cn.fanrunqi.materiallogin.stu;

/**
 * Created by jager on 2018/4/18.
 */

public class Stu_Score_Listview {
    private String course;
    private double score;
    public Stu_Score_Listview(String course, double score){
        this.course = course;
        this.score = score;
    }
    public String getCourse(){
        return course;
    }
    public double getScore(){
        return score;
    }
}
