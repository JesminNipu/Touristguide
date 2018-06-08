package com.example.nipu.touristguide.otherclass;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nipu.touristguide.R;

/**
 * Created by NIPU on 1/14/2018.
 */

public class Demo_ini extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView name;

    public Demo_ini(View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.imageView2);
        name=(TextView) itemView.findViewById(R.id.textView2);

    }
    public ImageView getImageView()
    {
        return imageView;
    }

    public TextView getName()
    {
        return name;
    }

}
