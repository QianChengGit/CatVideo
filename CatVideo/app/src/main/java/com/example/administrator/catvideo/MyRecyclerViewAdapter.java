package com.example.administrator.catvideo;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/4/27.
 */


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<Data> dataList = new ArrayList<>();
    private List<Data> imageList = new ArrayList<>();

    public MyRecyclerViewAdapter(List<Data> dataList,List<Data> imageList){
        this.dataList = dataList;
        this.imageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.VideoItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                Data data = dataList.get(position);
                Intent intent = new Intent(parent.getContext(),ItemActivity.class);
                intent.putExtra("URL",data.getVideourl());
                intent.putExtra("name",data.getNm());
                parent.getContext().startActivity(intent);
            }
        });

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"这是图片！",Toast.LENGTH_SHORT).show();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data = dataList.get(position);
        Data image = imageList.get(position);
        holder.imageView.setImageBitmap(image.getBitmap());
        holder.name.setText(data.getNm());
        holder.type.setText(data.getCat());
        holder.actor.setText(data.getStar());
        holder.director.setText(data.getDir());
        holder.comingTime.setText(data.getPubDesc());
        holder.videoName.setText(data.getVideoName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        private View VideoItemView;
        private ImageView imageView;
        private TextView name;
        private TextView type;
        private TextView actor;
        private TextView director;
        private TextView comingTime;
        private TextView videoName;
        public MyViewHolder(View itemView) {
            super(itemView);
            VideoItemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.video_image);
            name = (TextView) itemView.findViewById(R.id.video_name);
            type = (TextView) itemView.findViewById(R.id.video_type);
            actor = (TextView) itemView.findViewById(R.id.video_actor);
            director = (TextView) itemView.findViewById(R.id.video_director);
            comingTime = (TextView) itemView.findViewById(R.id.video_comingTime);
            videoName = (TextView) itemView.findViewById(R.id.video_videoName);

        }
    }


}
