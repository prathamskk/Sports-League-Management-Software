package com.project.sportsleaguemanagementproject.singleton;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.image.Image;

public class ImageLoader {
    private static ImageLoader single_instance = null;
    private final String appdataFolder = System.getenv("APPDATA") + "\\Sports-League-Management-Software\\";

    private ImageLoader() {
        File folder = new File(this.appdataFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

    }

    public static ImageLoader getInstance() {
        if (single_instance == null) {
            single_instance = new ImageLoader();
        }

        return single_instance;
    }

    public Image loadImage(String username) {
        Image img = new Image("file:" + this.appdataFolder + username + "\\avatar.png");
        if (img.getWidth() == 0.0D) {
            img = new Image("file:" + this.appdataFolder + "Default\\avatar.png");
            if (img.getWidth() == 0.0D) {
                System.out.println(img.getUrl());
                File profilePic = new File("./src/main/resources/com/project/sportsleaguemanagementproject/avatar.png");
                System.out.println(profilePic.toPath());
                this.addImage("Default", profilePic);
                img = new Image("file:" + this.appdataFolder + "Default\\avatar.png");
            }
        }

        return img;
    }

    public Image loadImage() {
        return this.loadImage(LoginSingleton.getInstance().username);
    }

    public void addImage(String username, File file) {
        URL url = null;

        try {
            url = new URL("file:" + file.getAbsolutePath());
        } catch (MalformedURLException var9) {
            var9.printStackTrace();
        }

        Image img = new Image(url.getPath());
        System.out.println(img.getUrl());
        File folder = new File(this.appdataFolder + "\\" + username + "\\");

        try {
            folder.mkdir();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        try {
            Files.copy(file.toPath(), Path.of(this.appdataFolder + "\\" + username + "\\avatar.png"));
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }
}
