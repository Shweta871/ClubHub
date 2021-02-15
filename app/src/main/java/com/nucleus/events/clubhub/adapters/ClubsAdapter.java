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
import com.nucleus.events.clubhub.Model.ClubsModel;
import com.nucleus.events.clubhub.R;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder> {

    List<ClubsModel> clubsModels;
    Context context;

    public ClubsAdapter(List<ClubsModel> clubsModels, Context context) {
        this.clubsModels = clubsModels;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs, parent, false);

        ClubsAdapter.ViewHolder viewHolder = new ClubsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ClubsModel clubsModel = clubsModels.get(position);
        holder.club_name.setText(clubsModel.getCname());
        holder.club_catagory.setText(clubsModel.getCcatagory());
        holder.club_collage.setText(clubsModel.getCcollage());
        holder.club_rating.setText(clubsModel.getCrating());
        holder.club_memeber.setText(clubsModel.getCmembers()+" Members");
        Glide.with(context).load(clubsModel.getClogo()).into(holder.c_image);


    }

    @Override
    public int getItemCount() {
        return clubsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView club_name;
        public TextView club_catagory;
        public TextView club_collage;
        public TextView club_rating;
        public TextView club_memeber;
        public Button get_detail;
        ImageView c_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            club_name = itemView.findViewById(R.id.c_name);
            club_catagory = itemView.findViewById(R.id.c_catagory);
            club_collage =  itemView.findViewById(R.id.c_collage);
            c_image = itemView.findViewById(R.id.c_image);
            club_rating = itemView.findViewById(R.id.c_rating);
            club_memeber = itemView.findViewById(R.id.c_members);
            get_detail = itemView.findViewById(R.id.btn_get_details);

        }


        @Override
        public void onClick(View view) {

        }
    }

}
