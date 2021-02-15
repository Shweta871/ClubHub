package com.nucleus.events.clubhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class AddEvents extends AppCompatActivity {

    private Toolbar mainToolBar;
    ActionBar actionBar;
    private ImageView add_image;

    private EditText addPostDesc;
    private Button post_btn;
    private Uri postImageUri=null;

    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private Bitmap compressedImageFile;
    private String current_club_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);


        firebaseFirestore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        current_club_id=firebaseAuth.getCurrentUser().getUid();


        add_image=findViewById(R.id.addImage);
        post_btn=findViewById(R.id.post_btn);
        addPostDesc=findViewById(R.id.add_post_desc);
    }

    public void NewPostImage(View view){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512,512)
                .setAspectRatio(1,1)
                .start(AddEvents.this);
    }


    public void NewPost(View view) {
        final String desc=addPostDesc.getText().toString();

        if (!TextUtils.isEmpty(desc) && postImageUri!=null){

            final String randomName= UUID.randomUUID().toString();
            StorageReference filePath=storageReference.child("post_images").child(randomName+".jpg");
            filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                    final String downloadURI=task.getResult().getStorage().getDownloadUrl().toString();
                    if (task.isSuccessful()){

                        File newImageFile=new File(postImageUri.getPath());

                        try {
                            compressedImageFile = new Compressor(AddEvents.this)
                                    .setMaxHeight(100)
                                    .setMaxWidth(100)
                                    .setQuality(2)
                                    .compressToBitmap(newImageFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] ThumbData = baos.toByteArray();


                        UploadTask uploadTask=storageReference.child("post_images/thumbs")
                                .child(randomName+".jpg").putBytes(ThumbData);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                String downloadThumbUri=taskSnapshot.getStorage().getDownloadUrl().toString();

                                Map<String, Object> postMap=new HashMap<>();
                                postMap.put("image_uri", downloadURI);
                                postMap.put("image_thumb", downloadThumbUri);
                                postMap.put("desc", desc);
                                postMap.put("user_id", current_club_id);
                                postMap.put("timeStamp", FieldValue.serverTimestamp());


                                firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if (task.isSuccessful()){

                                            Toast.makeText(AddEvents.this, "Post was added", Toast.LENGTH_LONG).show();

                                        }
                                        else {

                                        }
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                    else{


                    }
                }
            });
        }
        else{
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri=result.getUri();
                add_image.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
