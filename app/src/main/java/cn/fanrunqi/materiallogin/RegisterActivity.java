package cn.fanrunqi.materiallogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.fanrunqi.materiallogin.Util.Constant_value;
import cn.fanrunqi.materiallogin.Util.ValidateInput;
import cn.fanrunqi.materiallogin.httputil.HttpUtil;
import cn.fanrunqi.materiallogin.stu.Stu_MainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    //cmk
    private final String TAG = "RegisterActivity";
    final int SERVER_WRONG = -1;//服务器错误
    final int STATE_SUCCESS = 0;//注册成功
    final int STATE_TELEXITS = 1;//手机号已注册
    final int STATE_IDEIXTS = 2;//学号或者工号已被注册
    final int STATE_IDNOTEIXTS = 3;//不存在该学号或工号


    private FloatingActionButton fab;
    private CardView cvAdd;
    //cmk
    private EditText et_username;
    private EditText et_stu_or_tech_id;
    private EditText et_password;
    private EditText et_repeatpassword;
    private EditText et_question;
    private EditText et_answer;
    private Button bt_go;

    //cmk
    private String st_username;
    private String st_id;
    private String st_password;
    private String st_repeatpassword;
    private String st_question;
    private String st_answer;
    //cmk
    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SERVER_WRONG://服务器连接失败
                    showLoginWrongHintDialog("服务器连接错误");
                    break;
                case STATE_SUCCESS://注册成功
                    new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("恭喜你")
                            .setContentText("注册成功")
                            .setConfirmText("去登陆")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    animateRevealClose();
                                }
                            })
                            .setCancelText("取消")
                            .show();
                    break;
                case STATE_TELEXITS://手机号已注册
                    showLoginWrongHintDialog("手机号已被注册");
                    break;
                case STATE_IDEIXTS://ID已注册
                    showLoginWrongHintDialog("学号已被注册");
                    break;
                case STATE_IDNOTEIXTS://ID已注册
                    showLoginWrongHintDialog("学号或工号不存在");
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ShowEnterAnimation();
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
        //cmk
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_username = et_username.getText().toString().trim();
                st_id = et_stu_or_tech_id.getText().toString().trim();
                st_password = et_password.getText().toString().trim();
                st_repeatpassword = et_repeatpassword.getText().toString().trim();
                st_question = et_question.getText().toString().trim();
                st_answer = et_answer.getText().toString().trim();
                Log.d(TAG,st_username);
                validateInput();//验证表单
            }
        });

    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        //cmk
        et_username = (EditText) findViewById(R.id.et_username);
        et_stu_or_tech_id = (EditText) findViewById(R.id.et_stu_or_tech_id);
        et_password = (EditText) findViewById(R.id.et_password);
        et_repeatpassword = (EditText) findViewById(R.id.et_repeatpassword);
        et_question = (EditText) findViewById(R.id.et_question);
        et_answer = (EditText) findViewById(R.id.et_answer);
        bt_go = (Button) findViewById(R.id.bt_go);
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    /**
     * written by cmk
     * 验证表单的输入
     * @return 表单验证结果
     */
    private boolean validateInput(){
        //验证手机号
        if(!ValidateInput.isPhone(st_username)){
            showWrongInfo("手机号错误");
            et_username.requestFocus();
            return false;
        }else if(ValidateInput.isEmpty(st_id)){
            showWrongInfo("学号或者工号不能为空");
            et_stu_or_tech_id.requestFocus();
            return false;
        }else if(ValidateInput.isEmpty(st_password)){
            showWrongInfo("密码不能为空");
            et_stu_or_tech_id.requestFocus();
            return false;
        }else if(!st_password.equals(st_repeatpassword)){
            showWrongInfo("密码不一致不");
            et_stu_or_tech_id.requestFocus();
            return false;
        }else if(ValidateInput.isEmpty(st_question) || ValidateInput.isEmpty(st_answer)){
            showWrongInfo("密保问题和答案不能为空");
            et_stu_or_tech_id.requestFocus();
            return false;
        }else if(!HttpUtil.isNetworkConnected(RegisterActivity.this)){  //手机没网
                showWrongInfo("手机没有联网,请联网后重试!");
                return false;
        }else {
            connectServer();
        }

        return false;
    }

    /**
     * written by cmk 显示对话框
     * @param info
     */
    public void showWrongInfo(String info){
        showLoginWrongHintDialog(info);
//        final NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(this);
//        dialogBuilder
//                .withTitle("不能注册")
//                .withMessage(info)
//                .withButton1Text("确定")
//                .withEffect(Effectstype.Shake)
//                .withIcon(getResources().getDrawable(R.drawable.register_wrong))
//                .withDialogColor("#2ea67f")
//                .setButton1Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogBuilder.dismiss();
//                    }
//                })
//                .show();
    }

    /**
     * written by cmk
     * 连接服务器
     */
    public void connectServer(){
        Log.d(TAG,"connectServer " + st_username);
        RequestBody requestBody = new FormBody.Builder()
                .add("tel",st_username)
                .add("password",st_password)
                .add("id",st_id)
                .add("question",st_question)
                .add("answer",st_answer)
                .build();
        String url = HttpUtil.toUrl(Constant_value.HOST_IP,Constant_value.PORT,Constant_value.RegisterServletPath);
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
                Message message = new Message();
                if ("0".equals(result)) {//注册成功
                    message.what = STATE_SUCCESS;
                } else if("1".equals(result)){//手机号已被注册
                    message.what = STATE_TELEXITS;
                }else if("2".equals(result)){//id已被注册
                    message.what = STATE_IDEIXTS;
                }else if("3".equals(result)){//id不存在
                    message.what = STATE_IDNOTEIXTS;
                }
                handle.sendMessage(message);
            }
        });
    }

    /**
     * written by cmk
     * 显示对话框
     * @param hint
     */
    public void showLoginWrongHintDialog(String hint){
        new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                .setTitleText("注册失败")
                .setContentText(hint)
                .show();
    }
}
