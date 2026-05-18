package com.github.NoctuaAstrum.utils;


import com.github.NoctuaAstrum.utils.assets.AssetManager;
import com.github.NoctuaAstrum.utils.assets.AssetType;
import com.github.NoctuaAstrum.utils.data.PointData;
import com.github.NoctuaAstrum.utils.data.XYZData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileIO {
    public static final Logger LOGGER = Logger.getLogger("FileIO");


    public static BufferedReader getReaderForAssetFile(String fileName, AssetType fileType){
        return getReader(Path.of(Configs.assetImportDirectory + fileName + fileType.FILE_ENDING));
    }
    private static BufferedReader getReader(Path path){
        try {
            return Files.newBufferedReader(path);
        }catch (IOException e){
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        return null;
    }

    public static List<String> readFile(Path filePath){
        BufferedReader reader = getReader(filePath);
        if (reader == null) return new ArrayList<>(0);
        List<String> fileContent = reader.lines().toList();
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        return fileContent;
    }

    private static HashMap<String,ArrayList<String>> readZipFile0(Path path, String... entryNames) throws IOException {
        HashMap<String,ArrayList<String>> contents = new HashMap<>();

        try (ZipFile ggbFile = new ZipFile(path.toFile())) {
            for (String entryName : entryNames) {
                ZipEntry entry = ggbFile.getEntry(entryName);
                if (entry == null) continue;
                InputStream inputStream = ggbFile.getInputStream(entry);
                Scanner scanner = new Scanner(inputStream);
                ArrayList<String> content = new ArrayList<>();
                while (scanner.hasNext()){
                    String current = scanner.next();
                    if(current.contains("<")){
                        content.add(current);
                    }else{
                        content.set(content.size()-1,content.getLast()+" "+current);
                    }
                }
                scanner.close();
                inputStream.close();
                contents.put(entryName,content);
            }
        }

        return contents;
    }
    public static HashMap<String,ArrayList<String>> readZipFile(Path path, String... entryNames){
        try {
            return readZipFile0(path,entryNames);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        return new HashMap<>(0);
    }

    public static ArrayList<String> readSingleEntryFromZipFile(Path path,String entryName){
        return readZipFile(path,entryName).get(entryName);
    }


    public static void writeAsset(List<String> code, AssetType assetType){
        Path output;
        if(Configs.exportMode == Configs.ExportMode.INJECT_NEW_FILE) {
            output = Path.of(Configs.exportDirectory + AssetManager.importedAssets.get(assetType).get(Configs.Forwarder.getActiveOverwrittenAsset()) + assetType.FILE_ENDING);
        }else{
            output = Path.of(Configs.exportDirectory + Configs.exportName + assetType.FILE_ENDING);
        }

        if(Configs.printToConsoleInstead){
                code.forEach(System.out::println);
        }else{
            try {
                Files.createDirectories(Path.of(Configs.exportDirectory));
                Files.write(output, code);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static class PointReader {
        private final static Pattern pattern;
        private static LinkedHashMap<String, XYZData> mapData;


        static {
            pattern = Pattern.compile("-?[0-9]*\\.[0-9]+E?[+-]?[0-9]?");
            mapData = new LinkedHashMap<>();
        }

        public static PointData readFileAsPointData(String filename) {
            switch (Configs.pointImportFileType) {
                case GGB -> {
                    return toPointData(readFileGGB0(filename));
                }
                case XML -> {
                    return toPointData(readFileXML0(filename));
                }
                case null, default -> {
                    System.out.println("Error! FileType is neither .ggb nor .xml;");
                    return null;
                }
            }
        }

        private static List<String> readFileGGB0(String filename) {
            Path ggbPath = Paths.get(Configs.pointImportDirectory + filename + Configs.SupportedFileType.GGB.FILE_ENDING);
            return filterXmlPointsFile(readSingleEntryFromZipFile(ggbPath, "geogebra.xml"));
        }

        private static List<String> readFileXML0(String filename){
            Path pathXML = Path.of(Configs.pointImportDirectory+ filename + Configs.SupportedFileType.XML.FILE_ENDING);
            return filterXmlPointsFile(readFile(pathXML));
        }

        private static List<String> filterXmlPointsFile(List<String> input) {
            return input.stream()
                    .filter(PointReader::hasFilterRequirements)
                    .map(PointReader::trimString).toList();
        }

        private static PointData toPointData(List<String> fileContent) {
            if (Configs.printReadResult) {
                fileContent.forEach(System.out::println);
            }
            mapData = convertLines(fileContent);
            return new PointData(mapData);
        }

        private static boolean hasFilterRequirements(String line) {
            return (line.contains("<element") && line.contains("type=\"point\"")) ||
                    line.contains("<coords");
        }

        public static LinkedHashMap<String, XYZData> convertLines(List<String> s) {
            LinkedHashMap<String, XYZData> mapping = new LinkedHashMap<>();
            Matcher matcher;
            String name = "";
            List<Double> pointCoords;
            boolean sentAlreadyAssignedWarning = false;
            for (String current : s) {
                if (!(current.contains("="))) {
                    name = current;
                    continue;
                }
                if (name.toLowerCase().contains("temp")) {
                    System.out.println("[INFO] Skipped point " + name + " at " + current);
                    continue;
                }
                if (mapping.containsKey(name)) {
                    System.out.println("[WARNING] Point " + name + " was already assigned " + mapping.get(name) + " but next line was: " + current);
                    sentAlreadyAssignedWarning = true;
                    continue;
                }

                matcher = pattern.matcher(current);
                pointCoords = new ArrayList<>();

                while (matcher.find()) {
                    String coordString = matcher.group();
                    pointCoords.add(MathUtil.roundPoint(coordString));
                }

                if (!pointCoords.isEmpty() && !mapping.containsKey(name)) {
                    XYZData XYZData = new XYZData(pointCoords.get(0), pointCoords.get(1));
                    mapping.put(name, XYZData);
                }
            }
            if (sentAlreadyAssignedWarning) {
                System.out.println("[WARNING-INFO] The prior warnings happened because the file included non point data that had coordinate data (e.g. lines). Normally this shouldn't cause any problems. Please check if the assigned value is correct. If not, please file a bug report including the file you wanted to read");
            }
            return mapping;
        }

        public static String trimString(String s) {
            return s.replace("\t<coords ", "")
                    .replace("<element type=\"point\" label=", "")
                    .replace("_", "")
                    .replace("\"", "")
                    .replace(">", "")
                    .replace("/", "");
        }

    }
}

