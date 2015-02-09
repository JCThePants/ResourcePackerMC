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

import com.jcwhatever.resourcepackermc.ResourcePackFiles;
import com.jcwhatever.resourcepackermc.gui.img.GuiImages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * File based implementation of a {@link TreeView}.
 */
public class FileTree extends TreeView<String> {

    private Controller _controller;

    /**
     * Constructor.
     */
    public FileTree() {
        super(null);

        _controller = Controller.getInstance();
        setContextMenu(createTreeViewContextMenu());

        refresh();
    }

    /**
     * Refresh the file view.
     */
    public void refresh() {

        ResourcePackFiles files = _controller._files;
        if (files == null) {
            setRoot(null);
            return;
        }

        files.refresh();

        FileTreeItem item = getFileNode(files.getRootFolder(), null);
        item.setExpanded(true);

        setRoot(item);
    }

    @Nullable
    private FileTreeItem getSelectedItem() {
        return (FileTreeItem)getSelectionModel().getSelectedItem();
    }

    private ContextMenu createTreeViewContextMenu() {

        final MenuItem open = new MenuItem("Open", new ImageView(GuiImages.OPEN));

        final MenuItem include = new MenuItem("Include");

        final MenuItem exclude = new MenuItem("Exclude", new ImageView(GuiImages.STOP));

        final MenuItem delete = new MenuItem("Delete", new ImageView(GuiImages.DELETE));

        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        SeparatorMenuItem separator2 = new SeparatorMenuItem();

        ContextMenu menu = new ContextMenu(
                open,
                separator1,
                include,
                exclude,
                separator2,
                delete);

        setContextMenu(menu);

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileTreeItem selected = getSelectedItem();
                if (selected == null)
                    return;

                try {
                    Desktop.getDesktop().open(selected.getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        include.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileTreeItem selected = getSelectedItem();
                if (selected == null)
                    return;

                selected.setIncluded(true);
            }
        });

        exclude.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileTreeItem selected = getSelectedItem();
                if (selected == null)
                    return;

                selected.setIncluded(false);
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileTreeItem selected = getSelectedItem();
                if (selected == null)
                    return;

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete File: " + selected.getFile().getName());
                alert.setContentText("Please confirm you wish to delete the file: " + selected.getFile().getName());
                Optional<ButtonType> pressed = alert.showAndWait();
                if (!pressed.isPresent())
                    return;

                if (pressed.get() != ButtonType.OK)
                    return;

                selected.getFile().delete();

                ResourcePackFiles files = _controller._files;
                if (files != null) {

                    files.refresh();
                    selected.getParent().getChildren().remove(selected);
                }
            }
        });

        menu.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                FileTreeItem selected = getSelectedItem();
                if (selected == null) {
                    open.setDisable(true);
                    include.setDisable(true);
                    include.setGraphic(null);
                    exclude.setDisable(true);
                    delete.setDisable(true);
                    return;
                }

                open.setDisable(false);
                include.setDisable(selected.isIncluded() || selected.isDirectory());
                include.setGraphic(new ImageView(selected.getFileImage()));
                exclude.setDisable(!selected.isIncluded() || selected.isDirectory());
                delete.setDisable(false);
            }
        });

        return menu;
    }

    private FileTreeItem getFileNode(File file, @Nullable FileTreeItem parent) {

        FileTreeItem root = new FileTreeItem(_controller, file);
        if (parent != null)
            parent.getChildren().add(root);

        if (file.isDirectory()) {

            File[] files = file.listFiles();
            if (files != null) {

                List<File> fileList = new ArrayList<>(files.length);
                Collections.addAll(fileList, files);

                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {

                        if (!o1.isDirectory() || !o2.isDirectory()) {

                            if (o1.isDirectory())
                                return -1;

                            if (o2.isDirectory())
                                return 1;
                        }

                        return o1.getName().compareTo(o2.getName());
                    }
                });

                for (File childFile : fileList) {

                    if (_controller._files.getFiles().contains(childFile) || childFile.isDirectory()) {
                        FileTreeItem childItem = getFileNode(childFile, root);

                        // remove empty directory
                        if (childFile.isDirectory() && childItem.getChildren().size() == 0) {
                            root.getChildren().remove(childItem);
                        }
                    }
                }
            }
        }

        return root;
    }

}
