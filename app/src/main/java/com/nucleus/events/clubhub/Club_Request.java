package com.nucleus.events.clubhub;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nucleus.events.clubhub.Model.Club_request_model;
import com.nucleus.events.clubhub.adapters.Club_requestAdapter;

import java.util.ArrayList;
import java.util.List;

public class Club_Request extends AppCompatActivity {

    RecyclerView club_req;
    Club_requestAdapter clubRequestAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__request);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs");

        final List<Club_request_model> list = new ArrayList<>();

        club_req = findViewById(R.id.club_request_recycler_view);
        club_req.setHasFixedSize(true);
        club_req.setLayoutManager(new LinearLayoutManager(Club_Request.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {

                for (DataSnapshot dataSnapshot : Snapshot.getChildren()) {

                    Club_request_model clubRequestModel = dataSnapshot.getValue(Club_request_model.class);

                    Toast.makeText(Club_Request.this, "loading clubs", Toast.LENGTH_SHORT).show();

                    list.add(clubRequestModel);
                }

                clubRequestAdapter = new Club_requestAdapter(Club_Request.this, list);

                club_req.setAdapter(clubRequestAdapter);
                Toast.makeText(Club_Request.this, "loaded clubs", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
