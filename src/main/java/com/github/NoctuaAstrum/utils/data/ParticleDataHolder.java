package com.github.NoctuaAstrum.utils.data;

import com.github.NoctuaAstrum.utils.*;
import com.github.NoctuaAstrum.utils.assets.particles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds all relevant information to create the correct {@link ParticleSystem}
 */
public class ParticleDataHolder {
        public final String fileName;
        public final String particleSpawnerID;
        public final PointData pointData;
        public final XYZData centreOffset;
        public final MinMaxData spawnRate;
        public final int maxConcurrent;
        public final double startDelay;
        public final ArrayList<AttractorData> attractors;
        public final double systemLifeSpan;
        public final double systemCullDistance;
        public final double systemBoundingRadius;
        public final boolean systemIsImportant;
        public final MinMaxData spawnerLifeSpan;
        public final RotationData rotationOffset;
        public final boolean fixedRotation;
        public final MinMaxData waveDelay;
        public final int totalSpawners;
        public final VelocityData initialVelocity;
        public final MinMaxXYZData emitOffset;

    public ParticleDataHolder(Builder dataBuilder){
        this.fileName = dataBuilder.fileName;
        this.particleSpawnerID = dataBuilder.particleSpawnerID;
        this.pointData = dataBuilder.pointData;
        this.attractors = dataBuilder.attractors;
        this.centreOffset = dataBuilder.centreOffset;
        this.spawnRate = dataBuilder.spawnRate;
        this.maxConcurrent = dataBuilder.maxConcurrent;
        this.startDelay = dataBuilder.startDelay;
        this.systemLifeSpan = dataBuilder.systemLifeSpan;
        this.systemCullDistance = dataBuilder.systemCullDistance;
        this.systemBoundingRadius = dataBuilder.systemBoundingRadius;
        this.systemIsImportant = dataBuilder.systemIsImportant;
        this.spawnerLifeSpan = dataBuilder.spawnerLifeSpan;
        this.rotationOffset = dataBuilder.rotationOffset;
        this.fixedRotation = dataBuilder.fixedRotation;
        this.waveDelay = dataBuilder.waveDelay;
        this.totalSpawners = dataBuilder.totalSpawners;
        this.initialVelocity = dataBuilder.initialVelocity;
        this. emitOffset = dataBuilder.emitOffset;
        
    }

    /**
     * @return returns the {@link ParticleDataHolder} as a {@link ParticleSystem}
     */
    public ParticleSystem convertToParticleSystem(){
        return new ParticleSystem(
            fileName,
            systemLifeSpan,
            createSpawnerGroupArray(),
            systemCullDistance,
            systemBoundingRadius,
            systemIsImportant
        );
    }
    private ParticleSpawnerGroup[] createSpawnerGroupArray(){
        List<ParticleSpawnerGroup> psgList = pointData.dataMap().values().stream()
            .map(p-> new ParticleSpawnerGroup(
                    particleSpawnerID,
                    new XYZData(
                            round(p.x()+centreOffset.x()),
                            round(p.y()+centreOffset.y()),
                            round(p.z()+centreOffset.z())),
                    rotationOffset,
                    fixedRotation,
                    spawnRate,
                    spawnerLifeSpan,
                    startDelay,
                    waveDelay,
                    totalSpawners,
                    maxConcurrent,
                    initialVelocity,
                    emitOffset,
                    createParticleAttractorArray(p)
            )).toList();
        if(Configs.Forwarder.hasInjectMode()){
            FinalsAndMethods.importedSystems.stream().forEach(
                    system -> psgList.addAll(Arrays.asList(system.spawners)));
        }
        ParticleSpawnerGroup[] psgArray = new ParticleSpawnerGroup[psgList.size()];
        for(int i = 0; i < psgList.size();i++){
            psgArray[i] = psgList.get(i);
        }
        return psgArray;
    }
    private ParticleAttractor[] createParticleAttractorArray(XYZData p){

        if(!attractors.isEmpty()){
            ParticleAttractor[] paArray = new ParticleAttractor[attractors.size()];
            for (int i = 0; i < paArray.length; i++) {
                //System.out.println(i);
                AttractorData current = attractors.get(i);
                paArray[i] = new ParticleAttractor(
                        validateAttractorPosition(p, i),
                        current.radialAxis,
                        current.trailPositionMultiplier,
                        current.radius,
                        current.radialAcceleration,
                        current.radialTangentAcceleration,
                        current.linearAccelerations,
                        current.radialImpulse,
                        current.radialTangentImpulse,
                        validateAttractorImpulses(p, i),
                        current.dampingMultipliers);
            }
            return paArray;
        }else{
            return null;
        }
    }
    private XYZData validateAttractorPosition(XYZData p, int attractorNumber){
        if(attractors.get(attractorNumber).returnsToSystemCentre){
            return new XYZData(
                    round(-p.x()+centreOffset.x()),
                    round(-p.y()+centreOffset.y()),
                    round(-p.z()+centreOffset.z()));
        }else{
            return null;
        }
    }
    private XYZData validateAttractorImpulses(XYZData p, int attractorNumber){
        AttractorData currentAttractor = attractors.get(attractorNumber);
        if(currentAttractor.expandPointShape){
            return new XYZData(
                    round(p.x()*currentAttractor.pointShapeExpansionFactor),
                    round(p.y()*currentAttractor.pointShapeExpansionFactor),
                    round(p.z()*currentAttractor.pointShapeExpansionFactor));
        }else{
            return null;
        }
    }
    
