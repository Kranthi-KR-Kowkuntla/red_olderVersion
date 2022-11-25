package uk.ac.tees.w9577759.red;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPage extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-red-277ab-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        final EditText fullname = findViewById(R.id.name);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button register = findViewById(R.id.registerbutton);
        final Button loginnow = findViewById(R.id.button2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from registration page and store as string

                final String fullnametxt = fullname.getText().toString();
                final String emailtxt = email.getText().toString();
                final String phonetxt = phone.getText().toString();
                final String passwordtxt = password.getText().toString();

                if(fullnametxt.isEmpty() || emailtxt.isEmpty() || phonetxt.isEmpty() || passwordtxt.isEmpty()) {
                    Toast.makeText(RegistrationPage.this,"Please fill the form completely", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phonetxt))
                                Toast.makeText(RegistrationPage.this, "User already existed", Toast.LENGTH_SHORT).show();

                            else {
                                databaseReference.child("users").child(phonetxt).child("fullname").setValue(fullnametxt);
                                databaseReference.child("users").child(phonetxt).child("email").setValue(emailtxt);
                                databaseReference.child("users").child(phonetxt).child("password").setValue(passwordtxt);

                                Toast.makeText(RegistrationPage.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });
        loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

}