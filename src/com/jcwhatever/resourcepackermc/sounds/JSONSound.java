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


import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

/**
 * A sounds.json object. Represents a sound in a sound event object.
 */
public class JSONSound {

    private String _name;
    private double _volume = 1.0f;
    private double _pitch = 1.0f;
    private double _weight = 1.0f;
    private boolean _stream = false;
    private String _type = "sound";

    /**
     * Constructor.
     *
     * @param name  The sound event name or relative sound file path (no including .ogg extension).
     */
    public JSONSound(String name) {
        _name = name;
    }

    /**
     * Constructor.
     *
     * @param object  A {@code JSONObject} representing an existing entry from a json file.
     *
     * @throws JSONException
     */
    public JSONSound(JSONObject object) throws JSONException {

        _name = object.getString("name");

        if (object.has("volume"))
            _volume = object.getDouble("volume");

        if (object.has("pitch"))
            _pitch = object.getDouble("pitch");

        if (object.has("weight"))
            _weight = object.getDouble("weight");

        if (object.has("stream"))
            _stream = object.getBoolean("stream");

        if (object.has("type"))
            _type = object.getString("type");
    }

    /**
     * The sound event name or relative sound file path (not including .ogg extension).
     */
    public String getName() {
        return _name;
    }

    /**
     * Get the volume the sound should be played at.  Default is 1.0
     */
    public double getVolume() {
        return _volume;
    }

    /**
     * Set the volume the sound should be played at.
     *
     * @param volume  The volume.
     */
    public void setVolume(double volume) {
        _volume = volume;
    }

    /**
     * Get the pitch the sound should be played at. Default is 1.0
     */
    public double getPitch() {
        return _pitch;
    }

    /**
     * Set the pitch the sound should be played at.
     *
     * @param pitch  The pitch.
     */
    public void setPitch(double pitch) {
        _pitch = pitch;
    }

    /**
     * Get the weight of the sound in relation to other sounds included in the
     * sound event. Default is 1.0
     */
    public double getWeight() {
        return _weight;
    }

    /**
     * Set the weight of the sound in relation to other sounds included in the
     * sound event.
     *
     * @param weight  The weight.
     */
    public void setWeight(double weight) {
        _weight = weight;
    }

    /**
     * Determine if the sound should be streamed during playback.
     */
    public boolean isStream() {
        return _stream;
    }

    /**
     * Set the sounds stream playback flag.
     *
     * @param isStream  True to stream, otherwise false.
     */
    public void setStream(boolean isStream) {
        _stream = isStream;
    }

    /**
     * Get the sound type.  Default is "sound".
     */
    public String getType() {
        return _type;
    }

    /**
     * Set the sound type.
     *
     * @param type  The type.
     */
    public void setType(String type) {
        _type = type;
    }

    /**
     * Create a new {@code JSONObject} that represents the {@code JSONSound}.
     *
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.accumulate("name", _name);
        object.accumulate("volume", _volume);
        object.accumulate("pitch", _pitch);
        object.accumulate("weight", _weight);
        object.accumulate("stream", _stream);
        object.accumulate("type", _type);

        return object;
    }
}
