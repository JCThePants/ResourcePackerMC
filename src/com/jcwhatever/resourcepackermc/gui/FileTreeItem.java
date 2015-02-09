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

package com.jcwhatever.resourcepackermc.gui;

import com.jcwhatever.resourcepackermc.gui.img.GuiImages;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * A {@link TreeItem} implementation used for displaying files and directories.
 */
public class FileTreeItem extends TreeItem<String> {

    private File _file;
    private boolean _isDirectory;

    /**
     * Constructor.
     *
     * @param file  The represented file.
     */
    public FileTreeItem(File file) {
        super(file.getName());

        _file = file;
        _isDirectory = file.isDirectory();

        if (_isDirectory) {
            setImage(GuiImages.FOLDER);

            EventType<TreeModificationEvent<String>> type = TreeItem.branchExpandedEvent();

            addEventHandler(type, new EventHandler<TreeModificationEvent<String>>() {
                @Override
                public void handle(TreeModificationEvent<String> event) {

                    FileTreeItem source = (FileTreeItem) event.getSource();

                    ImageView view = (ImageView) source.getGraphic();

                    if (source.isExpanded()) {
                        view.setImage(GuiImages.FOLDER_OPEN);
                    } else {
                        view.setImage(GuiImages.FOLDER);
                    }
                }
            });
        }
        else {
            String name = getValue().toLowerCase();

            if (name.endsWith(".txt")) {
                setImage(GuiImages.TEXT);
            }
            else if (name.endsWith(".png")) {
                setImage(GuiImages.IMAGE);
            }
            else if (name.endsWith(".mcmeta")) {
                setImage(GuiImages.MCMETA);
            }
            else if (name.endsWith(".json")) {
                setImage(GuiImages.JSON);
            }
            else if (name.endsWith(".lang")) {
                setImage(GuiImages.LANG);
            }
            else if (name.endsWith(".ogg")) {
                setImage(GuiImages.AUDIO);
            }
        }
    }

    /**
     * Determine if the represented file is a directory.
     */
    public boolean isDirectory() {
        return _isDirectory;
    }

    /**
     * Get the represented file.
     */
    public File getFile() {
        return _file;
    }

    private void setImage(Image image) {
        setGraphic(new ImageView(image));
    }
}
