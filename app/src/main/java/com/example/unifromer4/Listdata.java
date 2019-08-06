package com.example.unifromer4;

public class Listdata {
    public String Product_Name;
    public String Product_description;
    public Double Product_Price;
    public String Image_url;
    public String Place_origin;
    public int Product_Stock;
    public String Product_ID;


    public String getId(){
        return Product_ID;
    }
    public void setId(String Product_ID){
        this.Product_ID = Product_ID;
    }
    public String getName(){
        return Product_Name;
    }
    public void setname(String Product_Name){
        this.Product_Name = Product_Name;
    }
    public String getDescription(){
        return Product_description;

    }
    public void setdescription(String Product_description){
        this.Product_description= Product_description;
    }
    public String getImgurl(){
        return Image_url;
    }

    public void setimgurl(String Image_url){
        this.Image_url = Image_url;
    }


    public String getOrigin(){
        return Place_origin;
    }

    public void setorigin(String Place_origin){
        this.Place_origin = Place_origin;
    }


    public int getstock(){
        return Product_Stock;
    }


    public void setstock(int Product_Stock){
        this.Product_Stock = Product_Stock;
    }


    public Double getprice(){
        return Product_Price;
    }
    public void setprice(Double Product_Price){
        this.Product_Price = Product_Price;
    }


}
