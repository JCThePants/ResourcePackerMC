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
public class SoundsTxtGenerator implements IGenerator {

    private static final String INDENT = "  ";

    @Override
    public void generateFile(ResourcePackFiles packFiles, File root) {
        File file = new File(root, "SOUNDS.TXT");
        generate(packFiles, file);
    }

    @Override
    public void generate(final ResourcePackFiles packFiles, File file) {

        Utils.writeTextFile(file, new ITextWriteHandler() {
            @Override
            public void write(OutputStreamWriter writer) throws IOException {

                SoundsTxtGenerator.this.write(packFiles, writer);
            }
        });

    }

    @Override
    public void generate(ResourcePackFiles packFiles, StringBuilder sb) {

        try {
            write(packFiles, sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Collection<OggSound> getSounds(ResourcePackFiles files) {
        return files.getSounds();
    }

    private void write(ResourcePackFiles files, Appendable writer) throws IOException {

        Collection<OggSound> sounds = getSounds(files);
        boolean hasWritten = false;

        for (OggSound sound : sounds) {

            if (hasWritten) {
                writer.append("-----------------------------------------------\n");
            }

            hasWritten = true;

            writer.append(sound.getSoundPath()).append(".ogg");
            writer.append('\n');

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

            writer.append('\n');
        }
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
    private void writeEntry(Appendable writer, String title, String description)
            throws IOException {

        if (description != null) {
            writer.append(INDENT);
            writer.append(title);
            writer.append(": ");
            writer.append(description);
            writer.append('\n');
        }
    }
}
