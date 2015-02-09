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

import com.jcwhatever.resourcepackermc.generators.IGenerator;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Settings for an editor panel.
 */
public class EditorSettings {

    private final Button _generateBtn;
    private final Button _reloadBtn;
    private final Button _saveBtn;
    private final TextArea _textArea;
    private final IGenerator _generator;

    /**
     * Constructor.
     *
     * @param generate   The panels Generate button.
     * @param reload     The panels Reload button.
     * @param save       The panels Save button.
     * @param textArea   The panels TextArea.
     * @param generator  The panels file generator.
     */
    public EditorSettings(Button generate, Button reload, Button save, TextArea textArea, IGenerator generator) {

        _generateBtn = generate;
        _reloadBtn = reload;
        _saveBtn = save;
        _textArea = textArea;
        _generator = generator;
    }

    /**
     * Get the "Generate" button.
     */
    public Button getGenerateBtn() {
        return _generateBtn;
    }

    /**
     * Get the "Reload" button.
     */
    public Button getReloadBtn() {
        return _reloadBtn;
    }

    /**
     * Get the "Save" button.
     */
    public Button getSaveBtn() {
        return _saveBtn;
    }

    /**
     * Get the editor text area.
     */
    public TextArea getTextArea() {
        return _textArea;
    }

    /**
     * Get the file generator.
     */
    public IGenerator getGenerator() {
        return _generator;
    }
}
