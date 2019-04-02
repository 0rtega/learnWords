import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class TrainDialog extends Dialog {

    private List<Word> wordsForTrain = new ArrayList<>();
    private List<Word> repeatedTrueWords = new ArrayList<>();
    private List<Word> repeatedFalseWords = new ArrayList<>();

    private boolean switchOn = false;

    public TrainDialog(Shell parentShell) {
        super(parentShell);
        createDialog();
    }



    private void createDialog(){
        wordsForTrain.addAll(WorkerWithBD.getWork().getListForTrain());

        Shell addS = getParent();
        addS.setMinimumSize(420, 220);
        addS.setSize(420, 220);
        addS.setLayout(new FormLayout());
        addS.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));


        Label l1 = new Label(addS, SWT.NONE);
        l1.setText("3");
        l1.setFont(new Font(addS.getDisplay(), "Arial", 0, 45));
        FormData fd_l1 = new FormData();
        fd_l1.top = new FormAttachment(0, 50);
        fd_l1.left = new FormAttachment(0, 200);
        l1.setLayoutData(fd_l1);

        Button tru = new Button(addS, SWT.NONE);
        tru.setText("Знаю");

        FormData fd_tru = new FormData();
        fd_tru.top = new FormAttachment(l1, 20);
        fd_tru.left = new FormAttachment(0, 20);
        tru.setLayoutData(fd_tru);

        tru.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                switchOn = true;
                Word w = (Word)l1.getData();
                w.levelKnow++;
                repeatedTrueWords.add(w);
            }
        });

        Button fal = new Button(addS, SWT.NONE);
        fal.setText("Не знаю");
        FormData fd_fal = new FormData();
        fd_fal.top = new FormAttachment(l1, 20);
        fd_fal.left = new FormAttachment(tru, 70);
        fal.setLayoutData(fd_fal);

        fal.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                switchOn = true;
                Word w = (Word)l1.getData();
                repeatedFalseWords.add(w);
            }
        });



        addS.open();
        new Thread(new Runnable() {
            @Override
            public void run() {

                int t = 2;
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int e = t--;
                    addS.getDisplay().getDefault().syncExec(new Runnable() {
                        public void run() {
                            l1.setText(e + "");
                        }
                    });
                    if(t == -1){
                        break;
                    }
                }
                df:
                for(Word w : wordsForTrain){
                    l1.setText(w.english);
                    l1.setData(w);
                    int r = 0;
                    while (r < 4000){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        r+=100;
                        if(switchOn){
                            switchOn = false;
                            continue df;
                        }
                    }

                }

                df:
                for(Word w : repeatedFalseWords){
                    l1.setText(w.english);
                    l1.setData(w);
                    int r = 0;
                    while (r < 4000){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        r+=100;
                        if(switchOn){
                            switchOn = false;
                            continue df;
                        }
                    }
                }


            }
        }).start();
    }
}
