package com.ace.legend.test.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ace.legend.test.R;
import com.ace.legend.test.RecyclerViewFragment;
import com.ace.legend.test.model.Flower;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by rohan on 6/24/15.
 */
public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Flower> flowerList = Collections.emptyList();
    private TouchListener touchListener;

    public CustomRecyclerAdapter(Context context, List<Flower> flowerList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.flowerList = flowerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout.item_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Flower flower = flowerList.get(position);
        viewHolder.tv_name.setText(flower.getName());
        viewHolder.tv_category.setText(flower.getCategory());
        Picasso.with(context).load(RecyclerViewFragment.PHOTO_BASE_URL + flower.getPhoto())
                .resize(100, 100)
                .into(viewHolder.iv_image);
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    public void setTouchListener(TouchListener touchListener){
        this.touchListener = touchListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{

        ImageView iv_image;
        ImageButton btn_overflow;
        TextView tv_name, tv_category;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            btn_overflow = (ImageButton) itemView.findViewById(R.id.btn_overflow);
            btn_overflow.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_UP:
                    //Toast.makeText(context, "from adapter", Toast.LENGTH_LONG).show();
                    touchListener.itemTouched(view, getPosition());
                    return true;

            }
            return false;
        }

    }

    public interface TouchListener{
        void itemTouched(View v, int position);
    }
}
