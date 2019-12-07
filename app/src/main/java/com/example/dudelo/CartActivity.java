package com.example.dudelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudelo.Model.Cart;
import com.example.dudelo.Prevalent.Prevalent;
import com.example.dudelo.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
    private Button nextbutton;
    private TextView toltalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

         recyclerView = findViewById(R.id.recyCleraCartId);

         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nextbutton = findViewById(R.id.nextButtonId);
        toltalPrice = findViewById(R.id.totalPriceId);



    }


    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(databaseReference.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products"),
                        Cart.class).build();




        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
;

                holder.textProduvtNmae.setText(model.getProductNamee());
                holder.textproductPrice.setText(model.getProductPricee());

                holder.textProductQuantity.setText(model.getQuantity());


            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);

                CartViewHolder holder = new CartViewHolder(view);

                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
      adapter.startListening();


    }
}
