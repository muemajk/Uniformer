package com.example.unifromer4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class AddNewProductsActivity extends AppCompatActivity {
    private static final String TAG = "AddNewProductsActivity";
    private Button addproduct,addpimg;
    private EditText productname,productdes,productprice,productstock,productorigin;
    private AppCompatImageView img;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Product");
    private DatabaseReference mDatabase = database.getReference();
    private Uri filePath;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    public boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_products);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        productname = (EditText) findViewById(R.id.product_name);
        productdes = (EditText) findViewById(R.id.product_description);
        productprice = (EditText) findViewById(R.id.product_price);
        addproduct = (Button)findViewById(R.id.add_new_product);
        productstock = (EditText)findViewById(R.id.product_stock);
        productorigin = (EditText)findViewById(R.id.product_origin);
        img = (AppCompatImageView)findViewById(R.id.select_product_image);
        addpimg = (Button)findViewById(R.id.add_new_img);

        addproduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                uploadimg();
            }
        });

        addpimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                chooseimg();
            }
        });





    }


    public void chooseimg(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
        //this method captures the picke image and displays it on the image view!
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void uploadimg(){

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final  StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String userId = myRef.push().getKey();
                            try {
                                uploadproduct(ref.toString());
                            }catch (NullPointerException n){
                                productname.setText(n.getMessage());
                                Toast.makeText(AddNewProductsActivity.this, "Failed "+n.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddNewProductsActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    public Boolean uploadproduct(String picurl){
        Double price = Double.parseDouble(productprice.getText().toString());
        int stock = Integer.parseInt(productstock.getText().toString());
        String productId = myRef.push().getKey();
        product prd = new product(productId,productname.getText().toString(),productdes.getText().toString(),price,picurl,productorigin.getText().toString(),stock);
        //myRef.child(mGroupId+"/").setValue(prd);
        Map<String, Object> productval = prd.toMap();



        myRef.child(productId).setValue(productval);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI


                Intent intent;
                intent = new Intent(AddNewProductsActivity.this, HomeActivity.class);
                startActivity(intent);

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                result = false;
                Log.w(TAG, "Failed to read value.", databaseError.toException());

                // ...
            }
        });


        return result;
    }
    }

