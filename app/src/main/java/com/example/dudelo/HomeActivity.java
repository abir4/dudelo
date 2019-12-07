package com.example.dudelo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dudelo.Model.Products;
import com.example.dudelo.Prevalent.Prevalent;
import com.example.dudelo.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference productRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        recyclerView = findViewById(R.id.recyClearViewId);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


       productRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerview = navigationView.getHeaderView(0);
        TextView userTextView = headerview.findViewById(R.id.textViewUserProfile);
        CircleImageView userprofileImage = headerview.findViewById(R.id.profile_image);
        userTextView.setText(Prevalent.currentOnlineUser.getName());

        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.mipmap.ic_launcher_round).into(userprofileImage);

//aikhne ache kichu




    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration =new AppBarConfiguration.Builder(
    R.id.nav_home,R.id.nav_settings,R.id.nav_logout,
    R.id.nav_catagory,R.id.nav_order)
            .

    setDrawerLayout(drawer)
                .

    build();

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()

    {
        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){

        if (menuItem.getItemId() == R.id.cart) {

            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);


        } else if (menuItem.getItemId() == R.id.order) {

        } else if (menuItem.getItemId() == R.id.catagory) {

        } else if (menuItem.getItemId() == R.id.settings) {

            Intent intent = new Intent(HomeActivity.this, SettinsActivity.class);
            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.logout) {

            Paper.book().destroy();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

        return true;
    }
    });













    }


    @Override
    protected void onStart() {




        super.onStart();

        ///cfjdfjneffncfnmnmvnfvbfnm

    final  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");


        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>().setQuery(productRef,Products.class)
                        .build();



        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =

                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Products model) {

                        holder.txtProductName.setText(model.getProductNamee());
                        holder.txxProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText(( model.getProductPricee()));
                        Picasso.get().load(model.getImage()).into(holder.productImageRecy);


                        ///new productDetailsAcitiy work


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                    Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);





                            }
                        });



                    }




                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);




                        return holder;
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}



