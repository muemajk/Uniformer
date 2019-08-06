package com.example.unifromer4;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Productpod {
     String product_name;
     String product_Id;
     String product_origin;
     Double product_price;
     String cart_number;
     int cart_size;
    public Productpod(Object cart){
    }
    public Productpod(){}

     public Productpod(String cart_number, int cart_size,String product_name,String product_Id,String product_origin,Double product_price ){
         this.cart_number = cart_number;
         this.cart_size=cart_size;
         this.product_name = product_name;
         this.product_Id = product_Id;
         this.product_origin = product_origin;
         this.product_price = product_price;

     }

     public void setProduct_Id(){
        this.product_Id = product_Id;
     }
     public String getProduct_Id(){
         return product_Id;
     }
    public String getProduct_origin(){
        return product_origin;
    }

    public void setProduct_origin(){
        this.product_origin = product_origin;
    }
    public Double getProduct_price(){
        return product_price;
    }
    public void setProduct_price(){
        this.product_price = product_price;
    }
    public String getProduct_name(){
        return product_name;
    }
    public void setProduct_name(){
        this.product_name = product_name;
    }


    public void setCart_number(){
        this.cart_number = cart_number;
    }
    public String getCart_number(){
        return cart_number;
    }

    public void setCart_size(){
        this.cart_size = cart_size;
    }
    public int getCart_size(){
        return cart_size;
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cart_number", cart_number);
        result.put("cart_size", cart_size);
        result.put("product_name", product_name);
        result.put("product_origin", product_origin);
        result.put("product_price", product_price);
        result.put("product_Id", product_Id);

        return result;
    }


}
