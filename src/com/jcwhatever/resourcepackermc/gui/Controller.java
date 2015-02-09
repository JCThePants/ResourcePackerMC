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
import com.jcwhatever.resourcepackermc.Utils;
import com.jcwhatever.resourcepackermc.generators.NucleusGenerator;
import com.jcwhatever.resourcepackermc.generators.PackGenerator;
import com.jcwhatever.resourcepackermc.generators.SoundsExtraTxtGenerator;
import com.jcwhatever.resourcepackermc.generators.SoundsJSONGenerator;
import com.jcwhatever.resourcepackermc.generators.SoundsTxtGenerator;
import com.jcwhatever.resourcepackermc.gui.editor.EditorController;
import com.jcwhatever.resourcepackermc.gui.editor.EditorSettings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The GUI controller.
 */
public class Controller implements Initializable {

    private static Controller _instance;

    /**
     * Get the latest instance of the {@code Controller}.
     */
    public static Controller getInstance() {
        return _instance;
    }

    private List<EditorController> _editorControllers = new ArrayList<>(4);

    File _folder;
    ResourcePackFiles _files;

    @FXML Label labelResourceFolder;

    @FXML Button btnRefreshFiles;
    @FXML Button btnOpenInFolder;
    @FXML Button btnOpenFile;

    @FXML Button btnOpenResourceFolder;
    @FXML Button btnGenerateSoundsJson;
    @FXML Button btnGenerateSoundsTxt;
    @FXML Button btnGenerateSoundsExtraTxt;
    @FXML Button btnGenerateResourceSoundsYml;
    @FXML Button btnZipResourcePack;

    @FXML Button btnReloadSoundsJson;
    @FXML Button btnSaveSoundsJson;

    @FXML Button btnReloadSoundsTxt;
    @FXML Button btnSaveSoundsTxt;

    @FXML Button btnReloadSoundsExtraTxt;
    @FXML Button btnSaveSoundsExtraTxt;

    @FXML Button btnReloadResourceSoundsYml;
    @FXML Button btnSaveResourceSoundsYml;

    @FXML FileTree fileTree;
    @FXML TitledPane pane1;
    @FXML Accordion accordion;

    @FXML TextArea textAreaSoundsJson;
    @FXML TextArea textAreaSoundsTxt;
    @FXML TextArea textAreaSoundsExtraTxt;
    @FXML TextArea textAreaResourceSoundsYml;

    public Controller() {
        _instance = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accordion.setExpandedPane(pane1);

        EditorSettings soundsJson = new EditorSettings(
                btnGenerateSoundsJson, btnReloadSoundsJson, btnSaveSoundsJson, textAreaSoundsJson,
                new SoundsJSONGenerator());
        loadEditor(new EditorController(soundsJson, "sounds.json"));


        EditorSettings soundsTxt = new EditorSettings(
                btnGenerateSoundsTxt, btnReloadSoundsTxt, btnSaveSoundsTxt, textAreaSoundsTxt,
                new SoundsTxtGenerator());
        loadEditor(new EditorController(soundsTxt, "SOUNDS.TXT"));


        EditorSettings soundsExtraTxt = new EditorSettings(
                btnGenerateSoundsExtraTxt, btnReloadSoundsExtraTxt, btnSaveSoundsExtraTxt, textAreaSoundsExtraTxt,
                new SoundsExtraTxtGenerator());
        loadEditor(new EditorController(soundsExtraTxt, "SOUNDS_EXTRA.TXT"));

        EditorSettings resourceSounds = new EditorSettings(
                btnGenerateResourceSoundsYml, btnReloadResourceSoundsYml, btnSaveResourceSoundsYml,
                textAreaResourceSoundsYml,
                new NucleusGenerator());
        loadEditor(new EditorController(resourceSounds, "resource-sounds.yml"));


        fileTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue,
                                TreeItem<String> stringTreeItem, TreeItem<String> t1) {

                if (t1 == null) {
                    btnOpenInFolder.setDisable(true);
                    btnOpenFile.setDisable(true);
                }
                else {
                    btnOpenInFolder.setDisable(false);
                    btnOpenFile.setDisable(false);
                }
            }
        });
    }

    @FXML
    protected void onRefreshFiles(ActionEvent event) {
        loadFolder(_folder);
    }

    @FXML
    protected void onOpenResourceFolder(ActionEvent event) {
        try {
            Desktop.getDesktop().open(_folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSelectResourceFolder(ActionEvent event) {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select resource pack folder");
        chooser.setInitialDirectory(_folder == null ? Utils.getJarFolder() : _folder);

        File selectedFolder = chooser.showDialog(((Node)event.getTarget()).getScene().getWindow());

        if (selectedFolder == null)
            return;

        _folder = selectedFolder;

        loadFolder(_folder);
    }

    @FXML
    protected void onOpenInFolder(ActionEvent event) {

        FileTreeItem item = (FileTreeItem) fileTree.getSelectionModel().getSelectedItem();

        if (item == null)
            return;

        File folder = item.getFile().getParentFile();
        if (folder == null)
            return;

        try {
            Desktop.getDesktop().open(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onOpenFile(ActionEvent event) {

        FileTreeItem item = (FileTreeItem) fileTree.getSelectionModel().getSelectedItem();

        if (item == null)
            return;

        try {
            Desktop.getDesktop().open(item.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onZipResourcePack(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Output Resource Pack File");
        chooser.setInitialDirectory(_folder);
        chooser.setInitialFileName("resource-pack.zip");
        chooser.setSelectedExtensionFilter(new ExtensionFilter("ZIP", "*.zip"));

        File file = chooser.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());
        if (file == null)
            return;

        PackGenerator generator = new PackGenerator();
        generator.generate(_files, file);
    }

    private void loadEditor(final EditorController controller) {

        _editorControllers.add(controller);

        controller.getSettings().getTextArea().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.onTextChanged(t1);
            }
        });

        controller.getSettings().getGenerateBtn().pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                controller.onGenerate(_files);
            }
        });

        controller.getSettings().getReloadBtn().pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                controller.loadFile();
            }
        });

        controller.getSettings().getSaveBtn().pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                controller.saveFile(_files);
            }
        });
    }

    private void loadFolder(File folder) {
        _files = new ResourcePackFiles(folder);

        labelResourceFolder.setText(_folder.getAbsolutePath());

        fileTree.refresh();

        // Enable buttons
        btnRefreshFiles.setDisable(false);
        btnOpenResourceFolder.setDisable(false);
        btnGenerateSoundsJson.setDisable(false);
        btnGenerateSoundsTxt.setDisable(false);
        btnGenerateSoundsExtraTxt.setDisable(false);
        btnGenerateResourceSoundsYml.setDisable(false);
        btnZipResourcePack.setDisable(false);

        // Enable TextArea's
        textAreaSoundsJson.setDisable(false);
        textAreaSoundsTxt.setDisable(false);
        textAreaSoundsExtraTxt.setDisable(false);
        textAreaResourceSoundsYml.setDisable(false);

        for (EditorController controller : _editorControllers) {
            controller.setRoot(folder);
            controller.loadFile();
        }
    }
}
