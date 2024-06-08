package com.mishraaditya.productclient;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mishraaditya.productclient.Dashboars.MainActivity;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;
    List<CartProductModel> cartList;

    TextView totalAmount;
    ShoppingCartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cartRecyclerView=findViewById(R.id.cartRecycler);
        totalAmount=findViewById(R.id.total_amount);

        Thread feThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cartList=CartDataBase.getInstance(CartActivity.this).cartDao().getAllProductFromCart();
                    for(CartProductModel item:cartList){
                        Log.d("Mishraaditya Room", "run: Prod has been Loaded..." + item.toString());
                    }
                }catch (RuntimeException re){
                    Log.d("Mishraaditya Room", "Catch :-->>");
                }
            }
        });
        feThread.start();
        while (true){
            if(!feThread.isAlive()){
                if(cartList.isEmpty()){
                    Toast.makeText(CartActivity.this,"No Item is in DB",Toast.LENGTH_LONG).show();
                    break;
                }else {
                    cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                    ShoppingCartAdapter adaptor=new ShoppingCartAdapter(cartList,CartActivity.this,totalAmount);
                    cartRecyclerView.setAdapter(adaptor);
                    updateTotalAmount();
                }
            }
        }
    }
    private void updateTotalAmount() {
        int total = 0;
        for (CartProductModel item : cartList) {
            total += item.getPrice() * item.getQuantity();
        }
        totalAmount.setText(String.format("Total: $%d", total));
    }
}