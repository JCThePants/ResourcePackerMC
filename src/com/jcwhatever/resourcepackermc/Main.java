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

import com.jcwhatever.resourcepackermc.generators.NucleusGenerator;
import com.jcwhatever.resourcepackermc.generators.PackGenerator;
import com.jcwhatever.resourcepackermc.generators.SoundInfoGenerator;
import com.jcwhatever.resourcepackermc.generators.SoundsJSONGenerator;
import com.jcwhatever.resourcepackermc.sounds.MinecraftSounds;
import com.jcwhatever.resourcepackermc.sounds.OggSound;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.sling.commons.json.JSONException;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.Collection;

/**
 * Main console class.
 */
public class Main {

    static Options _options = new Options();

    static {
        _options.addOption("folder", true, "The folder to pack.");
        _options.addOption("zip", true, "Specify the filename of the resource pack zip file to create. " +
                "Omit to prevent zip file creation.");
        _options.addOption("sounds", false, "Create a sounds.json file or modify existing.");
        _options.addOption("nucleus", false, "Create a NucleusFramework resource-sounds.yml file.");
        _options.addOption("soundtxt", false, "Create SOUNDS.TXT and SOUNDS_EXTRA.TXT file containing " +
                "information about sounds.");
        _options.addOption("help", false, "Get option help.");
    }

    public static void main(String[] args) {

        CommandLineParser parser = new BasicParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(_options, args);
        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage());
            showHelp();
            System.exit(-1);
            return;
        }

        // show help if requested
        if (cmd.hasOption("help")) {
            showHelp();
            return;
        }

        File folder;

        if (cmd.hasOption("folder")) {
            // use specified folder
            String foldername = cmd.getOptionValue("folder");
            folder = new File(foldername);
        } else {
            // use folder jar file is in.
            folder = Utils.getJarFolder();
        }

        // make sure folder exists
        if (!folder.exists()) {
            System.err.println("Folder not found: " + folder);
            System.exit(-1);
            return;
        }

        ResourcePackFiles files = new ResourcePackFiles(folder);
        Collection<OggSound> sounds = files.getSounds();
        Collection<OggSound> extraSounds = MinecraftSounds.removeMinecraft(sounds);

        // generate/update sounds.json file
        if (cmd.hasOption("sounds")) {

            SoundsJSONGenerator generator =
                    new SoundsJSONGenerator(folder, files.getSoundsJson());
            try {
                generator.generate(extraSounds);

                // add newly generated sounds.json files to files collection
                if (files.getSoundsJson() == null) {
                    File file = generator.getFile();
                    files.getFiles().add(file);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("Error while generating sounds.json");
            }
        }

        // Generate resource-sounds.yml
        if (cmd.hasOption("nucleus")) {
            NucleusGenerator generator = new NucleusGenerator(extraSounds);
            generator.generate(new File(Utils.getJarFolder(), "resource-sounds.yml"));
        }

        // Generate SOUNDS.TXT and SOUNDS_EXTRA.TXT
        if (cmd.hasOption("soundtxt")) {
            SoundInfoGenerator generator = new SoundInfoGenerator(sounds);

            File tracksFile = new File(Utils.getJarFolder(), "SOUNDS.TXT");

            generator.generate(tracksFile);
            System.out.println("Generated SOUNDS.TXT");

            if (!files.getFiles().contains(tracksFile)) {
                files.getFiles().add(tracksFile);
                System.out.println("Packed SOUNDS.TXT");
            }

            if (!extraSounds.isEmpty()) {

                SoundInfoGenerator extraGen = new SoundInfoGenerator(extraSounds);
                File extraTracksFile = new File(Utils.getJarFolder(), "SOUNDS_EXTRA.TXT");

                extraGen.generate(extraTracksFile);
                System.out.println("Generated SOUNDS_EXTRA.TXT");

                if (!files.getFiles().contains(extraTracksFile)) {
                    files.getFiles().add(extraTracksFile);
                    System.out.println("Packed SOUNDS_EXTRA.TXT");
                }
            }
        }

        // Generate Zip file
        if (cmd.hasOption("zip")) {
            String filename = cmd.getOptionValue("zip");
            File file = new File(Utils.getJarFolder(), filename);

            PackGenerator generator = new PackGenerator(files);
            try {
                generator.generate(file);
            } catch (ZipException e) {
                e.printStackTrace();
                System.err.println("Error while generating ZIP file.");
            }
        }
    }

    /**
     * Show help.
     */
    private static void showHelp() {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar " + Utils.getJar().getName(), _options);
    }
}
