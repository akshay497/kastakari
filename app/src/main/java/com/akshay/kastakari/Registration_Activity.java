package com.akshay.kastakari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Registration_Activity extends AppCompatActivity {
    private EditText emailid;
    private EditText password;
    private Button login;
    private Button register;
    private FirebaseAuth mauth;
    private ProgressDialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);
        mdialog=new ProgressDialog(this);
        Register();
    }
    public void Register()
    {
        emailid=(EditText)findViewById(R.id.remailid);
        password=(EditText)findViewById(R.id.rpassword);
        login=(Button)findViewById(R.id.rbtnlogin);
        register=(Button)findViewById(R.id.rbtnregister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailid.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    emailid.setError("email required");
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.setError("password required");
                }
                mdialog.setMessage("Logging you in");
                mdialog.show();
            mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Sucessfull ",Toast.LENGTH_SHORT).show();
                       mdialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),Home_Activity.class));

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login Failed ",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            }
        });


    }
}