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

package com.jcwhatever.resourcepackermc.gui.editor;

import com.jcwhatever.resourcepackermc.ResourcePackFiles;

/**
 * Represents a controller for the text editor panels.
 */
public interface IEditorController {

    /**
     * Get the editor settings.
     */
    EditorSettings getSettings();

    /**
     * Load the editor file.
     */
    void loadFile();

    /**
     * Invoke when the editor text is changed.
     *
     * @param newText  The new editor text.
     */
    void onTextChanged(String newText);

    /**
     * Invoke to generate a new file into the text editor.
     *
     * @param files  The current resource pack files.
     */
    void onGenerate(ResourcePackFiles files);

    /**
     * Save the string from the text editor into a default file.
     *
     * @param files  Tge current resource pack files.
     */
    void saveFile(ResourcePackFiles files);
}
