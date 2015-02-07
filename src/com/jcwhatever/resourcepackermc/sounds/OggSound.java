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

import org.gagravarr.vorbis.VorbisAudioData;
import org.gagravarr.vorbis.VorbisComments;
import org.gagravarr.vorbis.VorbisFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/**
 * A container for a resource pack ogg file.
 */
public class OggSound {

    private final File _file;
    private final String _soundPath;
    private final String _eventName;

    private int _totalSeconds;
    private int _seconds;
    private int _minutes;

    private String _title;
    private String _description;
    private String _artist;
    private String _album;
    private String _trackNumber;
    private String _performer;
    private String _genre;
    private String _copyright;
    private String _license;
    private String _contact;
    private String _isrc;

    private Map<String, String> _extraComments;

    private boolean _hasRead;

    /**
     * Constructor.
     *
     * @param file  The ogg file to represent.
     */
    public OggSound(File file) {
        _file = file;
        _soundPath = JSONSounds.getSoundPath(file);
        _eventName = Utils.getEventName(this);
    }

    /**
     * Get the sound path of the file.
     */
    public String getSoundPath() {
        return _soundPath;
    }

    /**
     * Get the file.
     */
    public File getFile() {
        return _file;
    }

    /**
     * Get the proposed event name for the sound.
     */
    public String getEventName() {
        return _eventName;
    }

    /**
     * Get the total duration in seconds of the audio file.
     */
    public int getTotalSeconds() {
        read();
        return _totalSeconds;
    }

    /**
     * Get the leftover duration in seconds of the audio file. (after removing minutes).
     */
    public int getSeconds() {
        read();
        return _seconds;
    }

    /**
     * Get the duration in minutes of the audio file.
     */
    public int getMinutes() {
        read();
        return _minutes;
    }

    /**
     * Get the title of the sound specified by the ogg file comments.
     *
     * @return  The title or null if not specified.
     */
    @Nullable
    public String getTitle() {
        read();
        return _title;
    }

    /**
     * Get the description of the sound specified by the ogg file comments.
     *
     * @return  The description or null if not specified.
     */
    @Nullable
    public String getDescription() {
        read();
        return _description;
    }

    /**
     * Get the sound artist specified by the ogg file comments.
     *
     * @return  The artist or null if not specified.
     */
    @Nullable
    public String getArtist() {
        read();
        return _artist;
    }

    /**
     * Get the album the sound is from specified by the ogg file comments.
     *
     * @return  The album or null if not specified.
     */
    @Nullable
    public String getAlbum() {
        read();
        return _album;
    }

    /**
     * Get the album track number specified by the ogg file comments.
     *
     * @return  The album track number or null if not specified.
     */
    @Nullable
    public String getTrackNumber() {
        read();
        return _trackNumber;
    }

    /**
     * Get the performer specified by the ogg file comments.
     *
     * @return  The performer or null if not specified.
     */
    @Nullable
    public String getPerformer() {
        read();
        return _performer;
    }

    /**
     * Get the genre of the sound specified by the ogg file comments.
     *
     * @return  The genre or null if not specified.
     */
    @Nullable
    public String getGenre() {
        read();
        return _genre;
    }

    /**
     * Get the sounds copyright specified by the ogg file comments.
     *
     * @return  The sounds copyright or null if not specified.
     */
    @Nullable
    public String getCopyright() {
        read();
        return _copyright;
    }

    /**
     * Get the sounds license specified by the ogg file comments.
     *
     * @return  The sounds license or null if not specified.
     */
    @Nullable
    public String getLicense() {
        read();
        return _license;
    }

    /**
     * Get contact info for the sound specified by the ogg file comments.
     *
     * @return  The contact info or null if not specified.
     */
    @Nullable
    public String getContact() {
        read();
        return  _contact;
    }

    /**
     * Get the sounds ISRC code specified by the ogg file comments.
     *
     * @return  The ISRC or null if not specified.
     */
    @Nullable
    public String getISRC() {
        read();
        return _isrc;
    }

    /**
     * Get all other ogg file comments.
     *
     * <p>The returned map is keyed to the comment title.</p>
     */
    public Map<String, String> getExtraComments() {
        read();
        return _extraComments;
    }

    /**
     * Read the file and fill the {@code OggSound} object with info.
     *
     * <p>Can be called more than once. Will not run more than once.</p>
     */
    private void read() {

        if (_hasRead)
            return;

        _hasRead = true;

        VorbisFile vorbis;
        try {
            vorbis = new VorbisFile(_file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        VorbisAudioData vad;
        long lastGranule = 0;

        try {
            while((vad = vorbis.getNextAudioPacket()) != null) {
                lastGranule = vad.getGranulePosition();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        VorbisComments vorbisComments = vorbis.getComment();
        Map<String, List<String>> comments = vorbisComments.getAllComments();
        _extraComments = new HashMap<>(10);

        for (Entry<String, List<String>> entry : comments.entrySet()) {

            switch (entry.getKey().toLowerCase()) {

                case "title":
                    _title = concat(entry.getValue());
                    break;

                case "album":
                    _album = concat(entry.getValue());
                    break;

                case "tracknumber":
                    _trackNumber = concat(entry.getValue());
                    break;

                case "artist":
                    _artist = concat(entry.getValue());
                    break;

                case "performer":
                    _performer = concat(entry.getValue());
                    break;

                case "copyright":
                    _copyright = concat(entry.getValue());
                    break;

                case "license":
                    _license = concat(entry.getValue());
                    break;

                case "description":
                    _description = concat(entry.getValue());
                    break;

                case "genre":
                    _genre = concat(entry.getValue());
                    break;

                case "contact":
                    _contact = concat(entry.getValue());
                    break;

                case "isrc":
                    _isrc = concat(entry.getValue());
                    break;

                default:
                    _extraComments.put(entry.getKey(), concat(entry.getValue()));
                    break;
            }
        }

        _totalSeconds = (int)(lastGranule / vorbis.getInfo().getRate());
        _minutes = (_totalSeconds / 60);
        _seconds = _totalSeconds - (_minutes * 60);
    }

    private String concat(List<String> list) {
        StringBuilder sb = new StringBuilder(list.size() * 25);

        for (String element : list) {
            if (sb.length() != 0)
                sb.append("; ");

            sb.append(element);
        }
        return sb.toString();
    }
}
