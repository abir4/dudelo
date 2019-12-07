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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudelo.Model.Users;
import com.example.dudelo.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText loginPhoneNumber,loginPassword;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private CheckBox rememberCheckBox;
    private TextView adminTextView,notAdminTextView;
    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rememberCheckBox = findViewById(R.id.rememCheckBoxId);
        loginButton = findViewById(R.id.logInButtonId);
        loginPhoneNumber = findViewById(R.id.editPhNLoginId);
        loginPassword = findViewById(R.id.editTextPasssLogInId);
        linearLayout = findViewById(R.id.linaraId);
        adminTextView = findViewById(R.id.textAdminId);
        notAdminTextView = findViewById(R.id.textNotAdminId);
        Paper.init(this);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        adminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.setText("Login Admin");
                adminTextView.setVisibility(View.INVISIBLE);
                notAdminTextView.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        notAdminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.setText("Login");
                adminTextView.setVisibility(View.VISIBLE);
                notAdminTextView.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            }
        });






    }

    private void loginUser() {

        String phone = loginPhoneNumber.getText().toString();
        String password = loginPassword.getText().toString();

         if (TextUtils.isEmpty(phone)) {

            Toast.makeText(this, "Please Write the phone number...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please Write the password...", Toast.LENGTH_SHORT).show();
        }else{



             progressDialog.setTitle("Login Account");
             progressDialog.setMessage("Please Wait...");
             progressDialog.setCanceledOnTouchOutside(false);
             progressDialog.show();


             AllowAccessToAccount(phone,password);

         }

    }

    private void AllowAccessToAccount(final String phone, final String password) {


        if(rememberCheckBox.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }


        final DatabaseReference RootEf;
        RootEf = FirebaseDatabase.getInstance().getReference();

        RootEf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbName).child(phone).exists()) {

                    Users userData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {

                        if (userData.getPassword().equals(password)) {

                             if(parentDbName.equals("Admins"))
                            {

                                Toast.makeText(LoginActivity.this, " Welcome Admin, you are Login SuccessFully...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminNewProductActivity.class);
                                startActivity(intent);

                            }
                            else if(parentDbName.equals("Users")){

                                Toast.makeText(LoginActivity.this, "Login SuccessFully...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                Prevalent.currentOnlineUser = userData;
                                startActivity(intent);

                            }


                        } else {
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }

                    
                }else{
                    Toast.makeText(LoginActivity.this, "Accounts With this" +phone+ " number do not exits", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

