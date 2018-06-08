package com.example.nipu.touristguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nipu.touristguide.modelclass.DivInformation;
import com.example.nipu.touristguide.activity.Placedetailsactivity;
import com.example.nipu.touristguide.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by NIPU on 1/14/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<DivInformation> divInformationList;
    private List<DivInformation> tmp;


    public RecyclerAdapter(List<DivInformation> divInformationList, Context context) {


        this.divInformationList = divInformationList;
        this.context = context; //this.context=context; hobe na fragment extends kora j,jody activity hoto thn context use hoto.

        tmp = new ArrayList<DivInformation>();
        tmp.addAll(divInformationList);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//for storing the reference of the view for one entry in the RecyclerVie
        // ViewHolder to keep the reference of View in memory, so when we need a new view it either creates a new ViewHolder object to inflate the layout and hold those references or it recycles one from existing stack.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_demo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DivInformation divInfo = divInformationList.get(position);
        holder.Name.setText(divInfo.getTitle());
        // holder.Calories.setText("Calories:"+Integer.toString(calories.get(position).getCalories()));
        Glide.with(context).load(divInfo.getImage()).into(holder.img);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = divInfo.getId();
                if (id > 0) {
                    Intent intent = new Intent(context, Placedetailsactivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                } else {
                    //send msg
                }
            }
        });

    }


    //filter category
    public void filterCat(String cat) {
        divInformationList.clear(); //suppose 1 to 5 ctegory data ache ekhn 1 to 7 category data caile 1 to 5 aga cler korbo then 1 to 7 add korbo
        if ("all".equalsIgnoreCase(cat)) {
            divInformationList.addAll(tmp);
        }
        else
            {
            for (int i = 0; i < tmp.size(); i++) {
                if (tmp.get(i).getType().toLowerCase(Locale.getDefault()).equalsIgnoreCase(cat)) { //getType() == landmark,park etc
                    divInformationList.add(tmp.get(i));
                }
            }
        }
        notifyDataSetChanged();
        Log.d("DD::", divInformationList.size() + "RRR");
    }

    //if you want to change list
    public void dataChanged(ArrayList<DivInformation> divInformationList) {
        this.divInformationList = divInformationList;
        this.tmp.clear();
        this.tmp.addAll(divInformationList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return divInformationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView Name;
        LinearLayout item;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.ppp);
            img = (ImageView) itemView.findViewById(R.id.imageView2);
            Name = (TextView) itemView.findViewById(R.id.textView2);
            mView = itemView;
        }
    }


}
