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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/**
 * Scans a resource pack folder for resource pack files.
 */
public class ResourcePackFiles {

    private static final Pattern PATTERN_DOT = Pattern.compile("\\.");

    private final File _folder;

    private Set<String> _includeExt = new HashSet<>(10);
    private Collection<OggSound> _sounds = new ArrayList<>(100);
    private ArrayList<File> _filesToAdd = new ArrayList<>(100);
    private File _soundsjson;

    /**
     * Constructor.
     *
     * @param resourceFolder  The resource pack root folder.
     */
    public ResourcePackFiles(File resourceFolder) {
        _folder = resourceFolder;

        _includeExt.add("txt");
        _includeExt.add("ogg");
        _includeExt.add("png");
        _includeExt.add("mcmeta");
        _includeExt.add("lang");
        _includeExt.add("json");

        iterateFiles(_folder, "");
    }

    /**
     * Get files found. Direct reference. Files can be added or removed
     * from the returned {@code ArrayList}.
     */
    public ArrayList<File> getFiles() {
        return _filesToAdd;
    }

    /**
     * Get a collection of {@code OggSound}'s that were found.
     *
     * <p>Not modifiable.</p>
     */
    public Collection<OggSound> getSounds() {
        return new ArrayList<>(_sounds);
    }

    /**
     * Get the sounds.json file, if found.
     *
     * @return  The sounds.json file or null if none found.
     */
    @Nullable
    public File getSoundsJson() {
        return _soundsjson;
    }

    /**
     * Get the resource pack root folder specified in constructor.
     */
    public File getRootFolder() {
        return _folder;
    }

    /**
     * Recursively iterate folders to retrieve resource pack files.
     *
     * <p>Results are added to (@code _filesToAdd} field.</p>
     *
     * @param folder   The folder to search.
     * @param zipPath  The relative path of the folder.
     */
    private void iterateFiles(File folder, String zipPath) {

        File[] files = folder.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            String name = file.getName();

            if(name.startsWith("."))
                continue;

            if (file.isDirectory()) {

                String path = (zipPath.isEmpty() ? File.separator : zipPath + File.separatorChar) + name;

                iterateFiles(file, path);
            }
            else {

                String[] comp = PATTERN_DOT.split(name);
                if (comp.length > 0) {

                    String ext = comp[comp.length - 1].toLowerCase();

                    boolean isSoundsJSON = name.equals("sounds.json");

                    if (_includeExt.contains(ext) || isSoundsJSON) {

                        if (isSoundsJSON)
                            _soundsjson = file;

                        _filesToAdd.add(file);
                        System.out.println("Found " + zipPath + File.separatorChar + name);
                    }

                    if (ext.equals("ogg")) {
                        _sounds.add(new OggSound(file));
                    }
                }
            }
        }
    }
}
