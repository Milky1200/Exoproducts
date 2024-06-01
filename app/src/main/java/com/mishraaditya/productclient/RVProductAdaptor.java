package com.mishraaditya.productclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RVProductAdaptor extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<ProductModel> productList;

    public RVProductAdaptor(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text.setText(productList.get(position).getTitle());
        Glide.with(context).load(productList.get(position).getProdImage()).
                placeholder(R.drawable.baseline_360).error(R.drawable.ic_launcher_foreground).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
