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
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Generates a text file with information about sound files.
 */
public class SoundInfoGenerator {

    private static final String INDENT = "  ";
    private final Collection<OggSound> _sounds;

    /**
     * Constructor.
     *
     * @param sounds  The sounds to include in the file.
     */
    public SoundInfoGenerator(Collection<OggSound> sounds) {
        _sounds = sounds;
    }

    /**
     * Generate the sound info text file.
     *
     * @param file  The output file.
     */
    public void generate(File file) {

        Utils.writeTextFile(file, new ITextWriteHandler() {
            @Override
            public void write(OutputStreamWriter writer) throws IOException {

                boolean hasWritten = false;

                for (OggSound sound : _sounds) {

                    if (hasWritten) {
                        writer.write("-----------------------------------------------\n");
                    }

                    hasWritten = true;

                    writer.write(sound.getSoundPath() + ".ogg");
                    writer.write('\n');

                    writeEntry(writer, "Title", sound.getTitle());
                    writeEntry(writer, "Artist", sound.getArtist());
                    writeEntry(writer, "Performer", sound.getPerformer());
                    writeEntry(writer, "Album", sound.getAlbum());
                    writeEntry(writer, "Track Number", sound.getTrackNumber());
                    writeEntry(writer, "Duration", String.valueOf(sound.getMinutes()) + ':' + sound.getSeconds());
                    writeEntry(writer, "Description", sound.getDescription());
                    writeEntry(writer, "Genre", sound.getGenre());
                    writeEntry(writer, "Copyright", sound.getCopyright());
                    writeEntry(writer, "License", sound.getLicense());
                    writeEntry(writer, "Contact", sound.getContact());
                    writeEntry(writer, "ISRC", sound.getISRC());

                    Map<String, String> extra = sound.getExtraComments();

                    for (Entry<String, String> entry : extra.entrySet()) {
                        writeEntry(writer, entry.getKey(), entry.getValue());
                    }

                    writer.write('\n');
                }
            }
        });
    }

    /**
     * Writes a sound info entry line into the file writer.
     *
     * <p>If the description is null, the entry is not written.</p>
     *
     * @param writer       The file writer.
     * @param title        The entry title.
     * @param description  The entry description.
     *
     * @throws IOException
     */
    private void writeEntry(OutputStreamWriter writer, String title, String description)
            throws IOException {

        if (description != null) {
            writer.write(INDENT);
            writer.write(title);
            writer.write(": ");
            writer.write(description);
            writer.write('\n');
        }
    }
}
