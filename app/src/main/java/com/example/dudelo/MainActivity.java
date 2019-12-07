package com.example.dudelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dudelo.Model.Users;
import com.example.dudelo.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinButton,mainLoginButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        joinButton = findViewById(R.id.joinNowButton);
        mainLoginButton = findViewById(R.id.mainLoginButton);
        Paper.init(this);


        mainLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);


            }
        });


        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey !=""  && UserPasswordKey !=""){

            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){


                AllowAccess(UserPhoneKey,UserPasswordKey);


                progressDialog.setTitle("Already Login");
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


            }


        }

    }

    private void AllowAccess(final String phone, final String pass) {


        final DatabaseReference RootEf;
        RootEf = FirebaseDatabase.getInstance().getReference();

        RootEf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Users").child(phone).exists()) {

                    Users userData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {

                        if (userData.getPassword().equals(pass)) {

                            Toast.makeText(MainActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                            Prevalent.currentOnlineUser = userData;

                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }


                }else{
                    Toast.makeText(MainActivity.this, "Accounts With this"+phone+ " number do not exits", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
