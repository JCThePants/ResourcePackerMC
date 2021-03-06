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

package com.jcwhatever.resourcepackermc.generators;

import com.jcwhatever.resourcepackermc.ResourcePackFiles;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * Generates resource pack zip file.
 */
public class PackGenerator implements IGenerator {

    @Override
    public void generateFile(ResourcePackFiles packFiles, File root) {
        File file = new File(root, "pack.zip");
        generate(packFiles, file);
    }

    @Override
    public void generate(ResourcePackFiles packFiles, File file) {

        if (file.exists())
            file.delete();

        try {
            ZipFile zip = new ZipFile(file);
            zip.setFileNameCharset("UTF-8");

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            //parameters.setDefaultFolderPath(zipPath.isEmpty() ? File.separator : zipPath);
            parameters.setDefaultFolderPath(packFiles.getRootFolder().getAbsolutePath());

            ArrayList<File> files = new ArrayList<>(packFiles.getFiles());
            files.removeAll(packFiles.getExcluded());

            zip.addFiles(files, parameters);

        }
        catch (ZipException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void generate(ResourcePackFiles packFiles, StringBuilder sb) {
        throw new UnsupportedOperationException();
    }
}
