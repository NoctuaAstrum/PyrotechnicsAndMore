package com.github.NoctuaAstrum.utils.data;

import com.github.NoctuaAstrum.utils.Configs;
import com.github.NoctuaAstrum.utils.PointReader;
import com.github.NoctuaAstrum.utils.assets.particles.*;
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
        public final AttractorData attractor;
        public final double systemLifeSpan;
        private final double systemCullDistance;
        private final double systemBoundingRadius;
        private final boolean systemIsImportant;

    public ParticleDataHolder(Builder dataBuilder){
        this.fileName = dataBuilder.fileName;
        this.particleSpawnerID = dataBuilder.particleSpawnerID;
        this.pointData = dataBuilder.pointData;
        this.attractor = dataBuilder.attractor;
        this.centreOffset = dataBuilder.centreOffset;
        this.spawnRate = dataBuilder.spawnRate;
        this.maxConcurrent = dataBuilder.maxConcurrent;
        this.startDelay = dataBuilder.startDelay;
        this.systemLifeSpan = dataBuilder.systemLifeSpan;
        this.systemCullDistance = dataBuilder.systemCullDistance;
        this.systemBoundingRadius = dataBuilder.systemBoundingRadius;
        this.systemIsImportant = dataBuilder.systemIsImportant;

        
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
                    null,
                    false,
                    spawnRate,
                    null,
                    startDelay,
                    null,
                    1,
                    maxConcurrent,
                    null,
                    null,
                    createParticleAttractorArray(p)
            )).toList();
        ParticleSpawnerGroup[] psgArray = new ParticleSpawnerGroup[psgList.size()];
        for(int i = 0; i < psgList.size();i++){
            psgArray[i] = psgList.get(i);
        }
        return psgArray;
    }
    private ParticleAttractor[] createParticleAttractorArray(XYZData p){
        //Currently only support for one
        ParticleAttractor[] paArray = new ParticleAttractor[1];

        paArray[0] = new ParticleAttractor(
                validateAttractorPosition(p),
                null,
                0,
                0,
                0,
                0,
                attractor.linearAccelerations,
                0,
                0,
                validateAttractorImpulses(p),
                null);

        return paArray;
    }
    private XYZData validateAttractorPosition(XYZData p){
        if(attractor.returnsToSystemCentre){
            return new XYZData(
                    round(-p.x()+centreOffset.x()),
                    round(-p.y()+centreOffset.y()),
                    round(-p.z()+centreOffset.z()));
        }else{
            return null;
        }
    }
    private XYZData validateAttractorImpulses(XYZData p){
        if(attractor.expandPointShape){
            return new XYZData(
                    round(p.x()*attractor.pointShapeExpansionFactor),
                    round(p.y()*attractor.pointShapeExpansionFactor),
                    round(p.z()*attractor.pointShapeExpansionFactor));
        }else{
            return null;
        }
    }
    
    private static double round(double rounding){
        return (double) Math.round(rounding * 100) / 100;
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
        private AttractorData attractor;
        private double systemLifeSpan;
        private double systemCullDistance;
        private double systemBoundingRadius;
        private boolean systemIsImportant;


        /**
         * Needs to be done to work correctly
         * @param filename name of the file that contains the points, filetype is defined in {@link Configs#fileType(Configs.SupportedFileType)}
         * @param particleSpawnerID the ID of the particleSpawner that is used for the point
         */
        public Builder aaa_required(String filename, String particleSpawnerID){
            this.fileName = filename;
            this.particleSpawnerID = particleSpawnerID;
            switch (Configs.getFileType()){
                case GGB -> this.pointData = PointReader.readFileGGB(filename);
                case XML -> this.pointData = PointReader.readFileXML(filename);
                default -> {
                    System.out.println("Error! FileType is neither .ggb nor .xml;");System.exit(-1);
                }
            }
            return this;
        }
        

        public Builder centreOffset(XYZData centreOffset){this.centreOffset=centreOffset;return this;}
        public Builder singleSpawn(){spawnRate = new MinMaxData(1,1);maxConcurrent = 1;return this;}
        public Builder attractor(AttractorData attractor){this.attractor = attractor;return this;}
        public Builder startDelay(double startDelay){this.startDelay = startDelay;return this;}
        public Builder systemLifeSpan(double systemLifeSpan){this.systemLifeSpan = systemLifeSpan;return this;}
        public Builder systemCullDistance(double systemCullDistance){this.systemCullDistance = systemCullDistance;return this;}
        public Builder systemBoundingRadius(double systemBoundingRadius){this.systemBoundingRadius = systemBoundingRadius;return this;}
        public Builder standardImportantSetup(){systemBoundingRadius = 100; systemCullDistance = 100; systemIsImportant = true; return this;}
        public Builder systemIsImportant(){systemIsImportant = true;return this;}







        public ParticleDataHolder buildAndReturn(){
            return new ParticleDataHolder(this);
        }
    }
    public enum Preset{
        TEST(new ParticleDataHolder.Builder().aaa_required("TestPoints","Placeholder").buildAndReturn());
        private final ParticleDataHolder PDH;
        Preset(ParticleDataHolder pdh){
            PDH = pdh;
        }
        public ParticleDataHolder get(){
            return PDH;
        }
    }
}
