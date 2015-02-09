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

package com.jcwhatever.resourcepackermc.gui.img;

import javafx.scene.image.Image;

/**
 * Images used by the GUI.
 */
public class GuiImages {

    public static final Image FOLDER = getImage("folder.png");
    public static final Image FOLDER_OPEN = getImage("folder-open.png");
    public static final Image IMAGE = getImage("image-x-generic.png");
    public static final Image TEXT = getImage("text-x-generic.png");
    public static final Image AUDIO = getImage("audio-x-generic.png");
    public static final Image JSON = getImage("text-x-script.png");
    public static final Image MCMETA = getImage("package-x-generic.png");
    public static final Image LANG = getImage("font-x-generic.png");

    public static final Image DELETE = getImage("edit-delete.png");
    public static final Image STOP = getImage("process-stop.png");
    public static final Image OPEN = getImage("document-open.png");

    private static Image getImage(String name) {
        return new Image(ClassLoader.getSystemResourceAsStream("com/jcwhatever/resourcepackermc/gui/img/" + name));
    }
}
