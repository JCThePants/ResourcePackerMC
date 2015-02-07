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

import com.jcwhatever.resourcepackermc.Utils;
import com.jcwhatever.resourcepackermc.Utils.ITextWriteHandler;
import com.jcwhatever.resourcepackermc.sounds.OggSound;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Generates Yaml file used by NucleusFramework's resource pack sound library.
 */
public class NucleusGenerator {

    private static final String INDENT = "  ";

    private final Collection<OggSound> _sounds;

    /**
     * Constructor.
     *
     * @param sounds  The sounds to include in the file.
     */
    public NucleusGenerator(Collection<OggSound> sounds) {

        _sounds = new ArrayList<>(sounds);
    }

    /**
     * Generate the yaml file.
     *
     * @param file  The output file.
     */
    public void generate(File file) {

        Utils.writeTextFile(file, new ITextWriteHandler() {
            @Override
            public void write(OutputStreamWriter writer) throws IOException {
                for (OggSound sound : _sounds) {
                    writeSound(writer, sound);
                }
            }
        });
    }

    /**
     * Write a sound entry.
     *
     * @param writer  The sound file writer.
     * @param sound   The sound to write.
     *
     * @throws IOException
     */
    private void writeSound(OutputStreamWriter writer, OggSound sound) throws IOException {

        writer.write(sound.getEventName());
        writer.write(':');
        writer.write('\n');

        // write title
        writer.write(INDENT);
        writer.write("title: '");
        writer.write(sound.getTitle() != null ? sound.getTitle() : sound.getEventName());
        writer.write('\'');
        writer.write('\n');

        // write credit if any
        String credit = null;
        if (sound.getArtist() != null)
            credit = sound.getArtist();
        else if (sound.getPerformer() != null)
            credit = sound.getPerformer();

        if (credit != null) {
            writer.write(INDENT);
            writer.write("credit: '");
            writer.write(Utils.escape(credit, '\''));
            writer.write('\'');
            writer.write('\n');
        }

        // write duration
        writer.write(INDENT);
        writer.write("duration: ");
        writer.write(String.valueOf(sound.getTotalSeconds()));
        writer.write('\n');

        writer.write('\n');
    }
}
