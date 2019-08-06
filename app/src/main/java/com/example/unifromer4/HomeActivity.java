package com.example.unifromer4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Listdata> list;
    public  RecyclerView recyclerview;
    TextView tryal;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Product");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle =  getIntent().getExtras();
        final String userid = intent.getStringExtra("USER_ID");
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //if (user != null) {
            // User is signed in
          //  userid = user.getUid();
        //} else {
            // No user is signed in
        //}

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(HomeActivity.this, AddNewProductsActivity.class);

                startActivity(intent);
            }
        });

        recyclerview = (RecyclerView) findViewById(R.id.recycler_menu);
        tryal = (TextView)findViewById(R.id.trial);
        tryal.setText("PRODUCTS ON SALE!");



        ValueEventListener prodListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                list = new ArrayList<>();
                // StringBuffer stringbuffer = new StringBuffer();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    product prod = dataSnapshot1.getValue(product.class);
                    Listdata listdata = new Listdata();
                    String id = prod.getId();
                    String name=prod.getName();
                    String description = prod.getDescription();
                    String origin = prod.getOrigin();
                    int stock = prod.getstock();
                    Double price = prod.getprice();
                    String img = prod.getImg_url();



                    listdata.setId(id);
                    listdata.setname(name);
                    listdata.setdescription(description);
                    listdata.setorigin(origin);
                    listdata.setstock(stock);
                    listdata.setimgurl(img);
                    listdata.setprice(price);
                    list.add(listdata);
                    Productrecycleview recycler = new Productrecycleview(HomeActivity.this,list, userid);
                    GridLayoutManager layoutmanager = new GridLayoutManager(getApplicationContext(),3);
                    recyclerview.setLayoutManager(layoutmanager);
                    recyclerview.setItemAnimator( new DefaultItemAnimator());
                    recyclerview.setAdapter(recycler);

                }


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
               // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(prodListener);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent tent = getIntent();
        Bundle bundle =  getIntent().getExtras();
         String userid = tent.getStringExtra("USER_ID");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {

            Intent intent;
            intent = new Intent(HomeActivity.this, CartActivity.class);
            intent.putExtra("USER_ID",userid);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {

            Intent intent;
            intent = new Intent(HomeActivity.this, AddNewProductsActivity.class);
            intent.putExtra("USER_ID",userid);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

            Intent intent;
            intent = new Intent(HomeActivity.this, senderActivity.class);
            intent.putExtra("USER_ID",userid);
            startActivity(intent);
                }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
