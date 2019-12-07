package com.example.dudelo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dudelo.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.dudelo.Prevalent.Prevalent;

public class SettinsActivity extends AppCompatActivity {



    private CircleImageView profileImageView;
    private TextView closeTextView,updateTextView,changeProfileTextView;
    private EditText fullNameEditText,phoneNumberEditText,addressEditText;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins);
        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");



        profileImageView = findViewById(R.id.settingProfileImageId);
        closeTextView = findViewById(R.id.textViewCloseSettinsId);
        updateTextView = findViewById(R.id.textViewUpdateSettinsId);
        changeProfileTextView = findViewById(R.id.textViewChageProfileId);

        fullNameEditText = findViewById(R.id.editFullNameSettingId);
        phoneNumberEditText = findViewById(R.id.editphoneNuberSettingId);
        addressEditText = findViewById(R.id.editAddresSettingId);


        userInfoDisplay(profileImageView,fullNameEditText,phoneNumberEditText,addressEditText);

            closeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            updateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checker.equals("clicked")){

                        userInfoSaved();

                    }else{


                        updateOnlyUserInfo();


                    }

                }
            });


            changeProfileTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checker = "clicked";

                    CropImage.activity(imageUri)
                            .setAspectRatio(1, 1)
                            .start(SettinsActivity.this);
                }
            });


    }

    private void updateOnlyUserInfo() {


     final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

     final    HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", fullNameEditText.getText().toString());
        userMap. put("address", addressEditText.getText().toString());
        userMap. put("phoneOrder", phoneNumberEditText.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(SettinsActivity.this, HomeActivity.class));
        Toast.makeText(SettinsActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SettinsActivity.this, SettinsActivity.class));
            finish();
        }
    }




    private void userInfoSaved() {


        if (TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNumberEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))

        {
            uploadImage();



        }


    }

    private void uploadImage() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(Prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("name", fullNameEditText.getText().toString());
                                userMap. put("address", addressEditText.getText().toString());
                                userMap. put("phoneOrder", phoneNumberEditText.getText().toString());
                                userMap. put("image", myUrl);
                                ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                                progressDialog.dismiss();

                                startActivity(new Intent(SettinsActivity.this, HomeActivity.class));
                                Toast.makeText(SettinsActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(SettinsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }




    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText phoneNumberEditText, final EditText addressEditText) {


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(dataSnapshot.child("image").exists()){

                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);

                        fullNameEditText.setText(name);
                        phoneNumberEditText.setText(phone);
                        addressEditText.setText(address);



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
