

package cn.fanrunqi.materiallogin.stu;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.fanrunqi.materiallogin.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 */
public class Stu_Score extends Fragment implements ScreenShotable{
    private static final String TAG = "Fragment->Stu_Score:";
    private View Fragmentone_view;
    private Bitmap bitmap;
    private List<String> data_list= new ArrayList<String>();
    private List<Stu_Score_Listview> list = new ArrayList<>();
    private ArrayAdapter<String> arr_adapter;
    private HintPopupWindow hintPopupWindow;
    static Button bu;
    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //dfa
            String xueqi = "";
            switch (msg.what){
                case 0:
                    xueqi = "大一上";
                    break;
                case 1:
                    xueqi = "大一下";
                    break;
                case 2:
                    xueqi = "大二上";
                    break;
                case 3:
                    xueqi = "大二下";
                    break;
                case 4:
                    xueqi = "大三上";
                    break;
                case 5:
                    xueqi = "大三下";
                    break;
                default:
                    xueqi = "wrong";
                    break;
            }
            bu.setText(xueqi);
            return false;
        }
    });

    public Stu_Score() {
        // Required empty public constructor
    }
    public static Stu_Score newInstance(){
        return new Stu_Score();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stu__score, container, false);
        bu = (Button)view.findViewById(R.id.button);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPopupWindow.showPopupWindow(view);
            }
        });
        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        strList.add("大一上");
        strList.add("大一下");
        strList.add("大二上");
        strList.add("大二下");
        strList.add("大三上");
        strList.add("大三下");

        final ArrayList<View.OnClickListener> clickList = new ArrayList<>();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };
        clickList.add(clickListener);
        clickList.add(clickListener);
        clickList.add(clickListener);
        clickList.add(clickListener);
        clickList.add(clickListener);
        clickList.add(clickListener);
        //具体初始化逻辑看下面的图
        hintPopupWindow = new HintPopupWindow(getActivity(), strList, clickList);
        initList();

        Stu_Score_Listview_Adapter adapter = new Stu_Score_Listview_Adapter(view.getContext(),R.layout.stu_score_form,list);
        ListView listview = (ListView)view.findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        return view;
    }
    private void initList(){

        for(int i=0;i<5;i++){
            Stu_Score_Listview cu1 = new Stu_Score_Listview("应用程序开发",99);
            list.add(cu1);
            Stu_Score_Listview cu2 = new Stu_Score_Listview("软件过程管理",88);
            list.add(cu2);
            Stu_Score_Listview cu3 = new Stu_Score_Listview("算法设计与分析",98);
            list.add(cu3);
            Stu_Score_Listview cu4 = new Stu_Score_Listview("游戏程序设计",97);
            list.add(cu4);
            Stu_Score_Listview cu5 = new Stu_Score_Listview("Linux使用教程",89);
            list.add(cu5);
            Stu_Score_Listview cu6 = new Stu_Score_Listview("人机交互",95);
            list.add(cu6);
            Stu_Score_Listview cu7 = new Stu_Score_Listview("C#程序设计",96);
            list.add(cu7);
            Stu_Score_Listview cu8 = new Stu_Score_Listview("软件体系结构",93);
            list.add(cu8);
        }

    }

    @Override
    public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(Fragmentone_view.getWidth(),
//                        Fragmentone_view.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                Fragmentone_view.draw(canvas);
//                Stu_Score.this.bitmap = bitmap;
//            }
//        };
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(Fragmentone_view.getWidth(),
                        Fragmentone_view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Fragmentone_view.draw(canvas);
                Stu_Score.this.bitmap = bitmap;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.Fragmentone_view = view.findViewById(R.id.container);
        boolean flag = Fragmentone_view == null ? false  : true;
        Log.d(TAG, ""+flag);
    }
    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

}
