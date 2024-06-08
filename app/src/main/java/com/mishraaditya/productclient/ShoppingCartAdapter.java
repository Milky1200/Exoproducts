package com.mishraaditya.productclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ItemViewHolder> {

    private List<CartProductModel> itemList = new ArrayList<>();
    private Context context;
    String priceL;
    TextView totalPrice;


    public ShoppingCartAdapter(List<CartProductModel> itemList, Context context, TextView totalPrice) {
        this.itemList = itemList;
        this.context = context;
        this.totalPrice=totalPrice;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CartProductModel item = itemList.get(position);
        // Bind data to the views
        holder.title.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        priceL=String.valueOf(itemList.get(position).getPrice());
        priceL="$"+priceL;
        holder.price.setText(priceL);
        //holder.price.setText(String.format("$%d", item.getPrice()));
        holder.brand.setText(item.getBrand());
        holder.warranty.setText(item.getWarranty());
        holder.description.setText(item.getDescription());

        // Load image using a library like Glide or Picasso
        Glide.with(context).load(item.getThumbnail()).into(holder.thumbnail);

        // Handle quantity management
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        holder.buttonAdd.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            item.setQuantity(quantity + 1);
            holder.quantity.setText(String.valueOf(item.getQuantity()));
            notifyDataSetChanged();
        });

        holder.buttonSubtract.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            if (quantity > 1) {
                item.setQuantity(quantity - 1);
                holder.quantity.setText(String.valueOf(item.getQuantity()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, category, price, brand, warranty, description, quantity;
        ImageView thumbnail;
        Button buttonAdd, buttonSubtract;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            category = itemView.findViewById(R.id.item_category);
            price = itemView.findViewById(R.id.item_price);
            brand = itemView.findViewById(R.id.item_brand);
            warranty = itemView.findViewById(R.id.item_warranty);
            description = itemView.findViewById(R.id.item_description);
            quantity = itemView.findViewById(R.id.item_quantity);
            thumbnail = itemView.findViewById(R.id.item_thumbnail);
            buttonAdd = itemView.findViewById(R.id.button_add);
            buttonSubtract = itemView.findViewById(R.id.button_subtract);
        }
    }

}

