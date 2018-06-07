import javax.swing.JComponent;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class RectangleImage// extends JComponent
{
    public Image img = null;
    public Rectangle rechteck = null;
    public Graphics2D g2;
    double virX;
    double virY;

    public RectangleImage(Image img, double x, int y)
    {
        if (img == null)
        {
            System.out.println("\"img\" ist null");
        } else
        {
            this.img = img;
        }
        this.rechteck = new Rectangle((int) x, y, img.getHeight(null), img.getWidth(null));
    }

    public void setSize(int width, int height)
    {
        rechteck.setSize(width, height);
    }

    public void draw(Graphics2D g2)
    {
        //System.out.println(g2);
        try
        {
            g2.drawImage(img, rechteck.x, rechteck.y, rechteck.height, rechteck.width, null);
            //System.out.println("Klappt");
        } catch (NullPointerException e)
        {
            System.out.println(e);
        }
    }

    public void bewegen(double x, double y)
    {
        virX = x;
        virY = y;
        this.rechteck.setBounds((int) virX, (int) virY, rechteck.width, rechteck.height);
    }

}