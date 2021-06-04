package sample.navigation;

import javafx.stage.Modality;

public class ScreenConfig {
    private String path;
    private String title;
    private Integer width;
    private Integer height;
    private Modality modality;

    public ScreenConfig(String path, String title, Integer width, Integer height, Modality modality) {
        this.path = path;
        this.title = title;
        this.width = width;
        this.height = height;
        this.modality = modality;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }
}
