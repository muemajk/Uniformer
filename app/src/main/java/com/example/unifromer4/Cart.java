package com.example.unifromer4;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

class Cart {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public  void addtocart( String pid,  String userid){
        DatabaseReference myPRef = database.getReference("Product");
        final DatabaseReference myRef = database.getReference("Cart/"+userid);

        myPRef.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                @NonNull
                product prod = dataSnapshot.getValue(product.class);
                String card_number = myRef.push().getKey();
                String pid = prod.getId();
               String pname = prod.getName();
               String porg= prod.getOrigin();

               Double price = prod.getprice();

                Listpod pod = new Listpod();
                pod.setProduct_Id(pid);
                pod.setProduct_name(pname);
                pod.setProduct_origin(porg);
                pod.setProduct_price(price);
                pod.setCart_number(card_number);
                pod.setCart_size(1);


                Map<String, Object> postValues = pod.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put( card_number, postValues);



                myRef.updateChildren(childUpdates);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                final String result = "Success";
            }
        });

    }



    public void updatecartsize(String userid,int cart_size,String card_number,String origin,String product_Id,String product_name, Double product_price ){
        final DatabaseReference myRef = database.getReference("Cart/"+userid);

        Listpod prod = new Listpod();
        prod.setCart_size(cart_size);
        prod.setProduct_price(product_price);
        prod.setProduct_origin(origin);
        prod.setProduct_Id(product_Id);
        prod.setCart_number(card_number);
        prod.setProduct_name(product_name);
        Map<String, Object> postValues = prod.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( card_number, postValues);

        myRef.updateChildren(childUpdates);


    }
}
