package com.github.NoctuaAstrum.utils.assets;

import com.github.NoctuaAstrum.utils.assets.particles.ParticleSystem;


import java.util.List;
import java.util.function.Function;

public class AssetType {

    /**
     * The Asset is a Hytale ParticleSystem file
     */
    public static final AssetType PARTICLE_SYSTEM = new AssetType(
            "ParticleSystem",
            ".particlesystem",
            ParticleSystem.class,
            AssetManager.AssetCleanups::cleanDefaultValuesParticleSystem);

    public final String NAME;
    public final String FILE_ENDING;
    public final Class<? extends Asset> ASSET_CLASS;
    public final Function<List<String>,List<String>> CLEANUP_FUNCTION;

    private AssetType(String name, String fileEnding, Class<? extends Asset> assetClass, Function<List<String>,List<String>> cleanupFunction){
        this.NAME = name;
        this.FILE_ENDING = fileEnding;
        this.ASSET_CLASS = assetClass;
        this.CLEANUP_FUNCTION = cleanupFunction;
    }

}
