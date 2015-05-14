/**
 * Created by Bersik on 16.03.2015.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public final class Main {

    private Image img;

    private ArrayList<Point> points = new ArrayList<Point>();

    private int state = 0;
    private boolean locked = false;

    private void clear(){
        points = new ArrayList<Point>();
        locked = false;
        img.clear();
    }

    public Main(){
        int sizeWidth = 1000;
        int sizeHeight = 660;
        Form form = new Form(sizeWidth, sizeHeight);
        img = new Image(sizeWidth -200, sizeHeight -29);

        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                img.paintNextSmooth(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        };

        img.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case 3:
                        img.fillRecursiveMethod(e.getX(), e.getY());
                        break;
                    case 4:
                        img.fillMethod3(e.getX(),e.getY());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                switch (state) {
                    case 0:
                        if (!locked && ((e.getButton() == MouseEvent.BUTTON1) ||(e.getButton() == MouseEvent.BUTTON3))) {
                            int x = -1, y = -1;

                            if (e.getButton() == MouseEvent.BUTTON1) {
                                x = e.getX();
                                y = e.getY();
                            } else if (points.size() > 1) {
                                x = points.get(0).x;
                                y = points.get(0).y;
                                locked = true;
                            }
                            if (points.size() > 0) {
                                Point prev = points.get(points.size() - 1);
                                img.drawLine(prev.x, prev.y, x, y);
                            } else
                                img.paintPixel(e.getX(), e.getY());
                            if (e.getButton() == MouseEvent.BUTTON1)
                                points.add(new Point(x, y));

                        }
                        break;
                    case 1:
                        img.addMouseMotionListener(mouseMotionListener);
                        img.paintStart(e.getX(), e.getY());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (state == 1)
                    img.removeMouseMotionListener(mouseMotionListener);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        form.add(img);

        JButton buttonFillPolygon= new JButton("Заповнити многокутник");
        buttonFillPolygon.setBounds(sizeWidth - 190, 110, 160, 30);
        buttonFillPolygon.addActionListener(event -> {
            if (points.size()>2) {
                img.fillMethodPolygon(points);
                img.drawLines(points);
            }
        });
        form.add(buttonFillPolygon);

        JRadioButton buttonPolygon = new JRadioButton("Многокутник");
        buttonPolygon.setBounds(sizeWidth - 180, 30, 140, 30);
        buttonPolygon.addItemListener(e -> {
            int b_state = e.getStateChange();
            if (b_state == ItemEvent.SELECTED) {
                clear();
                state = 0;
                buttonFillPolygon.setEnabled(true);
            }
        });
        buttonPolygon.setSelected(true);
        form.add(buttonPolygon);

        JRadioButton buttonSmooth = new JRadioButton("Плавна");
        buttonSmooth.setBounds(sizeWidth - 180, 60, 140, 30);
        buttonSmooth.addItemListener(e -> {
            int b_state = e.getStateChange();
            if (b_state == ItemEvent.SELECTED) {
                state = 1;
                buttonFillPolygon.setEnabled(false);
            }
        });
        form.add(buttonSmooth);



        JRadioButton buttonFill2= new JRadioButton("Заповнення 2");
        buttonFill2.setMnemonic(KeyEvent.VK_4);
        buttonFill2.setBounds(sizeWidth - 180, 140, 140, 30);
        buttonFill2.addItemListener(e -> {
            int b_state = e.getStateChange();
            if (b_state == ItemEvent.SELECTED) {
                state = 3;
                buttonFillPolygon.setEnabled(false);
            }
        });
        form.add(buttonFill2);

        JRadioButton buttonFill3= new JRadioButton("Заповнення 3");
        buttonFill3.setMnemonic(KeyEvent.VK_5);
        buttonFill3.setBounds(sizeWidth - 180, 170, 140, 30);
        buttonFill3.addItemListener(e -> {
            int b_state = e.getStateChange();
            if (b_state == ItemEvent.SELECTED) {
                state = 4;
                buttonFillPolygon.setEnabled(false);
            }
        });
        form.add(buttonFill3);

        ButtonGroup group = new ButtonGroup();
        group.add(buttonPolygon);
        group.add(buttonSmooth);
        group.add(buttonFill2);
        group.add(buttonFill3);

        JPanel colorPanel = new JPanel();
        colorPanel.setBounds(sizeWidth - 60, sizeHeight - 135, 20, 20);
        colorPanel.setBackground(Color.black);
        form.add(colorPanel);

        final JButton selectColor = new JButton("Вибрати колір");
        selectColor.setBounds(sizeWidth - 180, sizeHeight - 140, 110, 30);
        selectColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Виберіть колір", Color.black);
            if (color != null){
                colorPanel.setBackground(color);
                img.setColor(color);
            }
        });
        form.add(selectColor);


        final JButton clear = new JButton("Очистити");
        clear.setBounds(sizeWidth - 180, sizeHeight - 80, 140, 30);
        clear.addActionListener(event -> clear());
        form.add(clear);

        clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new  Main();
        });
    }
}
