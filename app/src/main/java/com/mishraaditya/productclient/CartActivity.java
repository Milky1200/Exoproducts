package com.mishraaditya.productclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements PaymentResultListener {

    RecyclerView cartRecyclerView;
    List<CartProductModel> cartList = new ArrayList<>();
    int random;
    TextView totalAmount;
    Button btnCheckout;
    ArrayList<CartProductModel> arrayCart;
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
        btnCheckout=findViewById(R.id.button_checkout);
        totalAmount=findViewById(R.id.total_amount);
        arrayCart = getIntent().getParcelableArrayListExtra("item_list");
        Log.e("myCartDB ", "onCreate: Inside Cart Act" );
        for (CartProductModel item:arrayCart){
            Log.d("myCartDB ", "onCreate: "+item.toString());
            cartList.add(item);
        }
        for (CartProductModel item:cartList){
            Log.d("myCartDBList ", "onCreate: "+item.toString());
        }
        //Log.d("myCartDB ", "onCreate: "+cartList.toString());
        setMyAdaptor(cartList);

        /*Thread feThread = new Thread(new Runnable() {
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
        }*/

        Checkout.preload(getApplicationContext());
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount;
                updateTotalAmount();
                amount=String.valueOf(random);
                PayAmountNow(amount);
            }
        });
    }

    private void PayAmountNow(String amount){
        final Activity activity = this;
        Checkout checkout=new Checkout();
        checkout.setImage(R.drawable.base_icon);
        checkout.setKeyID("rzp_test_YJX7t7M3hnkOzB");
        double finalAmount=Float.parseFloat(amount)*100;

        try {
            JSONObject options= new JSONObject();
            options.put("name","Aditya Mishra");
            options.put("description","Reference No: 879621314");
            options.put("currency","INR");
            options.put("amount",finalAmount+"");
            options.put("prefill.email","mishra@xyz.com");
            options.put("prefill.contact","9999999900");
            options.put("image","https://github.com/Milky1200/Exoproducts/blob/master/app/src/main/res/drawable/base_icon.png");
            checkout.open(activity,options);
        } catch (JSONException e) {
           Log.e("CheckOut: ","Error while making payment");
        }
    }

    private void setMyAdaptor(List<CartProductModel> cartList) {
        if(cartList.isEmpty()){
            Toast.makeText(CartActivity.this,"No Items is in DB",Toast.LENGTH_LONG).show();
        }else {
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
            adapter=new ShoppingCartAdapter(cartList,CartActivity.this,totalAmount,cartRecyclerView);
            cartRecyclerView.setAdapter(adapter);
            updateTotalAmount();
        }
    }

    String priceL;
    private void updateTotalAmount() {
        double total = 0;
        for (CartProductModel item : cartList) {
            total += item.getPrice() * item.getQuantity();
        }
        random= (int) total;
        priceL=String.valueOf(random);
        priceL="$"+priceL;
        totalAmount.setText(priceL);
    }

    @Override
    public void onPaymentSuccess(String s) {
        cartList.clear();
        adapter.notifyDataSetChanged();
        try {
            CartDataBase.getInstance(getApplicationContext()).cartDao().deleteAllProduct();
        }catch (DateTimeException e){
            Log.e("DeleteDB", "onPaymentSuccess: " );
        }
        Toast.makeText(CartActivity.this,"Payment Success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(CartActivity.this,"Payment Failed",Toast.LENGTH_LONG).show();
    }
}