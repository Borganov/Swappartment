package ch.hevs.swap.ui.homepage;

import android.app.Application;

public class GlobalVariable extends Application {

    public boolean isBuyer;

    public int color;

    public boolean isBuyer() {
        return isBuyer;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
