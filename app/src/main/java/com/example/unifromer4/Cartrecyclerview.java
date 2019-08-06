package com.example.unifromer4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Cartrecyclerview extends RecyclerView.Adapter<Cartrecyclerview.ViewHolder> {
    private List<Listpod> mData;
    private LayoutInflater mInflater;
    private Productrecycleview.ItemClickListener mClickListener;
    private Context mContext;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount();


    private String userid;

    public Cartrecyclerview(Context context, List<Listpod> list, String userid) {
        this.mContext = context;
        this.mData = list;
        this.userid = userid;
    }
    @NonNull
    @Override
    public Cartrecyclerview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.product_prices, parent, false);
        return new Cartrecyclerview.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cartrecyclerview.ViewHolder holder, int position) {
        Listpod product = mData.get(position);

        holder.origintag.setText(String.format(String.valueOf(product.getProduct_origin())));
        holder.pricevalue.setText(String.format(String.valueOf(product.getProduct_price())));
        holder.namevalue.setText(String.format(String.valueOf(product.getProduct_name())));
        holder.cartsize.setText(String.format(String.valueOf(product.getCart_size())));




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button removebtn,cartreduce;
        public TextView origintag, pricevalue,namevalue,cartsize;


        public View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            origintag = itemView.findViewById(R.id.product_in_cart_origin);
            pricevalue = itemView.findViewById(R.id.product_in_cart_price);
            namevalue = itemView.findViewById(R.id.product_in_cart_name);
            removebtn = itemView.findViewById(R.id.product_in_cart_action);
            cartsize = itemView.findViewById(R.id.product_incart_numbers);
            cartreduce = itemView.findViewById(R.id.product_in_cart_reduce);





            cartreduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int postion =getAdapterPosition();
                    Listpod pro = mData.get(postion);
                    String cartnumber=pro.getCart_number();
                    int currentsize = pro.getCart_size();
                    int newsize = currentsize-1;
                    if (newsize < 0){
                        newsize = 0;
                    }
                    String pid = pro.getProduct_Id();
                    String origin = pro.getProduct_origin();
                    String pname = pro.getProduct_name();
                    Double pprice = pro.getProduct_price();

                    pro.setCart_size(newsize);


                    Cart cart = new Cart();
                    cart.updatecartsize(userid,newsize,cartnumber,origin,pid,pname,pprice);
                    Toast.makeText(mContext,"Update size for product:"+ pro.getProduct_name(),Toast.LENGTH_LONG).show();
                    cartsize.setText(String.format(String.valueOf(newsize)));
                    newsize = 0;

                }
            });


            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int postion =getAdapterPosition();
                    Listpod pro = mData.get(postion);
                    String cartnumber=pro.getCart_number();
                    int currentsize = pro.getCart_size();
                    String pid = pro.getProduct_Id();
                    String origin = pro.getProduct_origin();
                    String pname = pro.getProduct_name();
                    Double pprice = pro.getProduct_price();
                    int newsize = currentsize+1;
                    pro.setCart_size(newsize);


                    Cart cart = new Cart();
                    cart.updatecartsize(userid,newsize,cartnumber,origin,pid,pname,pprice);
                    Toast.makeText(mContext,"Update size for product:"+ pro.getProduct_name(),Toast.LENGTH_LONG).show();
                    cartsize.setText(String.format(String.valueOf(newsize)));
                    newsize=0;


//                    pro.setCart_size(newsize);
//
//                    Map<String, Object> postValues = pro.toMap();
//
//                    Map<String, Object> childUpdates = new HashMap<>();
//                    childUpdates.put( cartnumber, postValues);
//
//
//                    myRef.updateChildren(childUpdates);
                }
            });
            removebtn.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            final int postion =getAdapterPosition();
            Listpod pro = mData.get(postion);
            String pid = pro.getCart_number();
            DatabaseReference myRef = database.getReference("Cart/"+userid);
            myRef.child(pid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(mContext,"Product has been removed from cart",Toast.LENGTH_LONG).show();

                    mData.remove(postion);
                    notifyItemRemoved(postion);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext,"Failed to remove product from cart",Toast.LENGTH_LONG).show();

                        }
                    });

        }
    }

    }
