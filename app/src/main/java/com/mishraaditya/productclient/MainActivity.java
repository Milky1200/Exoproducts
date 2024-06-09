package com.mishraaditya.productclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton gotoCart;

    Thread feThread;
    public final static int ID_TAG=899;
    ProductResponse productResponse;
    List<ProductModel> productModels;
    ArrayList<CartProductModel> cartModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView=findViewById(R.id.rvProducts);
        gotoCart=findViewById(R.id.gotoCartBtn);
        getProds();
        getCartProds();
        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCartProds();
                while (true) {
                    if(!feThread.isAlive()) {
                        Intent intent = new Intent(MainActivity.this, CartActivity.class);
                        intent.putParcelableArrayListExtra("item_list", cartModels);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    private void getCartProds() {
          feThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("Mishraaditya Room", "run: Inside Main Fetch...");
                            cartModels=(ArrayList<CartProductModel>)CartDataBase.getInstance(MainActivity.this).cartDao().getAllProductFromCart();
                    for(CartProductModel item:cartModels){
                        Log.d("Mishraaditya Room", "run: Prod has been Loaded..." + item.toString());
                    }
                }catch (RuntimeException re){
                    Log.d("Mishraaditya Room", "Catch :-->>");
                }
            }
        });
        feThread.start();
    }

    private void getProds() {
        Call<ProductResponse> call= RetrofitClient.getInstance().getApi().getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                productResponse=response.body();
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Got Products",Toast.LENGTH_SHORT).show();
                    productModels=productResponse.getProducts();
                    setMyAdaptor();
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed In SuccessResponse: "+response.message(),Toast.LENGTH_LONG).show();
                    Log.e("Mishraaditya","OnFailure: "+ response);
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this,"Failed in OnFailure: "+throwable.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("Mishraaditya","OnFailure: "+ throwable.getMessage());
            }
        });
    }
    private void setMyAdaptor(){
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        RVProductAdaptor adaptor=new RVProductAdaptor(MainActivity.this,productModels);
        recyclerView.setAdapter(adaptor);
    }
}