    private static double round(double rounding){
        return FinalsAndMethods.round(rounding);
    }

    /**
     * Builds the {@link ParticleDataHolder}
     */
    public static class Builder{
        private String fileName;
        private String particleSpawnerID;
        private PointData pointData;
        private XYZData centreOffset = new XYZData(0,0,0);
        private MinMaxData spawnRate;
        private int maxConcurrent;
        private double startDelay;
        private ArrayList<AttractorData> attractors = new ArrayList<>();
        private double systemLifeSpan;
        private double systemCullDistance;
        private double systemBoundingRadius;
        private boolean systemIsImportant;
        private MinMaxData spawnerLifeSpan;
        private RotationData rotationOffset;
        private boolean fixedRotation;
        private MinMaxData waveDelay;
        private int totalSpawners = 1;
        private VelocityData initialVelocity;
        private MinMaxXYZData emitOffset;

        

        public Builder centreOffset(XYZData centreOffset){this.centreOffset=centreOffset;return this;}
        public Builder singleSpawn(){spawnRate = new MinMaxData(1,1);maxConcurrent = 1;return this;}
        public Builder attractor(AttractorData... attractors){this.attractors = new ArrayList<>(Arrays.asList(attractors));return this;}
        public Builder startDelay(double startDelay){this.startDelay = startDelay;return this;}
        public Builder systemLifeSpan(double systemLifeSpan){this.systemLifeSpan = systemLifeSpan;return this;}
        public Builder systemCullDistance(double systemCullDistance){this.systemCullDistance = systemCullDistance;return this;}
        public Builder systemBoundingRadius(double systemBoundingRadius){this.systemBoundingRadius = systemBoundingRadius;return this;}
        public Builder standardImportantSetup(){systemBoundingRadius = 100; systemCullDistance = 100; systemIsImportant = true; return this;}
        public Builder systemIsImportant(){systemIsImportant = true;return this;}
        public Builder spawnerLifeSpan(MinMaxData spawnerLifeSpan){this.spawnerLifeSpan = spawnerLifeSpan;return this;}
        public Builder rotationOffset(RotationData rotationOffset){this.rotationOffset = rotationOffset;return this;}
        public Builder waveDelay(MinMaxData waveDelay){this.waveDelay = waveDelay;return this;}
        public Builder hasFixedRotation(){fixedRotation = true;return this;}
        public Builder totalSpawners(int totalSpawners){this.totalSpawners = totalSpawners;return this;}
        public Builder initialVelocity(VelocityData initialVelocity){this.initialVelocity = initialVelocity;return this;}
        public Builder emitOffset(MinMaxXYZData emitOffset){this.emitOffset = emitOffset;return this;}


        /***
         * Builds the {@link ParticleDataHolder} and inputs always required variables
         * @param filename name of the file that contains the points, filetype is defined in {@link Configs#setFileType(Configs.SupportedFileType)}
         * @param particleSpawnerID the ID of the particleSpawner that is used for the point
         * @return returns a {@link ParticleDataHolder}
         */
        public ParticleDataHolder build(String filename, String particleSpawnerID){
            this.fileName = filename;
            this.particleSpawnerID = particleSpawnerID;
            this.pointData = PointReader.readFile(filename);
            return new ParticleDataHolder(this);
        }
    }
    public enum Preset{
        TEST(new ParticleDataHolder.Builder().build("TestPoints","Placeholder"));
        private final ParticleDataHolder PDH;
        Preset(ParticleDataHolder pdh){
            PDH = pdh;
        }
        public ParticleDataHolder get(){
            return PDH;
        }
    }
}
