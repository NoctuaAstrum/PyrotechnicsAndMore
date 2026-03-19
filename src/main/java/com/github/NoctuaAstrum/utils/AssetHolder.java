package com.github.NoctuaAstrum.utils;

import com.github.NoctuaAstrum.utils.assets.particles.*;

import java.util.HashMap;
import java.util.stream.Stream;

public class AssetHolder {
    private final HashMap<String,ParticleSystem> importedAssets;
        
    public AssetHolder(){
        importedAssets = new HashMap<>();
    }
        
    public void add(String fileName, ParticleSystem system){
        importedAssets.put(fileName, system);
    }
        
    public ParticleSystem get(String fileName){
        return importedAssets.get(fileName);
    }
    public ParticleSpawnerGroup[] getSpawnerGroup(String fileName){
        return get(fileName).spawners;
    }

    public Stream<ParticleSystem> stream(){
        return importedAssets.values().stream();
    }
}