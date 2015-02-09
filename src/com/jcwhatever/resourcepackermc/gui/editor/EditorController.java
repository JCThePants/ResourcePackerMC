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
import com.jcwhatever.resourcepackermc.Utils;

import java.io.File;

/**
 * Universal implementation of {@link IEditorController}.
 */
public class EditorController implements IEditorController {

    private final EditorSettings _settings;
    private final String _filename;
    private File _root;
    private String _currentText = "";

    public EditorController(EditorSettings settings, String filename) {
        _settings = settings;
        _filename = filename;
    }

    public void setRoot(File root) {
        _root = root;
    }

    @Override
    public EditorSettings getSettings() {
        return _settings;
    }

    @Override
    public void loadFile() {

        if (_root == null)
            return;

        File file = Utils.findFile(_filename, _root);

        if (file == null) {
            _settings.getReloadBtn().setDisable(true);
            _currentText = "";
            _settings.getTextArea().setText("");
        }
        else {
            _currentText = Utils.scanTextFile(file);

            _settings.getSaveBtn().setDisable(true);
            _settings.getReloadBtn().setDisable(false);

            _settings.getTextArea().setText(_currentText);
        }
    }

    @Override
    public void onTextChanged(String newText) {
        _settings.getSaveBtn().setDisable(_currentText.equals(newText));
    }

    @Override
    public void onGenerate(ResourcePackFiles files) {

        StringBuilder sb = new StringBuilder(500);

        _settings.getGenerator().generate(files, sb);

        String text = sb.toString();

        _settings.getTextArea().setText(text);

        onTextChanged(text);
    }

    @Override
    public void saveFile(ResourcePackFiles files) {

        _settings.getGenerator().generateFile(files, _root);

        _currentText = _settings.getTextArea().getText();

        _settings.getSaveBtn().setDisable(true);
    }
}
