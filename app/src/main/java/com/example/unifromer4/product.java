package com.example.unifromer4;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class product {
    public String Product_Name;
    public String Product_description;
    public Double Product_Price;
    public String Image_url;
    public String Place_origin;
    public int Product_Stock;
    public String Product_ID;

    public product(){}

    public product(String Product_ID,String Product_Name,String Product_description, Double Product_Price, String Image_url, String Place_origin, int Product_Stock ){
        this.Product_Name =  Product_Name;
        this.Product_description = Product_description;
        this.Image_url = Image_url;
        this.Product_Price = Product_Price;
        this.Product_Stock = Product_Stock;
        this.Place_origin= Place_origin;
        this.Product_ID = Product_ID;

    }
    @NonNull
    public String getId(){
        return Product_ID;
    }
    @NonNull
    public void setId(){
        this.Product_ID = Product_ID;
    }
    @NonNull
    public String getName(){
        return Product_Name;
    }
    @NonNull
    public void setname(){
        this.Product_Name = Product_Name;
    }
    @NonNull
    public String getDescription(){
        return Product_description;

    }
    @NonNull
    public void setdescription(){
        this.Product_description= Product_description;
    }
    @NonNull
    public String getImg_url(){
        return Image_url;
    }
    @NonNull
    public void setimg_url(){
        this.Image_url = Image_url;
    }

    @NonNull
    public String getOrigin(){
        return Place_origin;
    }
    @NonNull
    public void setorigin(){
        this.Place_origin = Place_origin;
    }

    @NonNull
    public int getstock(){
        return Product_Stock;
    }

    @NonNull
    public void setstock(){
        this.Product_Stock = Product_Stock;
    }

    @NonNull
    public Double getprice(){
        return Product_Price;
    }
    @NonNull
    public void setprice(){
        this.Product_Price = Product_Price;
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Product_ID", Product_ID);
        result.put("Product_Name", Product_Name);
        result.put("Product_description", Product_description);
        result.put("Image_url", Image_url);
        result.put("Place_origin", Place_origin);
        result.put("Product_Stock", Product_Stock);
        result.put("Product_Price", Product_Price);

        return result;
    }
}
