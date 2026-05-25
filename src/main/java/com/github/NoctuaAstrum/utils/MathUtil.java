package com.github.NoctuaAstrum.utils;

public class MathUtil {

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