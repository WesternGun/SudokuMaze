package io.westerngun;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

@Slf4j
public class Dispatcher {
    public static void main(String[] args) {
        osInfoLogging();
        swtInfoLogging();

        Runnable runnable = (() -> {
            Display display = new Display(); // perfectly supports two monitors(can cross-monitor)
            Shell shell = new Shell(display, SWT.SHELL_TRIM); // TITLE | CLOSE | MIN | MAX | RESIZE
            shell.setMinimumSize(new Point(500, 500));
            shell.setSize(new Point(700, 700));
            shell.setMaximized(false);
            shell.setCapture(false); // if "true", cursor will be "halting" in the app window in Win10
            shell.setText("Sudoku Maze by WesternGun");
            shell.addPaintListener(new PaintListener(){
                public void paintControl(PaintEvent e){
                    Rectangle clientArea = shell.getClientArea();
                    e.gc.drawLine(0,0,clientArea.width,clientArea.height);
                }
            });
            Image small = new Image(display, 16, 16);
            DrawIconBlue drawIconBlue = new DrawIconBlue(small, display);
            drawIconBlue.drawOnImageAndRelease();

            Image large = new Image(display, 64, 64);
            DrawIconRed drawIconRed = new DrawIconRed(large, display);
            drawIconRed.drawOnImageAndRelease();

            // TODO it seems that Win10 only looks for the 16x16 icon, without considering other possibilities
            // when 16x16 is absent, will pick 64x64.
            shell.setImages(new Image[] { small, large });


            // center the window on primary monitor
            Monitor primary = display.getPrimaryMonitor();
            Rectangle bounds = primary.getBounds();
            Rectangle rect = shell.getBounds();
            int x = bounds.x + (bounds.width - rect.width) / 2;
            int y = bounds.y + (bounds.height - rect.height) / 2;
            shell.setLocation(x, y);

            shell.open();
            while (!shell.isDisposed()) { // loop to listen for mouse/keyboard event
                if (!display.readAndDispatch()) { // if is not focused, or if any inter-thread process is running
                    display.sleep();
                }
            }
            display.dispose();
        });

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
