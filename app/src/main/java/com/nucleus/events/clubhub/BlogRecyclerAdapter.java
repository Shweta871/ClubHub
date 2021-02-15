package com.nucleus.events.clubhub;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    public List<BlogPost> blog_list;
    public Context context;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;


    public BlogRecyclerAdapter(List<BlogPost> blog_list){
        this.blog_list=blog_list;
    }


    @Override
    public BlogRecyclerAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item, parent,false);

        context=parent.getContext();
        firebaseFirestore= FirebaseFirestore.getInstance();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String desc_data=blog_list.get(position).getDesc();
        holder.setDescText(desc_data);

        final String image_url=blog_list.get(position).getImage_uri();
        String thumbUrl=blog_list.get(position).getImage_thumb();
        holder.setBlogImage(image_url);


        long millisecond=blog_list.get(position).getTimeStamp().getTime();
        String dateString= DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
        holder.setTime(dateString);


        holder.interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Student_club_joining.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView descView;
        private View mView;
        private ImageView blogImageView;
        private TextView blogDate;
        private ImageView interested;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            interested=mView.findViewById(R.id.star);
        }

        public void setDescText(String descText){

            descView=mView.findViewById(R.id.blog_desc);
            descView.setText(descText);
        }

        public void setBlogImage(String downloadUri){

            blogImageView=mView.findViewById(R.id.blog_post_image);
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.square);
            Glide.with(context).load(downloadUri).into(blogImageView);
        }

        public void setTime(String Date){
            blogDate=mView.findViewById(R.id.blog_date);
            blogDate.setText(Date);
        }



    }

}
