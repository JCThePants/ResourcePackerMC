/*
 * This file is part of ResourcePackerMC for Bukkit, licensed under the MIT License (MIT).
 *
 * Copyright (c) JCThePants (www.jcwhatever.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jcwhatever.resourcepackermc;

import com.jcwhatever.resourcepackermc.sounds.OggSound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Static helper methods.
 */
public class Utils {

    private Utils() {}

    /**
     * Converts the casing of the supplied {@code OggSound} file name
     * into a Minecraft sound event name.
     */
    public static String getEventName(OggSound sound) {

        String s = sound.getFile().getName();
        s = s.substring(0, s.length() - 4);
        s = s.replace('_', ' ');

        if (s.length() < 2)
            return s;

        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder(s.length() + 10);

        for (String word : words) {

            if (word.length() <= 3) {
                result.append(word);
            } else {

                String firstLetter = word.substring(0, 1).toUpperCase();
                result.append(firstLetter);
                result.append(word.subSequence(1, word.length()));
            }
        }

        return result.toString();
    }

    /**
     * Write a text file.
     *
     * @param file          The file.
     * @param writeHandler  An object responsible for writing to the file.
     */
    public static void writeTextFile(File file, ITextWriteHandler writeHandler) {

        OutputStreamWriter writer = null;

        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(fileStream, "UTF-8");
            writeHandler.write(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Read a text file.
     *
     * @param file  The text file to read.
     */
    public static String scanTextFile(File file) {

        StringBuilder result = new StringBuilder(100);

        try {
            Scanner scanner = new Scanner(new FileInputStream(file), "UTF-8");

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (result.length() > 0)
                    result.append('\n');

                result.append(line);
            }

            return result.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Find a file by case sensitive name. (Recursive)
     *
     * @param name    The name of the file.
     * @param folder  THe folder to search in.
     *
     * @return  The file or null if not found.
     */
    public static File findFile(String name, File folder) {

        File[] files = folder.listFiles();
        if (files == null)
            return null;

        for (File file : files) {

            if (file.isDirectory()) {
                File result = findFile(name, file);
                if (result != null)
                    return result;
            }
            else if (file.getName().equals(name)) {
                return file;
            }
        }

        return null;
    }

    public static String escape(String str, char escapeChar) {
        return str.replaceAll(String.valueOf(escapeChar), "\\" + escapeChar);
    }

    /**
     * Get the FTPFolderWatcher jar file.
     */
    public static File getJar() {

        try {
            return new File(
                    Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    /**
     * Get the folder the FTPFolderWatcher jar is in.
     */
    public static File getJarFolder() {
        File baseFile = getJar();
        return baseFile.getParentFile();
    }

    /**
     * Used by {@code #writeTextFile}.
     */
    public interface ITextWriteHandler {
        void write(OutputStreamWriter writer) throws IOException;
    }
}
