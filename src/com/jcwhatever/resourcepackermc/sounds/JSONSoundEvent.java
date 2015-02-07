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

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A sounds.json object. Represents a Minecraft sounds event.
 */
public class JSONSoundEvent {

    private String _name;
    private boolean _replace = false;
    private String _category = "music";
    private List<JSONSound> _sounds = new ArrayList<>(50);

    /**
     * Constructor.
     *
     * @param name  The event name.
     */
    public JSONSoundEvent(String name) {
        _name = name;
    }

    /**
     * Constructor.
     *
     * @param name    The event name.
     * @param object  An existing {@code JSONObject} that represents the sound event.
     *
     * @throws JSONException
     */
    public JSONSoundEvent(String name, JSONObject object) throws JSONException {
        _name = name;

        if (object.has("replace"))
            _replace = object.getBoolean("replace");

        if (object.has("category"))
            _category = object.getString("category");

        if (object.has("sounds")) {

            JSONArray array = object.getJSONArray("sounds");

            int len = array.length();

            for (int i=0; i < len; i++) {

                JSONObject soundObject = array.optJSONObject(i);

                if (soundObject == null) {
                    String path = array.optString(i);
                    if (path == null)
                        continue;

                    _sounds.add(new JSONSound(name));
                }
                else {
                    _sounds.add(new JSONSound(soundObject));
                }
            }
        }
    }

    /**
     * Get the sound event name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Determine if the sounds in the event should replace default Minecraft sounds or
     * be added to them. Default is false.
     */
    public boolean isReplace() {
        return _replace;
    }

    /**
     * Set the replace flag.
     *
     * @param isReplace  True to replace, false to add.
     */
    public void setReplace(boolean isReplace) {
        _replace = isReplace;
    }

    /**
     * Get the sound category. Default is "music".
     */
    public String getCategory() {
        return _category;
    }

    /**
     * Set the sound category.
     *
     * <p>Minecraft values are: "ambient", "weather", "player", "neutral",
     * "hostile", "block", "record", "music", and "master"</p>
     *
     * @param category  The category.
     */
    public void setCategory(String category) {
        _category = category;
    }

    /**
     * Get the event sounds.
     */
    public List<JSONSound> getSounds() {
        return _sounds;
    }

    /**
     * Create a new {@code JSONObject} that represents the {@code JSONSoundEvent}.
     *
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();

        object.accumulate("replace", _replace);
        object.accumulate("category", _category);

        if (!_sounds.isEmpty()) {

            JSONArray array = new JSONArray();

            for (JSONSound sound : _sounds) {
                array.put(sound.toJSON());
            }

            object.accumulate("sounds", array);
        }

        return object;
    }
}
