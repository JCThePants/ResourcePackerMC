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
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import javax.annotation.Nullable;

/**
 * A {@link TreeItem} implementation used for displaying files and directories.
 */
public class FileTreeItem extends TreeItem<String> {

    private final Controller _controller;
    private File _file;
    private boolean _isDirectory;

    /**
     * Constructor.
     *
     * @param file  The represented file.
     */
    public FileTreeItem(Controller controller, File file) {
        super(file.getName());

        _controller = controller;
        _file = file;
        _isDirectory = file.isDirectory();

        if (_isDirectory) {
            EventType<TreeModificationEvent<String>> type = TreeItem.branchExpandedEvent();

            addEventHandler(type, new EventHandler<TreeModificationEvent<String>>() {
                @Override
                public void handle(TreeModificationEvent<String> event) {

                    FileTreeItem source = (FileTreeItem) event.getSource();

                    source.setImage();
                }
            });
        }

        setImage();
    }

    /**
     * Determine if the represented file is a directory.
     */
    public boolean isDirectory() {
        return _isDirectory;
    }

    /**
     * Determine if the file or folder will be included in the resource pack.
     */
    public boolean isIncluded() {
        return !_controller._files.getExcluded().contains(_file);
    }

    /**
     * Set the include flag.
     *
     * @param isIncluded  True to include in resource pack, False to exclude.
     */
    public void setIncluded(boolean isIncluded) {

        if (isIncluded) {
            _controller._files.getExcluded().remove(_file);
        }
        else {
            _controller._files.getExcluded().add(_file);
        }

        setImage();
    }

    /**
     * Get the represented file.
     */
    public File getFile() {
        return _file;
    }

    private void setImage() {

        if (!isIncluded()) {
            setImage(GuiImages.STOP);
            return;
        }

        if (_isDirectory) {

            if (isExpanded())
                setImage(GuiImages.FOLDER_OPEN);
            else
                setImage(GuiImages.FOLDER);

            return;
        }

        Image image = getFileImage();
        if (image != null)
            setImage(image);
    }

    private void setImage(Image image) {
        Node node = getGraphic();
        ImageView view;

        if (node instanceof ImageView) {
            view = (ImageView) node;
        }
        else {
            view = new ImageView();
            setGraphic(view);
        }

        view.setImage(image);
    }

    /**
     * Get the current image.
     */
    @Nullable
    public Image getImage() {
        ImageView view = (ImageView)getGraphic();
        if (view == null)
            return null;

        return view.getImage();
    }

    /**
     * Get the preferred image to use to represent the file.
     */
    @Nullable
    public Image getFileImage() {

        if (_isDirectory)
            return GuiImages.FOLDER;

        String name = getValue().toLowerCase();

        if (name.endsWith(".txt")) {
            return GuiImages.TEXT;
        }
        else if (name.endsWith(".png")) {
            return GuiImages.IMAGE;
        }
        else if (name.endsWith(".mcmeta")) {
            return GuiImages.MCMETA;
        }
        else if (name.endsWith(".json")) {
            return GuiImages.JSON;
        }
        else if (name.endsWith(".lang")) {
            return GuiImages.LANG;
        }
        else if (name.endsWith(".ogg")) {
            return GuiImages.AUDIO;
        }

        return null;
    }
}
