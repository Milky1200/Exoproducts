package com.mishraaditya.productclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductResponse productResponse;
    List<ProductModel> productModels;
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
        getProds();
    }

    private void getProds() {
        Call<ProductResponse> call=RetrofitClient.getInstance().getApi().getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                productResponse=response.body();
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Got Products",Toast.LENGTH_SHORT).show();
                    productModels=productResponse.getProducts();
                    setMyAdaptor();

                        /*
                            CartProductModel newCartItem=new CartProductModel(productList.get(ind).getId(),
                                    productList.get(ind).getTitle(),
                                    productList.get(ind).getDescription(),
                                    productList.get(ind).getCategory(),
                                    productList.get(ind).getPrice(),
                                    productList.get(ind).getBrand(),
                                    productList.get(ind).getWarranty(),
                                    productList.get(ind).getThumbnail(),
                                    1);



                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CartProductModel newCartItem=new CartProductModel(1,"title","Descriptiono","Catego",
                                    9899.00,"Bran","warr","thumb",1);
                            CartDataBase.getInstance(MainActivity.this).cartDao().AddToCart(newCartItem);


                            Log.d("Mishraaditya", "run: Prod has been inserted...");
                        }
                    }).start();

                         */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CartProductModel newCartItem=CartDataBase.getInstance(MainActivity.this).cartDao().getProductById(1);

                            Log.d("Roomtbs", "run: "+newCartItem);
                            Log.d("Mishraaditya", "run: Prod has been inserted...");
                        }
                    }).start();



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
