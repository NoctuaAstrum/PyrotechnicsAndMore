package com.github.NoctuaAstrum.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FinalsAndMethods {
    static final Gson gson;
    
    static {
         gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
     }
    
    public static double roundPoint(double round){
         return round(round,Configs.getReadingScaleFactor());
    }
    public static double roundPoint(String round){
        return roundPoint(Double.parseDouble(round));
    }
    public static double round(double round, double factor){
        return (double) Math.round((round * factor) * 100.0) / 100.0;
    }
     public static double round(double round){
          return round(round,1);
     }
    
}