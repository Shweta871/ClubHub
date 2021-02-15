package com.nucleus.events.clubhub.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.nucleus.events.clubhub.BlogPost;
import com.nucleus.events.clubhub.BlogRecyclerAdapter;
import com.nucleus.events.clubhub.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Events_fragment extends Fragment {

    private RecyclerView blog_list_view;
    private List<BlogPost> blog_list;
    FirebaseFirestore firebaseFirestore;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private FirebaseAuth firebaseAuth;

    private DocumentSnapshot lastVisible;

    private Boolean isFirstPageLoad=true;

    public Events_fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_events_fragment, container, false);

        blog_list=new ArrayList<>();
        blog_list_view=view.findViewById(R.id.blog_listView);

        firebaseAuth=FirebaseAuth.getInstance();
        blogRecyclerAdapter=new BlogRecyclerAdapter(blog_list);

        blog_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        blog_list_view.setAdapter(blogRecyclerAdapter);

        if(firebaseAuth.getCurrentUser() !=null) {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, "Error:" + e.getMessage());
                    } else {
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() != null && doc.getType() == DocumentChange.Type.ADDED) {


                                BlogPost blogPost = doc.getDocument().toObject(BlogPost.class);
                                blog_list.add(blogPost);


                            }
                        }
                    }

                }
            });
        }
        return view;

    }


    public interface OnFragmentInteractionListener {
    }
}




