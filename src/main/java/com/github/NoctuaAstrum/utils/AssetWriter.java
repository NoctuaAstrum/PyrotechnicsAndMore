package com.github.NoctuaAstrum.utils;

import com.github.NoctuaAstrum.utils.assets.particles.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Writes a {@link ParticleSystem} into a file with correct formatting and deletes default values
 */
public class AssetWriter {

    // /////////////// Util //////////////// //

    /**
     * <li>Removes lines with values of {@code 0.0},{@code false}
     * <li>Removes fatal {@code ,} at the end of lists (occurs through the removal of lines)
     * @param stringList List to clean of unnecessary values
     * @return cleaned List
     */
    private static List<String> cleanDefaultValues(List<String> stringList){
        List<String> cleanList = new ArrayList<>();
        String prior = "";
        //please ignore this mess
        for (String s : stringList){
            if (!(containsDefaultData(s,true)&&s.endsWith(","))) {
                if (!containsDefaultData(s,false)) {
                    prior = s;
                    cleanList.add(s);
                } else{
                    if(prior.endsWith(",")) {
                        cleanList.set(cleanList.size()-1, prior.substring(0,prior.length()-1));
                    }
                }
            }
        }
        return cleanList;
    }
    
    public static boolean containsDefaultData(String check, boolean commaCheck){
        String k = "";
        if(commaCheck){
            k=",";
        }
            
        return
            check.endsWith(": 0.0"+k)||
            check.endsWith(": -0.0"+k)||
            check.endsWith("false"+k)||
            check.endsWith(": 0"+k)||
            check.endsWith("\"TotalSpawners\": 1"+k)||
            check.contains("\"Id\":")
        ;
    }


        



    // /////////////// File //////////////// //
    private static void toJsonFile0(ParticleSystem pS){
        Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        List<String> code = Arrays.stream(gson.toJson(pS).split("\n")).toList();
        Path output;

        if(Configs.getExportName().equals("undefined")){
             output = Path.of("files/write/generated"+".particlesystem");
        }else {
             output = Path.of("files/write/"+Configs.getExportName()+".particlesystem");
        }

        try {
            Files.write(output, cleanDefaultValues(code));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void JsonFileTest(){
        toJsonFile0(new ParticleSystem("jsonfiletest",0f,new ParticleSpawnerGroup[]{ParticleSpawnerGroup.Preset.TEST.get()},100,10,false));
    }

    /**
     *
     * @param particleSystem the particle system that get written into a file
     */
    public static void toJsonFile(ParticleSystem particleSystem){
        toJsonFile0(particleSystem);
    }
    
}
