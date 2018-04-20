package cn.fanrunqi.materiallogin.stu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.fanrunqi.materiallogin.R;

/**
 * Created by jager on 2018/4/18.
 */

public class Stu_Score_Listview_Adapter extends ArrayAdapter<Stu_Score_Listview> {
    private  int resourceId;

    public Stu_Score_Listview_Adapter(Context context, int textViewResourceId, List<Stu_Score_Listview> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stu_Score_Listview info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tcourse = (TextView) view.findViewById(R.id.course);
        TextView tscore = (TextView) view.findViewById(R.id.score);
        tcourse.setText(info.getCourse());
        tscore.setText(String.valueOf(info.getScore()));
        return view;
    }

}

