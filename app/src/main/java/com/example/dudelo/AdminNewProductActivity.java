package com.example.dudelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class AdminNewProductActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private  ImageView watch,xaimifacesoni,wireless,portablemini,
            portable,fitnesvand,excelvan,usbminiportable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_product);


        watch = findViewById(R.id.watchId);
        xaimifacesoni=findViewById(R.id.xiamoifaceId);
        wireless = findViewById(R.id.wirelessblutothIdId);
        portablemini = findViewById(R.id.portableminiId);
     //   blutoothkaroke = findViewById(R.id.blutothkarokeId);

        portable = findViewById(R.id.portabeBlutoothId);
        fitnesvand = findViewById(R.id.fitnessBandId);
        excelvan = findViewById(R.id.exelevenId);
     //   wifiipsec = findViewById(R.id.)
        usbminiportable = findViewById(R.id.usbMiniIdPortable);
      //  wifiipsec = findViewById(R.id.wifiCameraId);


        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","watch");
                startActivity(intent);
            }
        });


        xaimifacesoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","xaimifacesoni");
                startActivity(intent);
            }
        });


        wireless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","wireless");
                startActivity(intent);
            }
        });


        portablemini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","portablemini");
                startActivity(intent);
            }
        });
////    4 step shssssss

//        blutoothkaroke.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
//                intent.putExtra("catagory","blutoothkaroke");
//                startActivity(intent);
//            }
//        });


        portable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","portable");
                startActivity(intent);
            }
        });


        fitnesvand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","fitnesvand");
                startActivity(intent);
            }
        });


        excelvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","excelvan");
                startActivity(intent);
            }
        });


        usbminiportable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
                intent.putExtra("catagory","usbminiportable");
                startActivity(intent);
            }
        });

//
//        wifiipsec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminNewProductActivity.this,AdminProductActivity.class);
//                intent.putExtra("catagory","wifiipsec");
//                startActivity(intent);
//            }
//        });



        int [] image = {R.drawable.saleofferslide1,
                R.drawable.midseasionselslide2,
                R.drawable.supersaleslide3};

        viewFlipper = findViewById(R.id.viewFlipperId);


        // for loop use

      for(int i =0; i<image.length; i++){
            flipperImage(image[i]);
        }





    }

    public void flipperImage(int i){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(i);

        viewFlipper.addView(imageView);

        viewFlipper.setFlipInterval(4000);// chage 4 sec auto
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
}
