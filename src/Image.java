
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class Image extends JPanel {
    public static int cx,cy;
    private BufferedImage imag;
    private Graphics2D graphics;
    private int height;
    private int width;
    private Color color;
    private int prevX,prevY;

    public void setColor(Color color){
        this.color = color;
        graphics.setColor(color);
    }

    public Image(int width, int height) {
        super();
        this.width=width;
        this.height=height;
        setBounds(0, 0, width, height);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = imag.createGraphics();
        color = Color.BLACK;
        clear();
        Filling.setParameters(imag, width, height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imag, 0, 0, this);
    }

    public void clear() {
        graphics.setColor(Color.white);
        graphics.fillRect(-cx, -cy, width, height);
        graphics.setColor(color);
        prevX=-1;
        prevY=-1;
        this.repaint();
    }

    public void paintStart(int x,int y){
        prevX=x;
        prevY=y;
        this.paintPixel(x, y);
        this.repaint();
    }

    public void paintNextSmooth(int x,int y){
        graphics.drawLine(prevX,prevY,x,y);
        prevX=x;
        prevY =y;
        this.repaint();
    }

    public void drawLine(int x1, int y1, int x2, int y2){
        graphics.drawLine(x1, y1, x2, y2);
        this.repaint();
    }

    public void paintPixel(int x,int y) {
        imag.setRGB(x, y, color.getRGB());
        this.repaint();
    }

    public void fillMethodPolygon(ArrayList<Point> points){
        Filling.anglesFill(points,color);
        this.repaint();
    }

    public void fillRecursiveMethod(int x,int y){
        if (imag.getRGB(x, y) != color.getRGB())
            Filling.QueueFilling1(x, y, imag.getRGB(x, y), color.getRGB());
        this.repaint();
    }

    public void fillMethod3(int x,int y){
        if (imag.getRGB(x, y) != color.getRGB())
            Filling.QueueFilling2(x, y, imag.getRGB(x, y), color.getRGB());
        this.repaint();
    }


    public void drawLines(ArrayList<Point> points) {
        for(int i=0;i<points.size()-1;i++)
            drawLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y);
        drawLine(points.get(points.size()-1).x,points.get(points.size()-1).y,points.get(0).x,points.get(0).y);
    }
}