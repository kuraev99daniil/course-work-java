package sample.navigation;

public class SceneConfig {
    private String path;
    private Integer width;
    private Integer height;
    private Switcher switcher;

    public SceneConfig(String path, Integer width, Integer height, Switcher switcher) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.switcher = switcher;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Switcher getSwitcher() {
        return switcher;
    }

    public void setSwitcher(Switcher switcher) {
        this.switcher = switcher;
    }
}
