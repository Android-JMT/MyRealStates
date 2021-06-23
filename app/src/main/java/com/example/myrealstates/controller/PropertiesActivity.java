package com.example.myrealstates.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrealstates.R;
import com.example.myrealstates.model.Property;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class PropertiesActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView propertyList;
    Button addProperties;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        firebaseFirestore = FirebaseFirestore.getInstance();

        propertyList = findViewById(R.id.recyclerViewProperties);
        addProperties = findViewById(R.id.addProperties);

        addProperties.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PropertyActivity.class);
            startActivity(intent);
        });

        Query query = firebaseFirestore.collection("properties");
        FirestoreRecyclerOptions<Property> options = new FirestoreRecyclerOptions.Builder<Property>().setQuery(query, Property.class).build();

        adapter = new FirestoreRecyclerAdapter<Property, PropertiesViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public PropertiesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_property, parent, false);
                return new PropertiesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull PropertiesActivity.PropertiesViewHolder holder, int position, @NonNull @NotNull Property model) {
                holder.name.setText(model.getName());
                holder.balance.setText(model.getBalance());
            }
        };

        propertyList.setHasFixedSize(true);
        propertyList.setLayoutManager(new LinearLayoutManager(this));
        propertyList.setAdapter(adapter);
        adapter.startListening();
     }

    private static class PropertiesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView balance;

        public PropertiesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewPropertyName);
            balance = itemView.findViewById(R.id.propertyBalance);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}