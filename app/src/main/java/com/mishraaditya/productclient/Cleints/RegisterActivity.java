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
import com.mishraaditya.productclient.R.layout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView loginLink;
    EditText email, password, username;
    AppCompatButton btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginLink=findViewById(R.id.loginLink);
        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtPassword);
        username=findViewById(R.id.edtName);
        btnRegister=findViewById(R.id.btnRegister);

        loginLink.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnRegister){
            //Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
            registerUser();

        } else if (v.getId()==R.id.loginLink) {
            switchOnLogin();
        }
    }

    private void registerUser() {
        String userName=username.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if(userName.isEmpty()){
            username.requestFocus();
            username.setError("Please Enter Your Username");
            return;
            //If Not Applied return then your validation will not matter and User will be registered.
        }
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

        Call<RegisterResponse> call = UserClient.getInstance().getApi().register(
                userName,userEmail,userPassword
        );
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse=response.body();
                if(response.isSuccessful()){
                    Intent iNext=new Intent(RegisterActivity.this, LoginActivity.class);
                    iNext.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(iNext);
                    finish();
                    Toast.makeText(RegisterActivity.this,registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this,registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void switchOnLogin() {
        Intent i=new Intent(RegisterActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}