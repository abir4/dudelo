package com.example.dudelo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminProductActivity extends AppCompatActivity {

    private String catagoryName,proname,descproduct,pricProduct,saveCurretDate,saveCurrentTime;
    private ImageView imagePRoduct;
    private EditText productname,description,price;
    private Button productAdd;
    private static final int  galleryKey = 1;
    private Uri imageUri;
    private String RandomProductKey,downloadImageUrl;
    private StorageReference storageRef;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        catagoryName = getIntent().getExtras().get("catagory").toString();

         storageRef = FirebaseStorage.getInstance().getReference().child("Product Image");


         databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

        imagePRoduct = findViewById(R.id.selectProductId);
        productname = findViewById(R.id.productNameId);
        description = findViewById(R.id.productDescriptionId);
        price  = findViewById(R.id.productPrice);
        progressDialog = new ProgressDialog(this);

        productAdd =  findViewById(R.id.addProductButtonId);

        imagePRoduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });


        productAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidProductsData();
            }
        });




    }

    private void openGallery() {

        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryKey);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode== galleryKey && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
          imagePRoduct.setImageURI(imageUri);

           Picasso.get().load(imageUri).into(imagePRoduct);

        }
    }



    private void ValidProductsData(){

        proname            = productname.getText().toString();
        descproduct       = description.getText().toString();
        pricProduct        = price.getText().toString();

        if(imageUri==null){

            Toast.makeText(this, "Product image is mandatroy...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(proname)){
            Toast.makeText(this, "Please Write product Name...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(descproduct)){
            Toast.makeText(this, "Please Write product Description...", Toast.LENGTH_SHORT).show();
        }
             else if(TextUtils.isEmpty(pricProduct)){
            Toast.makeText(this, "Please Write product Price...", Toast.LENGTH_SHORT).show();
        }else{
                 storeProductInformation();
        }


    }

    private void storeProductInformation() {



        progressDialog.setTitle("Adding new Product");
        progressDialog.setMessage(" Dear admin Go to, Please Wait while adding  are  new Product...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Calendar calendar = Calendar.getInstance();


        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurretDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH: mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());



            RandomProductKey = saveCurretDate + saveCurrentTime;

            final StorageReference filePath = storageRef.child(imageUri.getLastPathSegment()+RandomProductKey+".jpg");

            final UploadTask uploadTask = filePath.putFile(imageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {


                    String message = e.toString();
                    Toast.makeText(AdminProductActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Toast.makeText(AdminProductActivity.this, "Product Image Uploaded SuccessFully", Toast.LENGTH_SHORT).show();

                    Task<Uri>  uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                          if(!task.isSuccessful()){
                              throw  task.getException();
                          }

                          downloadImageUrl = filePath.getDownloadUrl().toString();
                          return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(
                            new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {


                            if(task.isSuccessful()){

                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(AdminProductActivity.this, "Product Image Save Database SuccessFully...", Toast.LENGTH_SHORT).show();

                                saveProductInfoDatabase();
                            }

                        }
                    });

                }
            });



    }

    private void saveProductInfoDatabase() {

     final     HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("pid",RandomProductKey);
        hashMap.put("date",saveCurretDate);
        hashMap.put("time",saveCurrentTime);
        hashMap.put("description",descproduct);
        hashMap.put("productNamee",proname);
        hashMap.put("productPricee",pricProduct);
        hashMap.put("catagory",catagoryName);
        hashMap.put("image",downloadImageUrl);


        databaseReference.child(RandomProductKey).updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Intent intent = new Intent(AdminProductActivity.this,AdminNewProductActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();

                            Toast.makeText(AdminProductActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();

                        }else{
                            progressDialog.dismiss();

                            String meesge =  task.getException().toString();
                            Toast.makeText(AdminProductActivity.this, "Error"+meesge, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
