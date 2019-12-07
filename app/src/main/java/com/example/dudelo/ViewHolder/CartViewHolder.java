package com.example.dudelo.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dudelo.Interface.ItemClickListner;
import com.example.dudelo.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textProduvtNmae,textproductPrice,textProductQuantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);


      textProduvtNmae = itemView.findViewById(R.id.cartProductName);
      textproductPrice = itemView.findViewById(R.id.cartProductPrice);
      textProductQuantity = itemView.findViewById(R.id.cartProductQuantiry);




    }

    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;

    }
}
