package cn.fanrunqi.materiallogin.model;

/**
 * Created by cmk on 2018/4/20.
 * 记录注册界面填写的信息
 */

public class RegisterInfo {
    private String st_username;
    private String st_id;
    private String st_password;
    private String st_repeatpassword;
    private String st_question;
    private String st_answer;

    public String getSt_username() {
        return st_username;
    }

    public void setSt_username(String st_username) {
        this.st_username = st_username;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getSt_password() {
        return st_password;
    }

    public void setSt_password(String st_password) {
        this.st_password = st_password;
    }

    public String getSt_repeatpassword() {
        return st_repeatpassword;
    }

    public void setSt_repeatpassword(String st_repeatpassword) {
        this.st_repeatpassword = st_repeatpassword;
    }

    public String getSt_question() {
        return st_question;
    }

    public void setSt_question(String st_question) {
        this.st_question = st_question;
    }

    public String getSt_answer() {
        return st_answer;
    }

    public void setSt_answer(String st_answer) {
        this.st_answer = st_answer;
    }
}
