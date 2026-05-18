 package com.github.NoctuaAstrum.utils.assets;

import com.github.NoctuaAstrum.utils.FileIO;
import com.github.NoctuaAstrum.utils.Json;
import com.github.NoctuaAstrum.utils.assets.particles.ParticleSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

 public class AssetManager{
     public static final Logger LOGGER = Logger.getLogger("AssetManager");
     public static final HashMap<AssetType,HashMap<String, Asset>> importedAssets = new HashMap<>();

     public static HashMap<String,Asset> getParticleSystemMap(){
         return importedAssets.computeIfAbsent(AssetType.PARTICLE_SYSTEM, assetType -> new HashMap<>());
     }

    public static class Importer {

        public static void readAssetFile(String filename, AssetType assetType){
            try {
                readAssetFile0(filename,assetType);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,"Failed to import asset "+filename+" of type "+assetType.NAME);
            }
        }

        private static void readAssetFile0(String fileName, AssetType assetType) throws IOException {
            BufferedReader reader = FileIO.getReaderForAssetFile(fileName, assetType);
            if(reader == null) return;

            Asset asset  = Json.GSON.fromJson(reader,assetType.ASSET_CLASS);

            HashMap<String,Asset> assetMap = importedAssets.get(assetType);
            if(assetMap==null) {
                assetMap = new HashMap<>();
                assetMap.put(fileName,asset);
                importedAssets.put(assetType,assetMap);
            }else{
                assetMap.put(fileName,asset);
            }
        }

    }

    public static class Exporter{

        public static void toJsonFile(Asset asset, AssetType assetType){
            String jsonAsset = Json.GSON.toJson(asset);
            List<String> code = assetType.CLEANUP_FUNCTION.apply(Arrays.stream(jsonAsset.split("\n")).toList());

            FileIO.writeAsset(code,assetType);
        }
        public static void particleSystemToJsonFile(ParticleSystem system){
            toJsonFile(system, AssetType.PARTICLE_SYSTEM);
        }
    }

    static class AssetCleanups{

        /**
         * <li>Removes lines with values of {@code 0.0},{@code false}
         * <li>Removes fatal {@code ,} at the end of lists (occurs through the removal of lines)
         * @param stringList List to clean of unnecessary values
         * @return cleaned List
         */
        static List<String> cleanDefaultValuesParticleSystem(List<String> stringList){
            List<String> cleanList = new ArrayList<>();
            String prior = "";
            //please ignore this mess
            for (String s : stringList){
                if (!(containsDefaultDataParticleSystem(s,true)&&s.endsWith(","))) {
                    if (!containsDefaultDataParticleSystem(s,false)) {
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

        private static boolean containsDefaultDataParticleSystem(String check, boolean commaCheck){
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
    }

}
