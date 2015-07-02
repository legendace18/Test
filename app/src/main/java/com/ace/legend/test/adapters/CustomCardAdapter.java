package com.ace.legend.test.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ace.legend.test.CardViewFragment;
import com.ace.legend.test.R;
import com.ace.legend.test.model.Flower;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by rohan on 6/25/15.
 */
public class CustomCardAdapter extends RecyclerView.Adapter<CustomCardAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Flower> flowerList = Collections.emptyList();

    public CustomCardAdapter(Context context, List<Flower> flowerList){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.flowerList = flowerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Flower flower = flowerList.get(position);
        holder.tv_name.setText(flower.getName());
        holder.tv_description.setText(flower.getInstructions());
        Picasso.with(context).load(CardViewFragment.PHOTO_BASE_URL + flower.getPhoto()).resize(100, 100).into(holder.iv_cardImage);
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_cardImage;
        TextView tv_name, tv_description;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_cardImage = (ImageView) itemView.findViewById(R.id.iv_cardImage);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }
}
