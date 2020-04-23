import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainClass {
    JFrame frame;

    JSlider slider;

    JPanel contentTop;
    GridLayout layoutTop;

    JPanel contentLeft;
    JPanel contentRight;
    JPanel contentBottom;
    JPanel contentMain;

    GridLayout layoutLeftRight;
    GridLayout layoutBottom;

    JButton buttonStart;
    JButton buttonStop;

    JButton buttonPriorityUp1;
    JButton buttonPriorityDown1;

    JButton buttonPriorityUp2;
    JButton buttonPriorityDown2;

    MyThread thread1;
    MyThread thread2;

    ActionListener listener;

    public synchronized void setSliderValue(int x) {
        slider.setValue(x);
//        frame.repaint();
    }

    void initSlider() {
        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(100);
        slider.setValue(50);
    }

    void initButtons() {
        buttonStart = new JButton("Start");
        buttonStop = new JButton("Stop");

        buttonPriorityUp1 = new JButton("Increase priority for thread #1");
        buttonPriorityDown1 = new JButton("Decrease priority for thread #1");

        buttonPriorityUp2 = new JButton("Increase priority for thread #2");
        buttonPriorityDown2 = new JButton("Decrease priority for thread #2");

        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonPriorityUp1.setEnabled(false);
        buttonPriorityDown1.setEnabled(false);
        buttonPriorityUp2.setEnabled(false);
        buttonPriorityDown2.setEnabled(false);
    }

    void initLayouts() {
        layoutTop = new GridLayout(1, 1);
        layoutLeftRight = new GridLayout(3, 1);
        layoutBottom = new GridLayout(1, 2);
    }

    void initContent() {
        contentTop = new JPanel();

        contentTop.setLayout(layoutTop);

        contentLeft = new JPanel();
        contentRight = new JPanel();
        contentBottom = new JPanel();

        contentMain = new JPanel();

        contentLeft.setLayout(layoutLeftRight);
        contentRight.setLayout(layoutLeftRight);
        contentBottom.setLayout(layoutBottom);

        contentMain.setLayout(new GridLayout(2, 1));

        contentLeft.add(buttonPriorityUp1);
        contentLeft.add(buttonPriorityDown1);
        contentLeft.add(buttonStart);

        contentRight.add(buttonPriorityUp2);
        contentRight.add(buttonPriorityDown2);
        contentRight.add(buttonStop);

        contentTop.add(slider);

        contentBottom.add(contentLeft);
        contentBottom.add(contentRight);

        contentMain.add(contentTop);
        contentMain.add(contentBottom);
    }

    void initThreads() {
        thread1 = new MyThread(10, this);
        thread2 = new MyThread(90, this);
    }

    void initListener() {
        listener = e -> {
            if (e.getSource() == buttonStart) {

                initThreads();

                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
                buttonPriorityUp1.setEnabled(true);
                buttonPriorityDown1.setEnabled(true);
                buttonPriorityUp2.setEnabled(true);
                buttonPriorityDown2.setEnabled(true);

            } else if (e.getSource() == buttonStop) {

                thread1.setRunning(false);
                thread2.setRunning(false);

                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                buttonPriorityUp1.setEnabled(false);
                buttonPriorityDown1.setEnabled(false);
                buttonPriorityUp2.setEnabled(false);
                buttonPriorityDown2.setEnabled(false);

            } else if (e.getSource() == buttonPriorityUp1) {
                thread1.increasePriority();
            } else if (e.getSource() == buttonPriorityDown1) {
                thread1.decreasePriority();
            } else if (e.getSource() == buttonPriorityUp2) {
                thread2.increasePriority();
            } else if (e.getSource() == buttonPriorityDown2) {
                thread2.decreasePriority();
            } else {
                System.out.println("Unknown button");
            }
        };

        buttonStart.addActionListener(listener);
        buttonStop.addActionListener(listener);
        buttonPriorityUp1.addActionListener(listener);
        buttonPriorityDown1.addActionListener(listener);
        buttonPriorityUp2.addActionListener(listener);
        buttonPriorityDown2.addActionListener(listener);
    }

    void initFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentMain);
        frame.setSize(700, 300);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    public MainClass() {
        initSlider();
        initButtons();
        initLayouts();
        initContent();
        initListener();
        initFrame();
    }

    public static void main(String[] args) {
        MainClass mc = new MainClass();
    }

}
