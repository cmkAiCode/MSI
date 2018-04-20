package cn.fanrunqi.materiallogin.stu;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.fanrunqi.materiallogin.R;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * 学生用户进入时的主界面
 */
public class Stu_MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener{


    final int SERVER_WRONG = -1;//服务器错误
    final int STATE_PASSWORDWORNG = 0;//密码错误
    final int STATE_NOID = 1;//没有该学号或工号
    final int STATE_TELEXITS = 2;//手机号已被注册
    final int STATE_IDEXITS = 3;//学号或工号已被注册

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    View view;
    private final String TAG_ME = "mine";
    private final String TAG_TIMETABLE = "timetable";
    private final String TAG_SCORE = "score";
    private final String TAG_NOTICE = "notice";
    private final String TAG_DIARY = "diary";
    private final String TAG_EXIT = "exit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition slide_right = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);
//        getWindow().setEnterTransition(slide_right);

        setContentView(R.layout.stu_main);
        overridePendingTransition(R.anim.right_to_current,R.anim.current_stay_translate);

        findViewById(R.id.toolbarback).getBackground().setAlpha(65);//0~255透明度值 ，0为完全
        Test_fragment fragmentOne = Test_fragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragmentOne)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, fragmentOne, drawerLayout, this);


    }
    //侧边栏   功能条
    private void createMenuList() {
        SlideMenuItem menuItem = new SlideMenuItem(TAG_ME, R.drawable.ic_me);
        list.add(menuItem);
        SlideMenuItem menuItem1 = new SlideMenuItem(TAG_TIMETABLE, R.drawable.ic_timetable);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem(TAG_SCORE, R.drawable.ic_score2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(TAG_NOTICE, R.drawable.ic_notice4);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(TAG_DIARY, R.drawable.ic_diary3);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(TAG_EXIT, R.drawable.ic_exit);
        list.add(menuItem5);
    }

    //设置ToolBar
    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case "close":
                return screenShotable;
            case "notice":
                replaceFragment(slideMenuItem,screenShotable,position);
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem,screenShotable, position);

        }
    }

    private ScreenShotable replaceFragment(Resourceble slideMenuItem,ScreenShotable screenShotable, int topPosition) {
        view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        //findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        Fragment fragment = null;
        switch (slideMenuItem.getName()){
            case TAG_ME:
                fragment  = Test_fragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment ).commit();
                return (ScreenShotable) fragment;
            case TAG_TIMETABLE:
                fragment = Test_fragment .newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment ).commit();
                return (ScreenShotable) fragment;
            case TAG_SCORE:
                fragment = Test_fragment .newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment ).commit();
                return (ScreenShotable) fragment;
            case TAG_NOTICE:
                fragment = Test_fragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                return (ScreenShotable) fragment;
            case TAG_DIARY:
                fragment = Test_fragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                return (ScreenShotable) fragment;
            case TAG_EXIT:
                fragment = Test_fragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                return (ScreenShotable) fragment;
            default:
                break;
        }
        return (ScreenShotable) fragment;
    }


    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


}
