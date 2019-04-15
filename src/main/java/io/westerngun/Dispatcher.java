package io.westerngun;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
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

            Image small = new Image(display, 16, 16);
            GC gc = new GC(small);
            gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
            gc.fillArc(0, 0, 16, 16, 45, 270);
            gc.dispose();

            Image large = new Image(display, 64, 64);
            GC gc2 = new GC(large);
            gc2.setBackground(display.getSystemColor(SWT.COLOR_RED));
            gc2.fillArc(0, 0, 64, 64, 45, 270);
            gc2.dispose();

            // TODO it seems that Win10 only looks for the 16x16 icon, without considering other possibilities
            // when 16x16 is absent, will pick 64x64.
            shell.setImages(new Image[] { large, small });


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
}
