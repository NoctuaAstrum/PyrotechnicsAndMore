package com.github.NoctuaAstrum.utils.assets.particles;

import java.util.Arrays;

public class ParticleSystem{
    public String id;
    public double lifeSpan;
    public ParticleSpawnerGroup[] spawners;
    public double cullDistance;
    public double boundingRadius;
    public boolean isImportant;



    public ParticleSystem( String id, double lifeSpan, ParticleSpawnerGroup[] spawners, double cullDistance, double boundingRadius, boolean isImportant) {
        this.id = id;
        this.lifeSpan = lifeSpan;
        this.spawners = spawners;
        this.cullDistance = cullDistance;
        this.boundingRadius = boundingRadius;
        this.isImportant = isImportant;
    }


    public String toString() {
        return "ParticleSystem{id='"
                + this.id
                + "', lifeSpan="
                + this.lifeSpan
                + ", spawners="
                + Arrays.toString(this.spawners)
                + ", cullDistance="
                + this.cullDistance
                + ", boundingRadius="
                + this.boundingRadius
                + ", isImportant="
                + this.isImportant
                + "}";
    }
}


