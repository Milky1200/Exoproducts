package com.mishraaditya.productclient;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView text;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        image=itemView.findViewById(R.id.ivImage);
        text=itemView.findViewById(R.id.tvTitle);
    }
}
