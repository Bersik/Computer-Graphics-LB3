import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Filling
{
    private static BufferedImage image;
    private static int width;
    private static int height;

    public static void anglesFill(ArrayList<Point> figure,Color colorC)
    {
        int color = colorC.getRGB();
        int xMin = figure.get(0).x;
        int xMax = figure.get(0).x;
        int yMin = figure.get(0).y;
        int yMax = figure.get(0).y;
        for (int i = 1; i < figure.size(); i++)
        {
            if (figure.get(i).x < xMin)
                xMin = figure.get(i).x;
            else
            if (xMax < figure.get(i).x)
                xMax = figure.get(i).x;
            if (figure.get(i).y < yMin)
                yMin = figure.get(i).y;
            else
            if (yMax < figure.get(i).y)
                yMax = figure.get(i).y;
        }

        for (int x = xMin; x <= xMax; x++)
            for (int y = yMin; y <= yMax; y++){
                double angle = 0;
                for (int i = 0; i < figure.size() - 1; i++){
                    double angleI = Math.abs((Math.atan2(y - figure.get(i).y, x - figure.get(i).x) - Math.atan2(y - figure.get(i + 1).y, x - figure.get(i + 1).x)) * 180 / Math.PI);
                    if (angleI > 180)
                        angleI = 360 - angleI;
                    angle += angleI;
                }
                double angleJ = Math.abs((Math.atan2(y - figure.get(figure.size() - 1).y, x - figure.get(figure.size() - 1).x) - Math.atan2(y - figure.get(0).y, x - figure.get(0).x)) * 180 / Math.PI);
                if (angleJ > 180)
                    angleJ = 360 - angleJ;
                angle += angleJ;
                if (Math.abs(angle - 360) < 1)
                    image.setRGB(x, y,color);
            }
    }

    public static void  setParameters(BufferedImage image,int width,int height){
        Filling.width=width;
        Filling.height=height;
        Filling.image = image;
    }

    public static void QueueFilling1(int x0,int y0,int oldColor,int color){
        Queue<Point> Q = new LinkedList<>();
        Q.add(new Point(x0,y0));
        while(Q.peek()!=null){
            Point p = Q.poll();
            int x=p.x;
            int y=p.y;
            if (image.getRGB(x,y)==oldColor){
                int w=x;
                int e=x;
                while(w>=0 && image.getRGB(w,y)==oldColor) {
                    w--;
                }
                while(e<width && image.getRGB(e,y)==oldColor)
                    e++;
                for(int xi = w+1;xi<e;xi++){
                    image.setRGB(xi, y, color);
                    if (y>0 && image.getRGB(xi,y-1)==oldColor)
                        Q.add(new Point(xi,y-1));
                    if (y<height-1 && image.getRGB(xi,y+1)==oldColor)
                        Q.add(new Point(xi,y+1));
                }
            }
        }
    }


    public static void QueueFilling2(int x0,int y0,int oldColor,int color){
        Queue<Point> Q = new LinkedList<>();
        Q.add(new Point(x0,y0));
        while(Q.peek()!=null){
            Point p = Q.poll();
            int x=p.x;
            int y=p.y;
            if (image.getRGB(x,y)==oldColor){
                image.setRGB(x, y, color);
                if (x>0)
                    Q.add(new Point(x - 1, y));
                if (x<width-1)
                    Q.add(new Point(x+1,y));
                if (y>0)
                    Q.add(new Point(x,y-1));
                if (y<height-1)
                    Q.add(new Point(x,y+1));
            }

        }
    }
}