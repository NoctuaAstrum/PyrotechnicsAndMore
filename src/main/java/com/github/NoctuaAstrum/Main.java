package com.github.NoctuaAstrum;


import com.github.NoctuaAstrum.utils.*;
import com.github.NoctuaAstrum.utils.assets.particles.ParticleSystem;
import com.github.NoctuaAstrum.utils.data.*;



public class Main {

    public static void main(String[] args) {

        // /////////////// NOTICE //////////////// //

        // the file where the points are needs to be under files/read/
        // points that should not be written need to contain "temp" in their name; casing is irrelevant
        // the file Hylogo.ggb has some example problems that can occur, you'll get more info in the console

        // /////////////// NOTICE //////////////// //


        //example configs//
        Configs.printReadResult(true);
        Configs.fileType(Configs.SupportedFileType.GGB);
        Configs.setExportName("EXAMPLE");
        Configs.readingScaleFactor(0.1);

        //an example//

        // create a ParticleDataHolder
        ParticleDataHolder example =
                new ParticleDataHolder.Builder()
                        //specify values of the System and SpawnerGroups
                        .attractor(new AttractorData.Builder()
                                //specify the values of the Attractor, currently supports only one
                                .pointShapeExpansion(3)
                                .linearAccelerations(new XYZData(
                                        0,
                                        -4,
                                        0))
                                //build it
                                .buildAndReturn())

                        .centreOffset(new XYZData(
                                7,
                                24,
                                0))
                        .singleSpawn()
                        .startDelay(1)
                        //build it
                        .build("Hylogo","YourParticleSpawner");

        //convert it to a particleSystem
        ParticleSystem exampleSystem = example.convertToParticleSystem();

        //write it into a file (found in files/write/); filename is set in
        AssetWriter.toJsonFile(exampleSystem);
        
    }
}