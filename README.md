### About
This project aims to help Hytale modders with creating shapes using particleSpawners as Dots (you'll need to have a custom one currently, I'll add some templates later). It is currently in development, but most of the basic features are implemented.

#### Attributions
[Gson](https://github.com/google/gson) is used to create the json syntax

### How to use
###### Prerequisites
Currently you need to have a Geogebra file that contains points in the graphing mode. At the moment only 2d (x and y axis) is supported. Any points that shouldn't be written need to contain "temp" in their name. The position and case is irrelevant, just the order of letters needs to be present. You also need to know how to write basic java (Later the project will come as an executable .jar (always be aware of potential viruses) with a GUI)

###### Guide
1) Clone this project and preferably open it in IntelliJ or Eclipse
2) Copy your .ggb file where your points are to the files/read directory (HyLogo.ggb and TestPoints.xml are example files that are already present)
3) Open the Main.java file (its under src/main/java/com/github/NoctuaAstrum). There is some example code you can look at
4) Specify your settings using Configs.\[configToChange]
5) Create a new ParticleDataHolder using it's builder subclass (I'll make a list of all the methods later)
6) Convert the ParticleDataHolder to a ParticleSystem using yourParticleDataHolder.convertToParticleSystem() and write the file using AssetWriter.toJsonFile(yourParticleSpawner)
7) Run the programm and find your file under files/write/

### Contribute and future plans
Feel free to add your own features to this project. If you want something added or want to add something please make an issue for it.

The currently planned features are:
- finish all the methods for the customization of the particleSystem
- make it possible to inject the points into an existing .particlesystem / make a copy with the injected part of it
- specify the export directory (for example to your mod assets)
- add support for [Shinao's Path to Points](https://shinao.github.io/PathToPoints/) and integrate it later
- add a GUI
