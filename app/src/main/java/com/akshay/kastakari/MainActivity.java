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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button login;
    private Button register;
   private FirebaseAuth mauth;
   private ProgressDialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();
        if (mauth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),Home_Activity.class));
        }
         mdialog=new ProgressDialog(this);
        login();
    }
    public void login()
    {
        email=(EditText)findViewById(R.id.emailid);
        pass=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.btnlogin);
        register=(Button)findViewById(R.id.btnregister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registration_Activity.class));

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emaila=email.getText().toString().trim();
                String passa=pass.getText().toString().trim();

                if(TextUtils.isEmpty(emaila))
                {
                    email.setError("email required");
                }
                if(TextUtils.isEmpty(passa))
                {
                    pass.setError("password required");
                }
                mdialog.setMessage("Logging you in");
                mdialog.show();

                mauth.signInWithEmailAndPassword(emaila,passa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Sucessfull ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent (getApplicationContext(),Home_Activity.class));
                            mdialog.dismiss();
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