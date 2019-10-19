package io.westerngun;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

@Slf4j
public class Dispatcher {
    public static void main(String[] args) {
        osInfoLogging();
        swtInfoLogging();

        Runnable runnable = new MainFrame();
        runnable.run();
    }

    private static void swtInfoLogging() {
        log.trace("SWT platform: {}", SWT.getPlatform());
        log.trace("SWT version: {}", SWT.getVersion());
    }

    private static void osInfoLogging() {
        log.trace("OS platform: {}", System.getProperty("os.name"));
        log.trace("OS version: {}", System.getProperty("os.version"));
        log.trace("OS arch: {}", System.getProperty("os.arch"));
    }

    static class DrawIconRed extends Drawer {

        DrawIconRed(Drawable drawable, Display display) {
            super(drawable, display);
        }

        @Override
        void draw() {
            getGc().setBackground(getDisplay().getSystemColor(SWT.COLOR_RED));
            getGc().fillArc(0, 0, 64, 64, 45, 270);
        }
    }

    static class DrawIconBlue extends Drawer {

        DrawIconBlue(Drawable drawable, Display display) {
            super(drawable, display);
        }

        @Override
        void draw() {
            getGc().setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
            getGc().fillArc(0, 0, 16, 16, 45, 270);
        }
    }
}
