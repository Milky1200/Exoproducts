package com.mishraaditya.productclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RVProductAdaptor extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    String priceL;

    int ind;
    List<ProductModel> productList;
    List<CartProductModel> cartItems;

    public RVProductAdaptor(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.market_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iTitle.setText(productList.get(position).getTitle());
        holder.iDescription.setText(productList.get(position).getDescription());
        holder.iWarranty.setText(productList.get(position).getWarranty());
        priceL=String.valueOf(productList.get(position).getPrice());
        priceL="$"+priceL;
        holder.iPrice.setText(priceL);
        holder.iBrand.setText(String.valueOf(productList.get(position).getPrice()));
        holder.iCategory.setText(productList.get(position).getCategory());
        Glide.with(context).load(productList.get(position).getThumbnail()).
                placeholder(R.drawable.baseline_360).
                error(R.drawable.ic_launcher_foreground).into(holder.iThumbnail);
        CartProductModel loadCartProduct=new CartProductModel(productList.get(position));
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ButtonPressed", "onClick: Start" );
                Toast.makeText(context,loadCartProduct.getTitle()+" Added!",Toast.LENGTH_SHORT).show();
                Thread inTread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(CartDataBase.getInstance(context).cartDao().isExits(loadCartProduct.getId())){
                                Log.d("Mishraaditya Room", "Found " +loadCartProduct.getId());
                                ind=CartDataBase.getInstance(context).cartDao().getProductById(loadCartProduct.getId()).getQuantity();
                                loadCartProduct.setQuantity(ind+1);
                                CartDataBase.getInstance(context).cartDao().updateProductQuantity(loadCartProduct.getId(),loadCartProduct.getQuantity());

                            }else {
                                loadCartProduct.setQuantity(1);
                                CartDataBase.getInstance(context).cartDao().AddToCart(loadCartProduct);
                                Log.d("Mishraaditya Room", "Not Found " +loadCartProduct.getId());
                            }
                            Log.d("Mishraaditya Room", "run: Prod has been inserted..." +loadCartProduct.getId());
                        }catch (RuntimeException re){
                            Log.d("Mishraaditya Room", "Catch :<<");
                        }
                    }
                });
                inTread.start();
                Thread feThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cartItems=CartDataBase.getInstance(context).cartDao().getAllProductFromCart();
                            for(CartProductModel item:cartItems){
                                Log.d("Mishraaditya Room", "run: Prod has been Loaded..." + item.toString());
                            }
                        }catch (RuntimeException re){
                            Log.d("Mishraaditya Room", "Catch :-->>");
                        }
                    }
                });
                while (true) {
                    if(!inTread.isAlive()){
                        feThread.start();
                        break;
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
