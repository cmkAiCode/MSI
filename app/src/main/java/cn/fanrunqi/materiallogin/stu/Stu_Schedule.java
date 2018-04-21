package cn.fanrunqi.materiallogin.stu;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.fanrunqi.materiallogin.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Stu_Schedule extends Fragment implements ScreenShotable {
    /*school*/
    private int gridHeight,gridWidth;

    private RelativeLayout layout;

    private RelativeLayout tmpLayout;

    private static boolean isFirst = true;
    /*school*/
    DropDownMenu mDropDownMenu;

    private String headers[] = {"周"};

    private List<View> popupViews = new ArrayList<>();

    private ListDropDownAdapter ageAdapter;

    private String ages[] = {"不限", "第一周", "第二周", "第三周", "第四周", "第五周","第六周","第七周","第八周","第九周","第十周","第十一周","第十二周","第十三周","第十四周","第十五周","第十六周"};

    private int constellationPosition = 0;

     View view;

    private View Fragmentone_view;
    private Bitmap bitmap;
    public static Stu_Schedule newInstance(){
        return new Stu_Schedule();
    }
    public Stu_Schedule() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_stu__schedule, container, false);

        /*school*/
        tmpLayout = (RelativeLayout) view.findViewById(R.id.Monday);
        /*school*/

        mDropDownMenu = view.findViewById(R.id.dropDownMenu);
        initView();
        return view;
    }



    @Override
    public void takeScreenShot() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(Fragmentone_view.getWidth(),
                        Fragmentone_view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Fragmentone_view.draw(canvas);
                Stu_Schedule.this.bitmap = bitmap;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.Fragmentone_view = view.findViewById(R.id.container);
        boolean flag = Fragmentone_view == null ? false  : true;
    }
    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    private void initView() {

        //init age menu
        final ListView ageView = new ListView(getContext());

        ageView.setDividerHeight(0);

        ageAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(ages));

        ageView.setAdapter(ageAdapter);

        //init popupViews

        popupViews.add(ageView);

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ageAdapter.setCheckItem(position);

                mDropDownMenu.setTabText(position == 0 ? headers[0] : ages[position]);
                //noinspection SimplifiableIfStatement

                if (position == 1) {

                    if(isFirst) {

                        isFirst = false;

                        gridWidth = tmpLayout.getWidth();

                        gridHeight = tmpLayout.getHeight()/12;

                    }

                    String text="算法设计基础@W3502";

                    addView(1,1,2,text);

                    addView(7,2,3,text);

                    addView(5,9,10,text);

                    addView(4,2,3,text);

                    addView(3,5,5,text);

                    addView(4,10,12,text);

                }
                mDropDownMenu.closeMenu();
                isFirst = true;

            }

        });

        //init context view

        TextView contentView = new TextView(getContext());
        Log.d("stext",contentView == null ? "1--" : "2---");

        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        contentView.setGravity(Gravity.CENTER);

        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        //init dropdownview

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

    }

    private TextView createTv(int start, int end, String text){

        TextView tv = new TextView(getContext());

        /*

         指定高度和宽度

         */

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth,gridHeight*(end-start+1));

        /*

        指定位置

         */

        tv.setY(gridHeight*(start-1));

        tv.setLayoutParams(params);

        tv.setGravity(Gravity.CENTER);

        tv.setText(text);

        return tv;

    }

    private void addView(int i,int start,int end,String text){

        TextView tv;

        switch (i){

            case 1:

                layout = (RelativeLayout) view.findViewById(R.id.Monday);

                break;

            case 2:

                layout = (RelativeLayout) view.findViewById(R.id.Tuesday);

                break;

            case 3:

                layout = (RelativeLayout) view.findViewById(R.id.Wednesday);

                break;

            case 4:

                layout = (RelativeLayout) view.findViewById(R.id.Thursday);

                break;

            case 5:

                layout = (RelativeLayout) view.findViewById(R.id.Friday);

                break;

            case 6:

                layout = (RelativeLayout) view.findViewById(R.id.Saturday);

                break;

            case 7:

                layout = (RelativeLayout) view.findViewById(R.id.Sunday);

                break;

        }

        tv= createTv(start,end,text);

        tv.setBackgroundColor(Color.argb(100,start*5,(start+end)*20,0));

        layout.addView(tv);

    }

}
