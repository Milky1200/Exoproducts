package com.mishraaditya.productclient.Cleints;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.mishraaditya.productclient.MainActivity;
import com.mishraaditya.productclient.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton btnLogin;
    TextView registerLink;
    EditText email,password;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtPassword);
        registerLink=findViewById(R.id.registerLink);
        btnLogin=findViewById(R.id.btnLogin);

        registerLink.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());



    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnLogin){
            //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            userLogin();
        } else if (v.getId()==R.id.registerLink) {
            switchOnRegister();
        }

    }

    private void userLogin() {
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if(userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please Enter Your email");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Please Enter Valid Email");
            return;
        }
        if(userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please Enter Your Password");
            return;
        }
        if(userPassword.length()<8){
            password.requestFocus();
            password.setError("Password Should Have 8digits");
            return;
        }

        Call<LoginResponse> call=UserClient.getInstance().getApi().login(userEmail,userPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                if(response.isSuccessful()){
                    if(loginResponse.getError().equals("000")){
                        sharedPrefManager.saveUser(loginResponse.getUser());
                        Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent iNext=new Intent(LoginActivity.this, MainActivity.class);
                        iNext.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(iNext);
                    }else {
                        Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this,"Failure Occurred",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void switchOnRegister() {
        Intent i=new Intent(LoginActivity.this, RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    protected void onStart(){
        super.onStart();
        if(sharedPrefManager.isLogged()){
            Intent iNext=new Intent(LoginActivity.this, MainActivity.class);
            iNext.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(iNext);
        }
    }
}