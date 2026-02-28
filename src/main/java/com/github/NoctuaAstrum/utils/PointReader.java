 package com.github.NoctuaAstrum.utils;

import com.github.NoctuaAstrum.utils.data.XYZData;
import com.github.NoctuaAstrum.utils.data.PointData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

 public class PointReader {
    private final static Pattern pattern = Pattern.compile("-?[0-9]*\\.[0-9]+");
    private static LinkedHashMap<String, XYZData> mapData;
    private static double scaleFactor;


    static{
        mapData = new LinkedHashMap<>();
        scaleFactor = Configs.getReadingScaleFactor();
    }

    public static PointData readFileXML(String filename){
        try {
            return toPointData(readFileXML0(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     public static PointData readFileGGB(String filename){
         try {
             return toPointData(readFileGGB0(filename));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }
    private static List<String> readFileGGB0(String filename) throws IOException {
        Path ggbPath = Paths.get("files/read/"+filename+".ggb");
        ZipFile ggbFile = new ZipFile(ggbPath.toFile());
        ZipEntry geogebraXML = ggbFile.getEntry("geogebra.xml");

        List<String> fileContent = new ArrayList<>();
        String current;

        if(geogebraXML!=null){
            InputStream stream = ggbFile.getInputStream(geogebraXML);
            Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8);
            while(scanner.hasNext()){
                current = scanner.next();
                if(current.contains("<")){
                    fileContent.add(current);
                }else{
                    fileContent.set(fileContent.size()-1,fileContent.getLast()+" "+current);
                }
            }
            scanner.close();
            stream.close();
        }
        return fileContent.stream()
                .filter(PointReader::hasFilterRequirements)
                .map(PointReader::trimString).toList();
    }

    private static List<String> readFileXML0(String filename) throws IOException {
        Path path = Paths.get("files/read/"+filename+".xml");

        List<String> fileContent;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            fileContent = reader.lines()
                    .filter(PointReader::hasFilterRequirements)
                    .map(PointReader::trimString).toList();
        }catch (RuntimeException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
        return fileContent;
    }
    private static PointData toPointData(List<String> fileContent){
        if(Configs.hasPrintReadResult()){fileContent.forEach(System.out::println);}
        mapData = convertLines(fileContent);
        return new PointData(mapData);
    }

    private static boolean hasFilterRequirements(String line){
        return (line.contains("<element") && line.contains("type=\"point\"")) ||
                line.contains("<coords");
    }

    public static LinkedHashMap<String, XYZData> convertLines(List<String> s){
        LinkedHashMap<String, XYZData> mapping = new LinkedHashMap<>();
        Matcher matcher;
        String name = "";
        List<Double> pointCoords;
        ArrayList<String> logBuffer = new ArrayList<>();
        boolean sentAlreadyAssignedWarning = false;
        boolean sentPatternWarning = false;
        for (String current : s) {
            if (!(current.contains("="))) {
                name = current;
                continue;
            }
            if(name.toLowerCase().contains("temp")){
                System.out.println("[INFO] Skipped point "+name+" at "+current);
                continue;
            }
            if(mapping.containsKey(name)){
                System.out.println("[WARNING] Point " +name+ " was already assigned "+mapping.get(name)+" but next line was: "+current);
                sentAlreadyAssignedWarning = true;
                continue;
            }
            if(current.contains("E-")){
                logBuffer.add("[ERROR] Unable to read Point correctly! \"E-\" pattern found for point " + name +", but this is not supported. Point was skipped.");
                sentPatternWarning = true;
                continue;
            }

            matcher = pattern.matcher(current);
            pointCoords = new ArrayList<>();

            while (matcher.find()) {
                String singleCoord = matcher.group();
                pointCoords.add((double) Math.round((Double.parseDouble(singleCoord) * scaleFactor) * 100.0) / 100.0);
            }

            if(!pointCoords.isEmpty() && !mapping.containsKey(name)){
                XYZData XYZData = new XYZData(pointCoords.get(0), pointCoords.get(1));
                mapping.put(name, XYZData);
            }
        }
        if(sentAlreadyAssignedWarning){
            System.out.println("[WARNING-INFO] The prior warnings happened because the file included non point data that had coordinate data (e.g. lines). Normally this shouldn't cause any problems. Please check if the assigned value is correct. If not, please file a bug report including the file you wanted to read");
        }
        logBuffer.forEach(System.out::println);
        if(sentPatternWarning){
            System.out.println("[ERROR-INFO] These errors were caused, because a coordinate of the points was near 0.0 and to accommodate the detail, E notation was used by Geogebra, which is not currently supported. To fix this, please manually round the number in Geogebra.");
        }
        return mapping;
    }



    public static String trimString(String s){
        return s.replace("\t<coords ","")
                .replace("<element type=\"point\" label=","")
                .replace("_","")
                .replace("\"","")
                .replace(">","")
                .replace("/","");
    }
}
