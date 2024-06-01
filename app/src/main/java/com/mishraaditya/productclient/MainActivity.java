package com.mishraaditya.productclient;

import android.os.Bundle;
import android.util.Log;
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
        Call<List<ProductModel>> call=RetrofitClient.getInstance().getApi().getProducts();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                productModels=response.body();
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Got Products",Toast.LENGTH_SHORT).show();
                    setMyAdaptor();
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed In SuccessResponse",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable throwable) {
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
