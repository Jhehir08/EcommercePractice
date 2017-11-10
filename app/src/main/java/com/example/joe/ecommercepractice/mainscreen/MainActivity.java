package com.example.joe.ecommercepractice.mainscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.joe.ecommercepractice.R;
import com.example.joe.ecommercepractice.model.Products;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private List<Products> mProducts = new ArrayList<>();
    private String topNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        switch (topNode) {
            case "jeans":
            case "shoes":
            case "tshirt":
                readFromDatabase();
                default:
                    throw new RuntimeException("Invalid topNode: " + topNode);
        }
    }

    private void initDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference(topNode);
    }

    private void readFromDatabase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    Products value = dataSnapshot.getValue(Products.class);
                    mProducts.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
