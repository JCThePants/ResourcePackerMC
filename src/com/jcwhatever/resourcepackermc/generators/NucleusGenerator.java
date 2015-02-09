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
import com.jcwhatever.resourcepackermc.Utils;
import com.jcwhatever.resourcepackermc.Utils.ITextWriteHandler;
import com.jcwhatever.resourcepackermc.sounds.MinecraftSounds;
import com.jcwhatever.resourcepackermc.sounds.OggSound;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

/**
 * Generates Yaml file used by NucleusFramework's resource pack sound library.
 */
public class NucleusGenerator implements IGenerator {

    private static final String INDENT = "  ";

    @Override
    public void generateFile(ResourcePackFiles packFiles, File root) {
        File file = new File(root, "resource-sounds.yml");
        generate(packFiles, file);
    }

    @Override
    public void generate(ResourcePackFiles files, File file) {

        final Collection<OggSound> sounds = MinecraftSounds.removeMinecraft(files.getSounds());

        Utils.writeTextFile(file, new ITextWriteHandler() {
            @Override
            public void write(OutputStreamWriter writer) throws IOException {
                for (OggSound sound : sounds) {
                    writeSound(writer, sound);
                }
            }
        });
    }

    @Override
    public void generate(ResourcePackFiles files, StringBuilder sb) {

        Collection<OggSound> sounds = MinecraftSounds.removeMinecraft(files.getSounds());

        try {
            for (OggSound sound : sounds) {
                writeSound(sb, sound);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write a sound entry.
     *
     * @param writer  The sound file writer.
     * @param sound   The sound to write.
     *
     * @throws IOException
     */
    private void writeSound(Appendable writer, OggSound sound) throws IOException {

        writer.append(sound.getEventName());
        writer.append(':');
        writer.append('\n');

        // write title
        writer.append(INDENT);
        writer.append("title: '");
        writer.append(sound.getTitle() != null ? sound.getTitle() : sound.getEventName());
        writer.append('\'');
        writer.append('\n');

        // write type (Guessing)
        writer.append(INDENT);
        writer.append("type: '");

        if (sound.getTotalSeconds() < 10)
            writer.append("effect");
        else
            writer.append("music");

        writer.append('\'');
        writer.append('\n');

        // write credit if any
        String credit = null;
        if (sound.getArtist() != null)
            credit = sound.getArtist();
        else if (sound.getPerformer() != null)
            credit = sound.getPerformer();

        if (credit != null) {
            writer.append(INDENT);
            writer.append("credit: '");
            writer.append(Utils.escape(credit, '\''));
            writer.append('\'');
            writer.append('\n');
        }

        // write duration
        writer.append(INDENT);
        writer.append("duration: ");
        writer.append(String.valueOf(sound.getTotalSeconds()));
        writer.append('\n');

        writer.append('\n');
    }
}
