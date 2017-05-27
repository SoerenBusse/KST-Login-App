package de.waishon.kstlogin.utils;

/**
 * Created by Sören on 09.04.2017.
 */

public class Resolution {
    private int width;
    private int height;
    private float scale;

    public Resolution(int width, float scale) {
        this.width = width;
        this.height = (int) (width * scale);
        this.scale = scale;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return (this.width == 0) ? "Automatisch"  : width + "x" + height;
    }
}
