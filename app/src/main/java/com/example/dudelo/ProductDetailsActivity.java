package com.example.dudelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.dudelo.Model.Products;
import com.example.dudelo.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
 import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {


    private ElegantNumberButton elegantNumberButton;
    private ImageView productImageView;
    private TextView proNameText,des,price;

    private Button cartButton;
    private String productId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

      //  String bundle = getIntent().getStringExtra("pid");
//
//        try {
//
//        }catch (Exception e){
//            System.out.println("Exception"+ e);
//        }



       productId = getIntent().getStringExtra("pid");

//
//
//       productId = getIntent().getStringExtra("productNamee");
//       productId = getIntent().getStringExtra("productPricee");
//       productId = getIntent().getStringExtra("image");
//
//

        elegantNumberButton = findViewById(R.id.elegantButtonId);
        productImageView = findViewById(R.id.immmmmmmmmId);
        proNameText = findViewById(R.id.productNameId);
        des = findViewById(R.id.productDescriptionId);
        price = findViewById(R.id.productPriceId);
        cartButton =findViewById(R.id.addtoCartButtonId);



        getProductDetails(productId);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Toast.makeText(ProductDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();

                addingToCartList();

            }
        });


    }

    private void addingToCartList() {



        Calendar calforDate = Calendar.getInstance();

        String saveCurrentTime ,saveCurrentDate;





        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,YYYY");
        saveCurrentDate = currentDate.format(calforDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH: mm: ss, a");
        saveCurrentTime  = currentDate.format(calforDate.getTime());




        final   DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("pp",proNameText.getText().toString());
        cartMap.put("ccc",price.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",elegantNumberButton.getNumber());
        cartMap.put("discount","");


        cartRef.child("User View ").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                .child(productId).updateChildren(cartMap)


                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {



                        if(task.isSuccessful()){



                            cartRef.child("Admin View ").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                    .child(productId).updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            if(task.isSuccessful()){

                                                Toast.makeText(ProductDetailsActivity.this, "Add to Cart List", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                                                startActivity(intent);

                                            }

                                        }
                                    });





                        }

                    }
                });


    }

    private void getProductDetails(String productId) {


     final    DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if(dataSnapshot.exists()){
                   Products products = dataSnapshot.getValue(Products.class);

                   proNameText.setText(products.getProductNamee());
                   price.setText(products.getProductPricee());
                   des.setText(products.getDescription());
                  Picasso.get().load(products.getImage()).into(productImageView);


               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}


