import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import java.awt.*;

public class Application {

    private Display display;
    private Shell shell;

    public void createWindow(){

         display = new Display();
         shell = new Shell(display);
        shell.setSize(700, 600);
        shell.setMinimumSize(700, 600);
        ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.WRAP | SWT.LEFT);
        ToolItem add = new ToolItem(toolBar, SWT.PUSH);
        add.setText("Добавить слово");
        add.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                showWindowForAddWords();
            }
        });
        ToolItem execize = new ToolItem(toolBar, SWT.PUSH);
        execize.setText("Тренировка");
        execize.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                showWindowForExecize();
            }
        });

        toolBar.pack();
        shell.open();
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }

    private void showWindowForAddWords(){
        Shell sh = new Shell(shell);
        sh.setSize(100,200);
        sh.setMinimumSize(100, 200);
        sh.setBackground(new Color(display, 255,1,1));
        sh.open();
    }


    private void showWindowForExecize(){

    }
}
