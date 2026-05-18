package com.github.NoctuaAstrum.utils;


import com.github.NoctuaAstrum.utils.assets.AssetManager;
import com.github.NoctuaAstrum.utils.assets.AssetType;

/**
 * Controls the configurations of file creation and reading as well as logging tools
 */
public class Configs {
    static boolean printReadResult;
    static SupportedFileType pointImportFileType;
    static double readingScaleFactor;
    static String exportName;
    static String activeOverwrittenAsset;
    static String pointImportDirectory;
    static String assetImportDirectory;
    static String exportDirectory;
    static boolean printToConsoleInstead;
    static ExportMode exportMode;
    static int decimals;

     static {
         printReadResult = false;
         pointImportFileType = SupportedFileType.GGB;
         readingScaleFactor = 1;
         exportName = "Generated";
         pointImportDirectory = "files/read/";
         assetImportDirectory = "files/read/";
         exportDirectory = "files/write/";
         exportMode = ExportMode.NEW_FILE;
         printToConsoleInstead = false;
         decimals = 2;
     }

    /**
     * @param printReadResult if {@code true} prints what {@link FileIO.PointReader} read before converting it to {@link com.github.NoctuaAstrum.utils.data.PointData}
     *                        <p>Default: {@code false}</p>
     */
    public static void setPrintReadResult(boolean printReadResult) {
        Configs.printReadResult = printReadResult;
    }

    /**
     * Sets the filetype that is being read
     * <p>Default: {@link SupportedFileType#GGB}</p>
     */
    public static void setPointImportFileType(SupportedFileType pointImportFileType) {
        Configs.pointImportFileType = pointImportFileType;
    }

    /**
     * @param readingScaleFactor is the factor the coords get multiplied with when converting them to {@link com.github.NoctuaAstrum.utils.data.PointData}.
     */
    public static void setReadingScaleFactor(double readingScaleFactor) {
        Configs.readingScaleFactor = readingScaleFactor;
    }

    /**
     * @param decimals is the amount of decimals that normally get kept/ round to.
     * <p>Default: {@code 2}
     */
    public static void setRoundingDecimals(int decimals) {
        Configs.decimals = decimals;
    }

    /**
     * @param exportName what the name of the export file should be.
     *                   <p>Default: Generated
     */
    public static void setExportName(String exportName) {
        Configs.exportName = exportName;
    }

    /**
     * @param pointImportDirectory is the directory the files that are getting imported from.
     *                        <p>DOES NOT CHANGE WHERE THE FILE WITH THE POINTS NEEDS TO BE.</p>
     */
    public static void setPointImportDirectory(String pointImportDirectory) {
        Configs.pointImportDirectory = validateDir(pointImportDirectory);
    }
    
    /**
     * @param exportDirectory is the directory the generated files get written into
     */
    
    public static void setExportDirectory(String exportDirectory){
        Configs.exportDirectory = validateDir(exportDirectory);
    }
    
    private static String validateDir(String dir){
        dir = dir.replace("\\","/").replace("//","/");
        return dir.endsWith("/") ? dir : dir + "/";
    }
    
    /**
     * @param exportMode how the files should get exported.
     */
    public static void setExportMode(ExportMode exportMode) {
        Configs.exportMode = exportMode;
    }
    
    /**
     * the {@link com.github.NoctuaAstrum.utils.assets.particles.ParticleSystem} now gets printed into the console instead of into a new file
     */
    public static void printToConsoleInstead(){
        printToConsoleInstead = true;
    }

    /**
     * IF YOU WANT TO HAVE A CUSTOM IMPORT DIRECTORY DEFINE IT FIRST USING {@link #setExportDirectory(String)}
     * @param systemName is the name of the files / the ID of the Asset
     */
    public static void importAsset(String systemName, AssetType assetType){
        AssetManager.Importer.readAssetFile(systemName,assetType);
    }

    /**
     * @param assetImportDirectory is the  filePath the Assets get imported from
     */
    public static void setAssetImportDirectory(String assetImportDirectory) {
        Configs.assetImportDirectory = assetImportDirectory;
    }

    /**
     * Contains the filetypes that are supported.
     */
    public enum SupportedFileType{
        /**
         * <p>The file containing the points is a geogebra .xml file (the formatting MUST be like the one Geogebra makes/uses,
         * else there is the possibility that points aren't read correctly).
         * <p>It would be best to use {@link SupportedFileType#GGB}.
         */
        XML(".xml"),
        /**
         * The file containing the points is a .ggb file.
         */
        GGB(".ggb");

        public final String FILE_ENDING;

        SupportedFileType(String fileEnding){
            this.FILE_ENDING = fileEnding;
        }

    }

    /**
     * Contains the export modes that are supported
     */
    public enum ExportMode{
        /**
         * A new file is created
         */
        NEW_FILE,
        /**
         * <p>Injects the points into the imported .particleSystem file and overwrites the old one.
         * <p>ONLY WORKS WITH CUSTOM OUTPUT PATH, ELSE {@link ExportMode#INJECT_NEW_FILE} IS USED.
         */
        INJECT_OVERWRITE,
        /**
         * Injects the points into the imported .particleSystem file and creates a new one.
         */
        INJECT_NEW_FILE
    }

    /**
     * Dev Tool to access values outside of this package
     */
    public static class Forwarder{
        
        /**
         * @return true if either {@link ExportMode#INJECT_OVERWRITE} or {@link ExportMode#INJECT_NEW_FILE} is used
         */
        public static boolean hasInjectMode(){
            return switch (Configs.exportMode) {
                case ExportMode.INJECT_OVERWRITE, ExportMode.INJECT_NEW_FILE -> true;
                default -> false;
            };
        }
        public static String getActiveOverwrittenAsset(){
            return activeOverwrittenAsset;
        }
        public static void setActiveOverwrittenAsset(String activeOverwrittenAsset){
            Configs.activeOverwrittenAsset = activeOverwrittenAsset;
        }
    }
}
