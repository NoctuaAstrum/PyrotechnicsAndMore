package com.github.NoctuaAstrum.utils;

import com.github.NoctuaAstrum.utils.assets.particles.*;

import java.util.ArrayList;
import java.util.HashMap;

class AssetHolder {
    private HashMap<String,ParticleSystem> importedAssets;
        
    public AssetHolder(){}
        
    public void add(String fileName, ParticleSystem system){
        importedAssets.put(fileName, system);
    }
        
    public ParticleSystem get(String fileName){
        return importedAssets.get(fileName);
    }
    public ParticleSpawnerGroup[] getSpawnerGroup(String fileName){
        return get(fileName).spawners;
    }
}