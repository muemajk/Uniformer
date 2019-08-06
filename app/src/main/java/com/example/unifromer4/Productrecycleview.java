package com.example.unifromer4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Productrecycleview  extends RecyclerView.Adapter<Productrecycleview.ViewHolder>{
    private List<Listdata> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private String userid;

    public Productrecycleview(Context context, List<Listdata> list, String userid) {
        this.mContext = context;
        this.mData = list;
        this.userid = userid;
    }
    public void onStart(){



    }

    @NonNull
    @Override
    public Productrecycleview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listdata product = mData.get(position);


        holder.stockvalue.setText(String.format(String.valueOf(product.getstock())));
        holder.pricevalue.setText(String.format(String.valueOf(product.getprice())));
        holder.namevalue.setText(product.getName());
        Picasso.get().load(product.getImgurl()).into(holder.imgtag);






    }



    @Override
    public int getItemCount() {
        return mData.size();
    }







    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button addcartbtn;
        public TextView stocktag,stockvalue,pricetag, pricevalue,nametag,namevalue;
        public ImageView imgtag;
        public View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            addcartbtn = itemView.findViewById(R.id.add_to_cart);
            stocktag = itemView.findViewById(R.id.Stock_tag);
            stockvalue = itemView.findViewById(R.id.stock_value);
            pricetag = itemView.findViewById(R.id.price_tag);
            pricevalue = itemView.findViewById(R.id.price_value);
            namevalue = itemView.findViewById(R.id.prod_name_id);
            imgtag = itemView.findViewById(R.id.grid_img_tag);

            addcartbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);

                    int position = getAdapterPosition();
                    Listdata produc = mData.get(position);
                    String pid = produc.getId();
                    Cart cart = new Cart();
                    cart.addtocart(pid,userid);
                }
            });

            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            Listdata pro = mData.get(getAdapterPosition());
            String pname = pro.getName();
            Toast.makeText(mContext,"the id"+pro.getId().toString(),Toast.LENGTH_LONG).show();

            Intent intent;
            intent = new Intent(mContext, ProductActivity.class);
            intent.putExtra("PRODUCT_ID",pro.getId());
            mContext.startActivity(intent);

        }
    }

    // convenience method for getting data at click position
    Listdata getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


