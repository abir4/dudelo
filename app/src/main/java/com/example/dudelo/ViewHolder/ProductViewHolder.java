package com.example.dudelo.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dudelo.Interface.ItemClickListner;
import com.example.dudelo.R;
import com.squareup.picasso.Picasso;

import java.text.CollationElementIterator;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{


    public TextView txtProductName,txxProductDescription,txtProductPrice;
    public ImageView productImageRecy;
    private ItemClickListner listner;



    public ProductViewHolder(@NonNull View itemView) {

        super(itemView);



                productImageRecy = (ImageView) itemView.findViewById(R.id.recycleProductImage);
        txtProductName =(TextView) itemView.findViewById(R.id.productRecyName);
        txxProductDescription =(TextView)  itemView.findViewById(R.id.productRecyDescription);
        txtProductPrice =  itemView.findViewById(R.id.productRecyPrice);








    }

    public void setItemClickListner(ItemClickListner listner)

    {


        this.listner = listner;
    }


    @Override
    public void onClick(View v) {


        listner.onClick(v,getAdapterPosition(),false);


    }


}
