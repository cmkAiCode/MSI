package cn.fanrunqi.materiallogin.model;

/**
 * Created by cmk on 2018/4/20.
 * 用于保存用户的登录信息
 */

public class LoginInfo {
    public static LoginInfo loginInfo;
    private String tel;
    private String password;

    public String getTel() {
        return tel;
    }

    public LoginInfo(String tel, String password) {
        this.tel = tel;
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
