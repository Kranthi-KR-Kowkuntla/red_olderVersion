package uk.ac.tees.w9577759.red;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView et;
//    EditText Phone,Password;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-red-277ab-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         et=findViewById(R.id.registerPage);
        Button loginbutton = findViewById(R.id.loginbutton);

        EditText phone = findViewById(R.id.PhoneNo);
        EditText password = findViewById(R.id.Password1);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phonetxt = phone.getText().toString();
                final String passwordtxt = password.getText().toString();

                if(phonetxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phonetxt)){

                                final String getPassword = snapshot.child(phonetxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordtxt))
                                {
                                        Toast.makeText(MainActivity.this,"Successfully Loged in",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, page1.class));
                                        finish();
                                }

                                else {
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();

                                }
                            }

                            else{
                                Toast.makeText(MainActivity.this, "user not recognized", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationPage.class));

            }
        });


    }
}