 package com.github.NoctuaAstrum.utils;

import com.github.NoctuaAstrum.utils.assets.particles.ParticleSystem;

import java.io.FileReader;
import java.io.IOException;


public class AssetImporter {

    public static void readAssetFile(String filename){
        try {
            readAssetFile0(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readAssetFile0(String fileName) throws IOException {

        ParticleSystem system;
        try (FileReader reader = new FileReader(Configs.importDirectory + fileName + ".particlesystem")) {
            system = FinalsAndMethods.gson.fromJson(reader,ParticleSystem.class);
        }catch (RuntimeException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
        FinalsAndMethods.importedSystems.add(fileName,system);
    }

}
