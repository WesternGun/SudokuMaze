package io.westerngun;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Dispatcher {
    public static void main(String[] args) {
        Runnable runnable = (() -> {
            Display display = new Display();
            Shell shell = new Shell(display);
            shell.setMinimumSize(new Point(500, 500));
            shell.setSize(new Point(700, 700));
            shell.setMaximized(false);
            shell.setCapture(false);
            shell.setText("Sudoku Maze by WesternGun");
            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        });

        runnable.run();
    }
}
