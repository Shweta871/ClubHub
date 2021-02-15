package com.nucleus.events.clubhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nucleus.events.clubhub.Model.Club_request_model;
import com.nucleus.events.clubhub.R;

import java.util.List;

public class Club_requestAdapter extends RecyclerView.Adapter<Club_requestAdapter.ViewHolder> {


    Context context;
    List<Club_request_model> club_request_models;


    public Club_requestAdapter(Context context, List<Club_request_model> club_request_models) {
        this.context = context;
        this.club_request_models = club_request_models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs_reqest, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Club_request_model clubRequestModel = club_request_models.get(position);
        holder.club_name.setText(clubRequestModel.getCname());
        holder.club_catagory.setText(clubRequestModel.getCcatagory());
        holder.club_collage.setText(clubRequestModel.getCcollage());
        Glide.with(context).load(clubRequestModel.getClogo()).into(holder.c_image);

    }

    @Override
    public int getItemCount() {
        return club_request_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView club_name;
        public TextView club_catagory;
        public TextView club_collage;
        ImageView c_image;
        Button accept , decline;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            club_name = itemView.findViewById(R.id.r_c_name);
            club_catagory = itemView.findViewById(R.id.r_C_catagory);
            club_collage =  itemView.findViewById(R.id.r_c_collage);
            c_image = itemView.findViewById(R.id.r_c_image);
            accept = itemView.findViewById(R.id.btn_approve);
            decline = itemView.findViewById(R.id.btn_decline);

        }


        @Override
        public void onClick(View v) {

        }
    }
}
