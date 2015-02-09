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

package com.jcwhatever.resourcepackermc.sounds;


import com.jcwhatever.resourcepackermc.Utils;
import com.jcwhatever.resourcepackermc.Utils.ITextWriteHandler;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * 
 */
public class JSONSounds {

    @Nullable
    public static String getSoundPath(File file) {

        String absolute = file.getAbsolutePath().replace('\\', '/');

        int index = absolute.lastIndexOf("sounds/");
        if (index == -1)
            return null;

        return absolute.substring(index + 7, absolute.length() - 4);
    }

    Map<String, JSONSoundEvent> _entries = new HashMap<>(50);

    public JSONSounds() throws JSONException {
        this(null);
    }

    public JSONSounds(@Nullable File file) throws JSONException {

        if (file != null) {

            StringBuilder result = new StringBuilder(200);

            Scanner scanner;

            try {
                scanner = new Scanner(new FileInputStream(file), "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (result.length() > 0)
                    result.append('\n');

                result.append(line);
            }

            JSONObject json = new JSONObject(result.toString());

            Iterator<String> iterator = json.keys();
            while (iterator.hasNext()) {
                String soundName = iterator.next();

                JSONObject sound = json.getJSONObject(soundName);

                JSONSoundEvent event = new JSONSoundEvent(soundName, sound);

                _entries.put(soundName, event);
            }
        }
    }

    public Set<String> getSoundNames() {
        return _entries.keySet();
    }

    @Nullable
    public JSONSoundEvent getSoundEvent(String name) {
        return _entries.get(name);
    }

    @Nullable
    public JSONSoundEvent putSoundEvent(String name, JSONSoundEvent event) {
        return _entries.put(name, event);
    }

    @Nullable
    public JSONSoundEvent getEventContains(String soundPath) {

        for (JSONSoundEvent event : _entries.values()) {

            for (JSONSound sound : event.getSounds()) {
                if (sound.getName().equals(soundPath))
                    return event;
            }
        }
        return null;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject object = new JSONObject();

        for (Entry<String, JSONSoundEvent> entry : _entries.entrySet()) {
            object.accumulate(entry.getKey(), entry.getValue().toJSON());
        }

        return object;
    }

    public void write(File file) throws IOException, JSONException {

        if (file.exists())
            file.delete();

        file.createNewFile();

        JSONObject object = toJSON();

        final String json = object.toString(4);

        Utils.writeTextFile(file, new ITextWriteHandler() {
            @Override
            public void write(OutputStreamWriter writer) throws IOException {
                writer.write(json);
            }
        });
    }

    public void write(Appendable appendable) throws IOException, JSONException {

        JSONObject object = toJSON();

        final String json = object.toString(4);

        appendable.append(json);
    }
}
