package com.github.NoctuaAstrum.utils.assets.particles;


import com.github.NoctuaAstrum.utils.data.XYZData;

public class ParticleAttractor{
   protected XYZData position;
   protected XYZData radialAxis;
   protected float trailPositionMultiplier; //0.0-1.0
   protected float radius;
   protected float radialAcceleration;
   protected float radialTangentAcceleration;
   protected XYZData linearAcceleration;
   protected float radialImpulse;
   protected float radialTangentImpulse;
   protected XYZData linearImpulse;
   protected XYZData dampingMultiplier;

   public ParticleAttractor(
      XYZData position,
      XYZData radialAxis,
      float trailPositionMultiplier,
      float radius,
      float radialAcceleration,
      float radialTangentAcceleration,
      XYZData linearAcceleration,
      float radialImpulse,
      float radialTangentImpulse,
      XYZData linearImpulse,
      XYZData dampingMultiplier
   ) {
      this.position = position;
      this.radialAxis = radialAxis;
      this.trailPositionMultiplier = trailPositionMultiplier;
      this.radius = radius;
      this.radialAcceleration = radialAcceleration;
      this.radialTangentAcceleration = radialTangentAcceleration;
      this.linearAcceleration = linearAcceleration;
      this.radialImpulse = radialImpulse;
      this.radialTangentImpulse = radialTangentImpulse;
      this.linearImpulse = linearImpulse;
      this.dampingMultiplier = dampingMultiplier;
   }


   public String toString() {
      return "ParticleAttractor{position="
         + this.position
         + ", radialAxis="
         + this.radialAxis
         + ", trailPositionMultiplier="
         + this.trailPositionMultiplier
         + ", radius="
         + this.radius
         + ", radialAcceleration="
         + this.radialAcceleration
         + ", radialTangentAcceleration="
         + this.radialTangentAcceleration
         + ", linearAcceleration="
         + this.linearAcceleration
         + ", radialImpulse="
         + this.radialImpulse
         + ", radialTangentImpulse="
         + this.radialTangentImpulse
         + ", linearImpulse="
         + this.linearImpulse
         + ", dampingMultiplier="
         + this.dampingMultiplier
         + "}";
   }
   public enum Preset{
      TEST(new ParticleAttractor(null,new XYZData(0,8,8),0,0,6.8f,0,new XYZData(2,0,2),0,0,null,null));
      private final ParticleAttractor PA;
      Preset(ParticleAttractor pa){
         PA = pa;
      }
      public ParticleAttractor get(){
         return PA;
      }
   }
}
