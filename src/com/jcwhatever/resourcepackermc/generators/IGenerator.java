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

import java.io.File;

/**
 * Interface for a file generator.
 */
public interface IGenerator {

    /**
     * Generate the file in the default location.
     *
     * @param packFiles  The resource pack files.
     * @param root       The root resource folder.
     */
    public void generateFile(ResourcePackFiles packFiles, File root);

    /**
     * Generate the file.
     *
     * @param packFiles  The resource pack files.
     * @param file       The output file.
     */
    public void generate(ResourcePackFiles packFiles, File file);

    /**
     * Generate the file into a {@code StringBuilder}.
     *
     * @param packFiles  The resource pack files.
     * @param sb         The {@code StringBuilder} to append the output to.
     */
    public void generate(ResourcePackFiles packFiles, StringBuilder sb);


}
