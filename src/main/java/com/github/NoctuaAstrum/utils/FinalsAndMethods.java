package com.github.NoctuaAstrum.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FinalsAndMethods {
    static final Gson gson;
    static final AssetHolder importedSystems;
    
    static {
         gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
         importedSystems = new AssetHolder();
     }
    
    public static double roundPoint(double round){
         return round(round,Configs.readingScaleFactor);
    }
    public static double roundPoint(String round){
        return roundPoint(Double.parseDouble(round));
    }

    public static double round(double round){
        return round(round,1);
    }

    public static double round(double round, double factor){
        return round(round,factor,Configs.decimals);
    }

    public static double round(double round, double factor,double decimals){
        double decimal = Math.pow(10.0,decimals);
        return (double) Math.round((round * factor) * decimal) / decimal;
    }
    
}