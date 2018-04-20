package cn.fanrunqi.materiallogin;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import cn.fanrunqi.materiallogin.Util.Constant_value;
import cn.fanrunqi.materiallogin.Util.ValidateInput;
import cn.fanrunqi.materiallogin.httputil.HttpUtil;
import cn.fanrunqi.materiallogin.model.LoginInfo;
import cn.fanrunqi.materiallogin.stu.Stu_MainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    final int SERVER_WRONG = -1;//服务器错误
    final int STATE_PASSWORDWORNG = 0;//密码错误
    final int STATE_NOUSER = 1;//没有该用户
    final int STATE_STUDENT = 2;//学生用户
    final int STATE_TEACHER = 3;//老师用户

    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;//登录
    private CardView cv;
    private FloatingActionButton fab;
    private TextView tv_findPWD;

    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SERVER_WRONG://服务器连接失败
                    showLoginWrongHintDialog("服务器连接错误");
                    break;
                case STATE_STUDENT://学生登录
                    LoginInfo.loginInfo = new LoginInfo(etUsername.getText().toString().trim(),etPassword.getText().toString().trim());
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                    Intent i2 = new Intent(MainActivity.this,Stu_MainActivity.class);
                    startActivity(i2, oc2.toBundle());
                    break;
                case STATE_TEACHER://教师登录
                    break;
                case STATE_PASSWORDWORNG://密码错误
                    showLoginWrongHintDialog("密码错误");
                    break;
                case STATE_NOUSER://没有该用户
                    showLoginWrongHintDialog("该用户不存在");
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setClickListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        //测试用
        etUsername.setText("15823184588");
        etPassword.setText("123456");
        btGo = findViewById(R.id.bt_go);//登录
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        cv.getBackground().setAlpha(10);//0~255透明度值 ，0为完全
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

    //为组件添加点击事件
    private void setClickListener(){
        //设置悬浮按钮点击事件  打开注册界面
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(MainActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
        //登录按钮  点击事件
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //validate();
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                Intent i2 = new Intent(MainActivity.this,Stu_MainActivity.class);
                startActivity(i2, oc2.toBundle());
            }
        });

        tv_findPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                startActivity(new Intent(MainActivity.this, FindpwdActivity.class));
            }
        });
    }

    //验证用户输入的登录信息是否合法 是否正确
    public boolean validate() {
        String tel = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        //判断手机是否合法
        if(!ValidateInput.isPhone(tel)){//手机不合法
            showLoginWrongHintDialog("手机号不合法");
            etUsername.requestFocus();
            return false;
        }else if(ValidateInput.isEmpty(password)){//密码为空
            showLoginWrongHintDialog("密码未填写");
            etPassword.requestFocus();
            return false;
        }else {//信息格式正确 则连接服务器验证是否密码正确
            //手机没网
            if(!HttpUtil.isNetworkConnected(MainActivity.this)){
                showLoginWrongHintDialog("手机没有联网,请联网后重试!");
                return false;
            }
            //有网
            requestInternetPermission();
        }
        return true;
    }

    /**
     * 显示错误提示信息
     * @param hint 要提示的信息
     */
    public void showLoginWrongHintDialog(String hint){
        SweetAlertDialog st = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.ERROR_TYPE);
        st.setTitleText("啊哦...登录失败");
        st.setContentText(hint);
        st.show();
    }

    //请求联网权限
    private void requestInternetPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) !=
                PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"申请权限requestInternetPermission");
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},1);
        }else {
            connectServer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"申请权限");
            connectServer();
        }else {
            showLoginWrongHintDialog("没有权限连接网络,请设置权限后重试!");
        }
    }

    /**
     * 连接服务器
     */
    public void connectServer(){
        RequestBody requestBody = new FormBody.Builder()
                .add("tel",etUsername.getText().toString().trim())
                .add("password",etPassword.getText().toString().trim())
                .build();
        String url = HttpUtil.toUrl(Constant_value.HOST_IP,Constant_value.PORT,Constant_value.LoginServletPath);
        Log.d(TAG,url);
        HttpUtil.sendOkHttpRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = SERVER_WRONG;//服务器错误
                handle.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("LoginA",result);
                Message message = new Message();
                if ("1".equals(result)) {//学生登录正确
                    message.what = STATE_STUDENT;
                } else if("2".equals(result)){//教师登录正确
                    message.what = STATE_TEACHER;
                }else if("0".equals(result)){//密码错误
                    message.what = STATE_PASSWORDWORNG;
                }else {//没有相应用户
                    message.what = STATE_NOUSER;
                }
                handle.sendMessage(message);
            }
        });
    }
}
