package com.example.myrealstates.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrealstates.R;
import com.example.myrealstates.model.Property;
import com.google.firebase.firestore.FirebaseFirestore;

public class PropertyActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    EditText editTextPropertyName;
    EditText editTextPropertyBalance;
    Button saveProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        firebaseFirestore = FirebaseFirestore.getInstance();

        editTextPropertyName = findViewById(R.id.editTextName);
        editTextPropertyBalance = findViewById(R.id.editTextBalance);
        saveProperty = findViewById(R.id.saveProperty);

        saveProperty.setOnClickListener(v -> {
            String name = editTextPropertyName.getText().toString();
            String balance = editTextPropertyBalance.getText().toString();

            Property property = new Property(name, balance);

            firebaseFirestore.collection("properties").document().set(property).addOnSuccessListener(unused -> {
                Toast.makeText(PropertyActivity.this, "Property saved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PropertiesActivity.class);
                startActivity(intent);
            }).addOnFailureListener(e -> Toast.makeText(PropertyActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}