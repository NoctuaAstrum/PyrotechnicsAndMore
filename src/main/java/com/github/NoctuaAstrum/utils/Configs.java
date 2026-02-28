package com.github.NoctuaAstrum.utils;

/**
 * Controls the configurations of file creation and reading as well as logging tools
 */
public class Configs {
    private static boolean printReadResult = false;
    private static SupportedFileType fileType = SupportedFileType.GGB;
    private static double readingScaleFactor = 1;
    private static String exportName = "undefined";

    public static void printReadResult(boolean printReadResult) {
        Configs.printReadResult = printReadResult;
    }

    public static void fileType(SupportedFileType fileType) {
        Configs.fileType = fileType;
    }

    public static void readingScaleFactor(double readingScaleFactor) {
        Configs.readingScaleFactor = readingScaleFactor;
    }

    public static boolean hasPrintReadResult() {
        return printReadResult;
    }

    public static SupportedFileType getFileType() {
        return fileType;
    }

    public static double getReadingScaleFactor() {
        return readingScaleFactor;
    }

    public static String getExportName() {
        return exportName;
    }

    public static void setExportName(String exportName) {
        Configs.exportName = exportName;
    }

    public enum SupportedFileType{
        XML,
        GGB
    }
}