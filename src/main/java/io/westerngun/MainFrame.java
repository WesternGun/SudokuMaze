package io.westerngun;

import com.sun.xml.internal.bind.v2.TODO;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class MainFrame implements Runnable {
    private Display display;
    private Shell shell;

    public void run() {
        display = new Display(); // perfectly supports two monitors(can cross-monitor)
        shell = new Shell(display, SWT.SHELL_TRIM); // TITLE | CLOSE | MIN | MAX | RESIZE
        // title
        shell.setText("Sudoku Maze by WesternGun");
        // mouse focus
        shell.setCapture(false); // if "true", cursor will be "halting" in the app window in Win10
        // min size(cannot be shrinked to less)
        shell.setMinimumSize(new Point(500, 500));
        // actuall size
        shell.setSize(new Point(700, 700));
        // maximized
        shell.setMaximized(false);
        // alpha: transparency. 0: invisible. 255: full
        shell.setAlpha(255);

        // fill components
        fillInShell();
        // always on top
        toggleAlwaysOnTop(true);
        // set icons
        setIcons();
        // centralize window in primary monitor
        centralize();

        // at last, show window
        shell.open();
        // wait while is not closed
        while (!shell.isDisposed()) { // loop to listen for mouse/keyboard event
            if (!display.readAndDispatch()) { // if is not focused, or if any inter-thread process is running
                display.sleep();
            }
        }
        display.dispose(); // when closed, also dispose all children
    }

    private void toggleAlwaysOnTop(boolean isOnTop){
        long handle = shell.handle;
        Point location = shell.getLocation();
        Point dimension = shell.getSize();
        OS.SetWindowPos(handle, isOnTop ? OS.HWND_TOPMOST : OS.HWND_NOTOPMOST, location.x, location.y, dimension.x, dimension.y, 0);
        // TODO explore if we can make this on top of ANY window, like that of DirectX(games)
    }

    private void setIcons() {

        Image small = new Image(display, 16, 16);
        DrawIconBlue drawIconBlue = new DrawIconBlue(small, display);
        drawIconBlue.drawOnImageAndRelease();

        Image large = new Image(display, 64, 64);
        DrawIconRed drawIconRed = new DrawIconRed(large, display);
        drawIconRed.drawOnImageAndRelease();

        /* TODO it seems that Win10 gives 16x16 icon priority when larger and smaller icons coexist.
           Only when 16x16 is absent, will pick 64x64. */
        shell.setImages(new Image[] {large, small});
    }

    private void centralize() {
        // center the window on primary monitor
        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);
    }

    private void fillInShell() {
        shell.setLayout(new GridLayout()); // initial layout to fill composite in
        final Composite parent = new Composite(shell, SWT.NONE);
        parent.setLayoutData(new GridData(GridData.FILL_BOTH));
        final StackLayout layout = new StackLayout();
        parent.setLayout(layout);
        final Button[] bArray = new Button[10];
        for (int i = 0; i < 10; i++) {
            bArray[i] = new Button(parent, SWT.PUSH);
            bArray[i].setText("Button "+i);
        }
        layout.topControl = bArray[0];

        Button b = new Button(shell, SWT.PUSH);
        b.setText("Show Next Button");
        final int[] index = new int[1];
        b.addListener(SWT.Selection, e -> {
            index[0] = (index[0] + 1) % 10;
            layout.topControl = bArray[index[0]];
            parent.layout();
        });

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
