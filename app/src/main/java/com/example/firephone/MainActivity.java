package com.example.firephone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Button btn,btm;
    EditText txt1;
    TextView tx;
    DatabaseReference rootRef,demoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1=findViewById(R.id.editText);
        tx=findViewById(R.id.textView);
        btn=findViewById(R.id.button);
        btm=findViewById(R.id.button2);
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("demo");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(txt1.getText().toString().isEmpty())) {

                    Toast.makeText(getApplicationContext(), "Proceed", Toast.LENGTH_LONG).show();
                    String value = txt1.getText().toString();
                    demoRef.setValue(value);

                    demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value1 = dataSnapshot.getValue(String.class);
                            tx.setText(value1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                else
                {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("EMPTY VALUE")
                            .show();

                }
            }
        });
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+txt1.getText().toString()));
                Toast.makeText(MainActivity.this,"calling"+txt1.getText().toString(),Toast.LENGTH_LONG).show();
                startActivity(i);
            }

        });
    }
}

