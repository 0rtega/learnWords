import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainDialog extends Dialog {

    private List<Word> wordsForTrain = new ArrayList<>();
    private List<Word> repeatedFalseWords = new ArrayList<>();

    private boolean switchOn = false;
    private  Shell addS;
    private Label l1;
    private  Button tru, fal;
    public TrainDialog(Shell parentShell) {
        super(parentShell);
        createDialog();
    }



    private void createDialog(){
        wordsForTrain.addAll(WorkerWithBD.getWork().getListForTrain());



         addS = getParent();
        addS.setMinimumSize(420, 220);
        addS.setSize(420, 220);
        addS.setLayout(new FormLayout());
        addS.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));


        l1 = new Label(addS, SWT.NONE);
        l1.setText("3");
        l1.setFont(new Font(addS.getDisplay(), "Arial", 0, 45));
        FormData fd_l1 = new FormData();
        fd_l1.top = new FormAttachment(0, 50);
        fd_l1.left = new FormAttachment(0, 100);
        fd_l1.width = 250;
        l1.setLayoutData(fd_l1);

        tru = new Button(addS, SWT.NONE);
        tru.setText("Знаю");
        tru.setEnabled(false);
        FormData fd_tru = new FormData();
        fd_tru.top = new FormAttachment(l1, 20);
        fd_tru.left = new FormAttachment(0, 70);
        fd_tru.width = 130;
        tru.setLayoutData(fd_tru);

        tru.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                switchOn = true;
                Word w = (Word)l1.getData();
                w.levelKnow++;
                w.date = new Date();
            }
        });


        fal = new Button(addS, SWT.NONE);
        fal.setText("Не знаю");
        fal.setEnabled(false);
        FormData fd_fal = new FormData();
        fd_fal.top = new FormAttachment(l1, 20);
        fd_fal.left = new FormAttachment(tru, 50);
        fd_fal.width = 130;
        fal.setLayoutData(fd_fal);

        fal.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                switchOn = true;
                Word w = (Word)l1.getData();
                repeatedFalseWords.add(w);
            }
        });

        Display.getDefault().addListener(SWT.Close, new Listener() {
            @Override
            public void handleEvent(Event event) {
                WorkerWithBD.getWork().updateData(wordsForTrain);
            }
        });
        Display.getDefault().addFilter(SWT.KeyUp, event -> {
            if(event.keyCode == 16777219){
                switchOn = true;
                Word w = (Word)l1.getData();
                w.levelKnow++;
                w.date = new Date();
            }else if(event.keyCode == 16777220){
                switchOn = true;
                Word w = (Word)l1.getData();
                repeatedFalseWords.add(w);
            }
        });

        addS.open();
        if(wordsForTrain.size() == 0){
            l1.setText("Тренировка окончена");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            addS.close();
            return;
        }
        Thread thread = new Thread(() -> {
            preStart();
            if(!addS.isDisposed()) {
                firstRound();
                secondRound();
                Display.getDefault().syncExec(() -> {
                    tru.setEnabled(false);
                    fal.setEnabled(false);
                    l1.setText("Конец");
                });

                WorkerWithBD.getWork().updateData(wordsForTrain);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }




    private void secondRound(){
        df:
        for (Word w : repeatedFalseWords) {
            Display.getDefault().syncExec(() -> {
                l1.setText(w.english);
                l1.setData(w);
            });

            int r = 0;
            while (r < 4000) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r += 100;
                if (switchOn) {
                    switchOn = false;
                    continue df;
                }
            }
            w.levelKnow--;
        }
    }

    private void firstRound(){
        Display.getDefault().syncExec(() -> {
            tru.setEnabled(true);
            fal.setEnabled(true);
        });
        df:
        for (Word w : wordsForTrain) {
            Display.getDefault().syncExec(() -> {
                l1.setText(w.english);
                l1.setData(w);
            });

            int r = 0;
            while (r < 4000) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r += 100;
                if (switchOn) {
                    switchOn = false;
                    continue df;
                }
            }
            repeatedFalseWords.add(w);
        }
    }

    private void preStart(){
        int t = 2;
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int e = t--;
            Display.getDefault().syncExec(() -> l1.setText(e + ""));
            if(t == -1){
                break;
            }
        }
    }
}
