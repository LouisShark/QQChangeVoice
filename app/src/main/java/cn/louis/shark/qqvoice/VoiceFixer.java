package cn.louis.shark.qqvoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LouisShark on 2017/8/24.
 * this is on cn.louis.shark.qqvoice.
 */

public class VoiceFixer {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_LUOLI = 1;
    public static final int MODE_DASHU = 2;
    public static final int MODE_JINGSONG = 3;
    public static final int MODE_GAOGUAI = 4;
    public static final int MODE_KONGLING = 5;

    public static native void fix(String path, int mode);

    /**
     * Saves the sound with effect applied to a specified path on the SD card.
     * 
     * @param inputPath The path of the input sound file.
     * @param outputPath The path where the output sound file will be saved.
     * @param mode The effect mode to apply.
     */
    public static void saveSoundWithEffect(String inputPath, String outputPath, int mode) {
        // Apply the effect using the native fix method
        fix(inputPath, mode);

        // Perform file I/O operations to save the processed sound
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        System.loadLibrary("fmod");
        System.loadLibrary("fmodL");
        System.loadLibrary("change_voice");
    }
}
