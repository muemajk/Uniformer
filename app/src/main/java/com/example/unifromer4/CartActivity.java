package com.example.unifromer4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<Listpod> list;
    List<Double> pricelist = new ArrayList<>();;
    public RecyclerView recyclerview;
    public TextView final_price;
    ArrayList<Double> prices_in_cart = new ArrayList<>();
    public Button cartclear,finaliseord;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        Bundle bundle =  getIntent().getExtras();
        final String userid = intent.getStringExtra("USER_ID");
        final DatabaseReference myRef = database.getReference("Cart/"+userid+"/");


        recyclerview = (RecyclerView) findViewById(R.id.cart_recyclerView);
        final_price = (TextView)findViewById(R.id.in_cart_total_price);
        cartclear = (Button)findViewById(R.id.in_cart_clear);
        finaliseord =(Button)findViewById(R.id.order_finalise);
        finaliseord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(CartActivity.this, senderActivity.class);

                startActivity(intent);
            }
        });

        cartclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                      myRef.removeValue();
            }
        });


        ValueEventListener cartListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                list = new ArrayList<>();
                //pricelist = new ArrayList<>();
                pricelist.add(0.0);
                // StringBuffer stringbuffer = new StringBuffer();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    Productpod prod = dataSnapshot1.getValue(Productpod.class);
                    Listpod listdata = new Listpod();
                    String id = prod.getCart_number();
                    String pid = prod.getProduct_Id();
                    String name = prod.getProduct_name();
                    Double price = prod.getProduct_price();
                    String origin = prod.getProduct_origin();
                    int size = prod.getCart_size();
                    if (prod.getProduct_price() == null) {
                        price = 0.0;
                    }
                    listdata.setCart_number(id);
                    listdata.setProduct_name(name);
                    listdata.setProduct_origin(origin);
                    listdata.setProduct_price(price);
                    listdata.setCart_size(size);
                    listdata.setProduct_Id(pid);
                    list.add(listdata);
                    pricelist.add(price*size);
                    Cartrecyclerview recycler = new Cartrecyclerview(CartActivity.this, list,userid);
                    LinearLayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
                    recyclerview.setLayoutManager(layoutmanager);
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(recycler);
                }
                    Double pricess = cartprice(pricelist);
                    final_price.setText(String.format(String.valueOf(Math.round(pricess*100.0)/100.0))+" KSH");
                pricelist.clear();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
         };

        myRef.addValueEventListener(cartListener);



    }

    public Double cartprice(List<Double> prices){
        Double pricing =0.0;
        for (int x = 0; x < prices.size(); x++){
                pricing = pricing + prices.get(x);
                //prices_in_cart.add(pricelist.get(x));

        }
        return pricing;
    }



}
