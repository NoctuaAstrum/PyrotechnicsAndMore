package com.github.NoctuaAstrum.utils.data;


/**
 * Holds all relevant information to create the correct {@link com.github.NoctuaAstrum.utils.assets.particles.ParticleAttractor}
 */
public class AttractorData {
    public final XYZData offsets;
    public final boolean returnsToSystemCentre;
    public final XYZData radialAxis;
    public final double trailPositionMultiplier;
    public final double radius;
    public final double radialAcceleration;
    public final double radialTangentAcceleration;
    public final XYZData linearAccelerations;
    public final double radialImpulse;
    public final double radialTangentImpulse;
    public final XYZData linearImpulses;
    public final XYZData dampingMultipliers;
    public final boolean expandPointShape;
    public final double pointShapeExpansionFactor;

    public AttractorData(Builder attractorDataBuilder){
        this.offsets                    =attractorDataBuilder.offsets;
        this.returnsToSystemCentre      =attractorDataBuilder.returnsToSystemCentre;
        this.radialAxis                 =attractorDataBuilder.radialAxis;
        this.trailPositionMultiplier    =attractorDataBuilder.trailPositionMultiplier;
        this.radius                     =attractorDataBuilder.radius;
        this.radialAcceleration         =attractorDataBuilder.radialAcceleration;
        this.radialTangentAcceleration  =attractorDataBuilder.radialTangentAcceleration;
        this.linearAccelerations        =attractorDataBuilder.linearAccelerations;
        this.radialImpulse              =attractorDataBuilder.radialImpulse;
        this.radialTangentImpulse       =attractorDataBuilder.radialTangentImpulse;
        this.linearImpulses             =attractorDataBuilder.linearImpulses;
        this.dampingMultipliers         =attractorDataBuilder.dampingMultipliers;
        this.expandPointShape           =attractorDataBuilder.expandPointShape;
        this.pointShapeExpansionFactor  =attractorDataBuilder.pointShapeExpansionFactor;
    }


    public static class Builder {
        private XYZData offsets;
        private boolean returnsToSystemCentre;
        private XYZData radialAxis;
        private double trailPositionMultiplier;
        private double radius;
        private double radialAcceleration;
        private double radialTangentAcceleration;
        private XYZData linearAccelerations;
        private double radialImpulse;
        private double radialTangentImpulse;
        private XYZData linearImpulses;
        private XYZData dampingMultipliers;
        private boolean expandPointShape;
        private double pointShapeExpansionFactor;

        public Builder offsets                     (XYZData offsets)                   {this.offsets=offsets; return this;}
        public Builder returnsToSystemCentre       ()                                  {this.returnsToSystemCentre=true; return this;}
        public Builder radialAxis                  (XYZData radialAxis)                {this.radialAxis=radialAxis; return this;}
        public Builder trailPositionMultiplier     (double trailPositionMultiplier)    {this.trailPositionMultiplier=trailPositionMultiplier; return this;}
        public Builder radius                      (double radius)                     {this.radius=radius; return this;}
        public Builder radialAcceleration          (double radialAcceleration)         {this.radialAcceleration=radialAcceleration; return this;}
        public Builder radialTangentAcceleration   (double radialTangentAcceleration)  {this.radialTangentAcceleration=radialTangentAcceleration; return this;}
        public Builder linearAccelerations         (XYZData linearAccelerations)       {this.linearAccelerations=linearAccelerations; return this;}
        public Builder radialImpulse               (double radialImpulse)              {this.radialImpulse=radialImpulse; return this;}
        public Builder radialTangentImpulse        (double radialTangentImpulse)       {this.radialTangentImpulse=radialTangentImpulse; return this;}
        public Builder linearImpulses              (XYZData linearImpulses)            {this.linearImpulses=linearImpulses; return this;}
        public Builder dampingMultipliers          (XYZData dampingMultipliers)        {this.dampingMultipliers=dampingMultipliers; return this;}
        public Builder pointShapeExpansion         (double pointShapeExpansionFactor)        {this.pointShapeExpansionFactor=pointShapeExpansionFactor;expandPointShape=true; return this;}

        public AttractorData buildAndReturn(){
            return new AttractorData(this);
        }

    }
}
