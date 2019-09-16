package io.westerngun;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public abstract class Drawer {
    private final Drawable drawable;

    @Getter
    private final Display display;

    @Getter
    private GC gc;

    abstract void draw();

    public void drawOnImageAndRelease() {
        draw();
        gc.dispose(); // contract of releasing resources immediately
    }

    public Drawer(Drawable drawable, Display display) {
        this.drawable = drawable;
        this.display = display;
        this.gc = new GC(drawable);
    }
}
