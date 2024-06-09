package com.mishraaditya.productclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    RecyclerView recyclerView;
    int random;


    public ShoppingCartAdapter(List<CartProductModel> itemList, Context context, TextView totalPrice,RecyclerView recyclerView) {
        this.itemList = itemList;
        this.context = context;
        this.totalPrice=totalPrice;
        this.recyclerView=recyclerView;
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
            //itemList.remove(item);
            item.setQuantity(quantity + 1);
            holder.quantity.setText(String.valueOf(item.getQuantity()));
            itemList.get(position).setQuantity(item.getQuantity());
            //itemList.add(item);
            notifyDataSetChanged();
            recyclerView.scrollToPosition(position);
            updateTotalAmount();

            Thread inTread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CartDataBase.getInstance(context).cartDao().updateProductQuantity(item.getId(), item.getQuantity());
                        //Log.d("Mishraaditya Room", "run: Prod has been inserted..." +loadCartProduct.getId());
                    }catch (RuntimeException re){
                        Log.d("Mishraaditya Room", "Catch :<<");
                    }
                }
            });
            inTread.start();
        });

        holder.buttonSubtract.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            if (quantity > 1) {
                //itemList.remove(item);
                item.setQuantity(quantity - 1);
                holder.quantity.setText(String.valueOf(item.getQuantity()));
                itemList.get(position).setQuantity(item.getQuantity());
                //itemList.add(item);
                notifyDataSetChanged();
                recyclerView.scrollToPosition(position);
                updateTotalAmount();

                Thread inTread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(CartDataBase.getInstance(context).cartDao().isExits(item.getId())){
                                if(item.getQuantity()<=0){
                                    CartDataBase.getInstance(context).cartDao().deleteProductFromCart(item.getId());
                                }else {
                                    CartDataBase.getInstance(context).cartDao().updateProductQuantity(item.getId(), item.getQuantity());
                                }
                            }
                            //Log.d("Mishraaditya Room", "run: Prod has been inserted..." +loadCartProduct.getId());
                        }catch (RuntimeException re){
                            Log.d("Mishraaditya Room", "Catch :<<");
                        }
                    }
                });
                inTread.start();
            }else{
                //item.setQuantity(quantity - 1);
                itemList.remove(item);
                Toast.makeText(context,"Item Deleted: "+item.getTitle().toString(),Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
                updateTotalAmount();
                Thread inTread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(CartDataBase.getInstance(context).cartDao().isExits(item.getId())){
                                CartDataBase.getInstance(context).cartDao().deleteProductFromCart(item.getId());
                                Log.e("Mishraaditya Room", "run: Prod has been Deleted..." +item.getId());
                            }
                            //Log.d("Mishraaditya Room", "run: Prod has been inserted..." +loadCartProduct.getId());
                        }catch (RuntimeException re){
                            Log.d("Mishraaditya Room", "Catch :<<");
                        }
                    }
                });
                inTread.start();
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

    private void updateTotalAmount() {
        double total = 0;
        for (CartProductModel item : itemList) {
            total += item.getPrice() * item.getQuantity();
        }
        random=(int)total;
        priceL=String.valueOf(random);
        priceL="$"+priceL;
        totalPrice.setText(priceL);
    }

}

