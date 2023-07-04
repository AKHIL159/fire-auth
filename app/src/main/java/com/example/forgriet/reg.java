package com.example.forgriet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import etc.user;

public class reg extends AppCompatActivity {

    EditText userreg,passwordreg;
    TextView t;
    Button registerbtn;

    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        userreg = findViewById(R.id.usernamereg);
        passwordreg = findViewById(R.id.passreg);
        registerbtn = findViewById(R.id.registerbtn);
        t = findViewById(R.id.textView2);

        auth = FirebaseAuth.getInstance();


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userreg.getText().toString();
                String password = passwordreg.getText().toString();

                // Create new user account
                auth.createUserWithEmailAndPassword(email+"@email.com", password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User is registered
                                    // Get the user ID of the newly registered user
                                    String userId = auth.getCurrentUser().getUid();

// Create a new User object with the username and email address
                                    String username = userreg.getText().toString();
                                    String passwordinclass = passwordreg.getText().toString();
                                    user u = new user(username, passwordinclass);

// Save the User object to the database
                                    databaseReference.child("users").child(userId).setValue(u);

                                    Intent intent = new Intent(reg.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Registration failed
                                    //Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    t.setText(task.getException().getMessage());
                                }
                            }
                        });
            }
        });
    }

}
