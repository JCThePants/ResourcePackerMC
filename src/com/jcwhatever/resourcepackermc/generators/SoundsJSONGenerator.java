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

import com.jcwhatever.resourcepackermc.sounds.JSONSound;
import com.jcwhatever.resourcepackermc.sounds.JSONSoundEvent;
import com.jcwhatever.resourcepackermc.sounds.JSONSounds;
import com.jcwhatever.resourcepackermc.sounds.OggSound;

import org.apache.sling.commons.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.Nullable;

/**
 * Generates a Minecraft resource pack sound json file.
 */
public class SoundsJSONGenerator {

    private File _resourceFolder;
    private File _soundsjson;

    /**
     * Constructor.
     *
     * @param resourceFolder  The uncompressed resource pack root folder.
     * @param existingFile    The existing sounds.json file, if any. Used to include existing entries.
     */
    public SoundsJSONGenerator(File resourceFolder, @Nullable File existingFile) {
        _resourceFolder = resourceFolder;
        _soundsjson = existingFile;
    }

    /**
     * Get the sounds.json file object.
     */
    public File getFile() {
        if (_soundsjson == null) {
            String soundsJsonPath = _resourceFolder.getAbsolutePath() +
                    File.separatorChar + "assets" +
                    File.separatorChar + "minecraft" +
                    File.separatorChar + "sounds.json";

            return new File(soundsJsonPath);
        }
        else {
            return _soundsjson;
        }
    }

    /**
     * Generate the sounds.json file.
     *
     * @param oggSounds  The sounds to include.
     *
     * @throws JSONException
     */
    public void generate(Collection<OggSound> oggSounds) throws JSONException {

        JSONSounds sounds = new JSONSounds(_soundsjson);

        // add sounds
        for (OggSound ogg : oggSounds) {

            String name = ogg.getSoundPath();
            boolean stream = ogg.getTotalSeconds() > 3;

            JSONSound sound = new JSONSound(name);
            sound.setStream(stream);


            JSONSoundEvent event = new JSONSoundEvent(ogg.getEventName());
            event.getSounds().add(sound);

            if (name.contains("ambient"))
                event.setCategory("ambient");
            else if (name.contains("weather"))
                event.setCategory("weather");
            else if (name.contains("player"))
                event.setCategory("player");
            else if (name.contains("neutral"))
                event.setCategory("neutral");
            else if (name.contains("hostile"))
                event.setCategory("hostile");
            else if (name.contains("block"))
                event.setCategory("block");
            else if (name.contains("record"))
                event.setCategory("record");

            sounds.putSoundEvent(event.getName(), event);
        }

        try {
            sounds.writeToFile(getFile());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
