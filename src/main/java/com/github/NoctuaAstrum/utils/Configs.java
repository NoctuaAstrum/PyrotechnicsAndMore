package com.github.NoctuaAstrum.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controls the configurations of file creation and reading as well as logging tools
 */
public class Configs {
    private static boolean printReadResult;
    private static SupportedFileType fileType;
    private static double readingScaleFactor;
    private static String exportName;
    private static String importDirectory;
    static final Gson gson;
    private static ExportMode exportMode;

     static {
         printReadResult = false;
         fileType = SupportedFileType.GGB;
         readingScaleFactor = 1;
         exportName = "Generated";
         importDirectory = "files/read/";
         gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
         exportMode = ExportMode.NEW_FILE;
     }

    /**
     * @param printReadResult if {@code true} prints what {@link AssetReaders} read before converting it to {@link com.github.NoctuaAstrum.utils.data.PointData}
     *                        <p>Default: {@code false}</p>
     */
    public static void setPrintReadResult(boolean printReadResult) {
        Configs.printReadResult = printReadResult;
    }

    /**
     * Sets the filetype that is being read
     * <p>Default: {@link SupportedFileType#GGB}</p>
     */
    public static void setFileType(SupportedFileType fileType) {
        Configs.fileType = fileType;
    }

    /**
     * @param readingScaleFactor is the factor the coords get multiplied with when converting them to {@link com.github.NoctuaAstrum.utils.data.PointData}.
     */
    public static void setReadingScaleFactor(double readingScaleFactor) {
        Configs.readingScaleFactor = readingScaleFactor;
    }

    /**
     * @return if the reading result of {@link AssetReaders} should be pasted to console.
     */
    public static boolean hasPrintReadResult() {
        return printReadResult;
    }

    /**
     * @return what file type is being read.
     */
    public static SupportedFileType getFileType() {
        return fileType;
    }

    /**
     * @return the factor the coords get multiplied with when converting them to {@link com.github.NoctuaAstrum.utils.data.PointData}.
     */
    public static double getReadingScaleFactor() {
        return readingScaleFactor;
    }

    /**
     * @return what the name of the export file is.
     */
    public static String getExportName() {
        return exportName;
    }

    /**
     * @param exportName what the name of the export file should be.
     *                   <p>Default: Generated
     */
    public static void setExportName(String exportName) {
        Configs.exportName = exportName;
    }

    /**
     * @return the directory that is getting imported from.
     */
    public static String getImportDirectory() {
        return importDirectory;
    }

    /**
     * @param importDirectory is the directory the files that are getting imported from.
     *                        <p>DOES NOT CHANGE WHERE THE FILE WITH THE POINTS NEEDS TO BE.</p>
     */
    public static void setImportDirectory(String importDirectory) {
        Configs.importDirectory = importDirectory;
    }

    /**
     * @return how the file is getting exported.
     */
    public static ExportMode getExportMode() {
        return exportMode;
    }

    /**
     * @param exportMode how the files should get exported.
     */
    public static void setExportMode(ExportMode exportMode) {
        Configs.exportMode = exportMode;
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
        XML,
        /**
         * The file containing the points is a .ggb file.
         */
        GGB
    }

    /**
     * Contains the export modes that are supported
     */
    public enum ExportMode{
        /**
         * A new file is created. Disables the import of a .particleSystem File
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
}