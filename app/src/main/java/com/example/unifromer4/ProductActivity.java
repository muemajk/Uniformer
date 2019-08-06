package com.example.unifromer4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    Button addtocart;
    TextView pname, pdescription,pstock,pprice,porigin;
    ImageView pimg;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        pname = (TextView)findViewById(R.id.info_product_name);
        pdescription = (TextView)findViewById(R.id.info_product_description);
        pstock = (TextView)findViewById(R.id.info_product_stock);
        pprice = (TextView)findViewById(R.id.info_product_price);
        porigin = (TextView)findViewById(R.id.info_product_origin);
        addtocart = (Button)findViewById(R.id.info_product_to_cart);
        pimg = (ImageView)findViewById(R.id.info_prod_img);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent tent = getIntent();
        Bundle bundle =  getIntent().getExtras();
        final String userid = tent.getStringExtra("USER_ID");
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        final String prodId = getIntent().getStringExtra("PRODUCT_ID");


        DatabaseReference myRef = database.getReference("Product/"+prodId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // StringBuffer stringbuffer = new StringBuffer()

                    product prod = dataSnapshot.getValue(product.class);


                    pname.setText(prod.getName().toUpperCase());
                    pdescription.setText(prod.getDescription());
                    pstock.setText("IN STOCK: "+String.format(String.valueOf(prod.getstock())));
                    pprice.setText("PRICE: "+prod.getprice().toString());
                    porigin.setText(prod.getOrigin());
                    Picasso.get().load(prod.getImg_url()).resize(407,323).into(pimg);


                    // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                addtocart.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Cart cart = new Cart();
                        cart.addtocart(prodId, userid);
                        Intent intent;
                        intent = new Intent(ProductActivity.this, CartActivity.class);
                        intent.putExtra("PRODUCT_ID",prodId);
                        startActivity(intent);
                        }
                });


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //  Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }
}
