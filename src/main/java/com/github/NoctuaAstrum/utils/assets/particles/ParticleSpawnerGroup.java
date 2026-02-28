package com.github.NoctuaAstrum.utils.assets.particles;

import com.github.NoctuaAstrum.utils.data.*;
import java.util.Arrays;

public class ParticleSpawnerGroup{

   protected String spawnerId;
   protected XYZData positionOffset;
   protected RotationData rotationOffset;
   protected boolean fixedRotation;
   protected MinMaxData spawnRate;
   protected MinMaxData lifeSpan;
   protected double startDelay;
   protected MinMaxData waveDelay;
   protected int totalSpawners;
   protected int maxConcurrent;
   protected VelocityData initialVelocity;
   protected MinMaxXYZData emitOffset;
   protected ParticleAttractor[] attractors;

   public ParticleSpawnerGroup(
      String spawnerId,
      XYZData positionOffset,
      RotationData rotationOffset,
      boolean fixedRotation,
      MinMaxData spawnRate,
      MinMaxData lifeSpan,
      double startDelay,
      MinMaxData waveDelay,
      int totalSpawners,
      int maxConcurrent,
      VelocityData initialVelocity,
      MinMaxXYZData emitOffset,
      ParticleAttractor[] attractors
   ) {
      this.spawnerId = spawnerId;
      this.positionOffset = positionOffset;
      this.rotationOffset = rotationOffset;
      this.fixedRotation = fixedRotation;
      this.spawnRate = spawnRate;
      this.startDelay = startDelay;
      this.lifeSpan = lifeSpan;
      this.waveDelay = waveDelay;
      this.totalSpawners = totalSpawners;
      this.maxConcurrent = maxConcurrent;
      this.initialVelocity = initialVelocity;
      this.emitOffset = emitOffset;
      this.attractors = attractors;
   }


   public String toString() {
      return "ParticleSpawnerGroup{spawnerId='"
         + this.spawnerId
         + "', positionOffset="
         + this.positionOffset
         + ", rotationOffset="
         + this.rotationOffset
         + ", fixedRotation="
         + this.fixedRotation
         + ", spawnRate="
         + this.spawnRate
         + ", lifeSpan="
         + this.lifeSpan
         + ", startDelay="
         + this.startDelay
         + ", waveDelay="
         + this.waveDelay
         + ", totalSpawners="
         + this.totalSpawners
         + ", maxConcurrent="
         + this.maxConcurrent
         + ", initialVelocity="
         + this.initialVelocity
         + ", emitOffset="
         + this.emitOffset
         + ", attractors="
         + Arrays.toString(this.attractors)
         + "}";
   }
   public enum Preset{
      TEST(new ParticleSpawnerGroup("Placeholder",new XYZData(2,0,2),null,false,null,null,1,null,1,0,null,null,new ParticleAttractor[]{ParticleAttractor.Preset.TEST.get(), ParticleAttractor.Preset.TEST.get()}));
      private final ParticleSpawnerGroup PSG;
      Preset(ParticleSpawnerGroup psg){
         PSG = psg;
      }
      public ParticleSpawnerGroup get(){
         return PSG;
      }
   }
}
