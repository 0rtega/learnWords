import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class AddNewWordsDialog extends Dialog {



    public AddNewWordsDialog(Shell parentShell) {
        super(parentShell);
        createDialog();
    }

    private void createDialog(){
        Shell addS = getParent();
        addS.setMinimumSize(420, 220);
        addS.setSize(420, 220);
        addS.setLayout(new FormLayout());
        addS.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));

        Label l1 = new Label(addS, SWT.NONE);
        l1.setText("Слово на русском: ");

        FormData fd_l1 = new FormData();
        fd_l1.top = new FormAttachment(0, 10);
        fd_l1.left = new FormAttachment(0, 10);
        l1.setLayoutData(fd_l1);

        Label l2 = new Label(addS, SWT.NONE);
        l2.setText("Слово на английском: ");
        FormData fd_l2 = new FormData();
        fd_l2.top = new FormAttachment(l1, 17);
        fd_l2.left = new FormAttachment(0, 10);
        l2.setLayoutData(fd_l2);


        Text textRu = new Text(addS, SWT.BORDER);

        FormData fd_textRu = new FormData();
        fd_textRu.top = new FormAttachment(0, 10);
        fd_textRu.left = new FormAttachment(l1, 70);
        fd_textRu.width = 130;
        textRu.setLayoutData(fd_textRu);


        Button removeWordRu = new Button(addS, SWT.NONE);
        removeWordRu.setText("X");
        removeWordRu.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                textRu.setText("");
            }
        });
        FormData fd_removeWordRu = new FormData();
        fd_removeWordRu.top = new FormAttachment(0, 8);
        fd_removeWordRu.left = new FormAttachment(textRu, 5);
        removeWordRu.setLayoutData(fd_removeWordRu);

        Text textEn = new Text(addS, SWT.BORDER);

        FormData fd_textEn = new FormData();
        fd_textEn.top = new FormAttachment(textRu, 10);
        fd_textEn.left = new FormAttachment(l2, 45);
        fd_textEn.width = 130;
        textEn.setLayoutData(fd_textEn);


        Button removeWordEn = new Button(addS, SWT.NONE);
        removeWordEn.setText("X");
        removeWordEn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                textEn.setText("");
            }
        });
        FormData fd_removeWordEn = new FormData();
        fd_removeWordEn.top = new FormAttachment(removeWordRu, 7);
        fd_removeWordEn.left = new FormAttachment(textEn, 5);
        removeWordEn.setLayoutData(fd_removeWordEn);

        Button add = new Button(addS, SWT.NONE);
        add.setText("Добавить");
        FormData fd_add = new FormData();
        fd_add.top = new FormAttachment(textEn, 50);
        fd_add.left = new FormAttachment(0, 115);
        fd_add.width = 130;
        add.setLayoutData(fd_add);
        add.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                WorkerWithBD.getWork().addWord(textRu.getText(), textEn.getText());
                textEn.setText("");
                textRu.setText("");
            }
        });

        Button cancel = new Button(addS, SWT.NONE);
        cancel.setText("Отмена");
        FormData fd_cancel = new FormData();
        fd_cancel.top = new FormAttachment(textEn, 50);
        fd_cancel.left = new FormAttachment(add, 10);
        fd_cancel.width = 130;
        cancel.setLayoutData(fd_cancel);
        cancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                addS.dispose();
            }
        });
        addS.open();
    }
}
