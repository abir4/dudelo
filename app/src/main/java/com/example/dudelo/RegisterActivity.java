package com.example.dudelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAcounntsButton;
    private EditText nameEditRegister,phoneEditRegister,passEditRegister;
  private   ProgressDialog progressDialog;
  private LinearLayout linearLayoutRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        linearLayoutRegister = findViewById(R.id.linearRegisterId);
        createAcounntsButton = findViewById(R.id.registerCreateAccountButtonId);
        nameEditRegister = findViewById(R.id.registerNameEdit);
        phoneEditRegister = findViewById(R.id.registerPhoneEdit);
        passEditRegister = findViewById(R.id.registerEdutPasssId);

        linearLayoutRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });



        progressDialog =  new ProgressDialog(this);




        createAcounntsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();


            }
        });



    }

    private void createAccount() {


        String name = nameEditRegister.getText().toString();
        String phone = phoneEditRegister.getText().toString();
        String pass = passEditRegister.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Write the name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {

            Toast.makeText(this, "Please Write the phone number...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){

            Toast.makeText(this, "Please Write the password...", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validatorPhoneNumber(name,phone,pass);

        }

    }

    private void validatorPhoneNumber(final String name, final String phone, final String pass) {

        final DatabaseReference RootEf;
        RootEf = FirebaseDatabase.getInstance().getReference();

        RootEf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("phone",phone);
                    hashMap.put("password",pass);
                    hashMap.put("name",name);

                    RootEf.child("Users").child(phone).updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Congratulation your account create is SuccessFull...", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);

                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: please try again after some time..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this,"This"+ phone+"Already Exits", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please again and another phone number..", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
