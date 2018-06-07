import javax.swing.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Zahlplatte extends RectangleImage
{
    Feld feld;
    String inhalt;
    Font font;
    Color color;
    JButton button;
    static Image kreis = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Kreis.gif"),
            (int)(Bildschirm.feldb/Bildschirm.plattenr), (int)(Bildschirm.feldh/Bildschirm.plattenr), false);
    static Image kreis_bandit = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Kreis_bandit.gif"),
            (int)(Bildschirm.feldb/Bildschirm.plattenr), (int)(Bildschirm.feldh/Bildschirm.plattenr), false);
    public Zahlplatte(Feld feld, int wert, int x, int y)
    {
        super(kreis, x, y);
        this.feld = feld;
        inhalt = ""+wert;
        if(wert == 6 || wert == 8)
        {
            font = new Font("Arial", Font.BOLD, 30);
            color = new Color(255, 0, 0);
        }
        else
        {
            font = new Font("Arial", Font.PLAIN, 25);
            color = new Color(0, 0, 0);
        }
        button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        Main.bildschirm.add(button);
        button.setBounds((int)rechteck.getX(), (int)rechteck.getY(), (int)rechteck.getWidth(), (int)rechteck.getHeight());
        Zahlplatte dies = this;
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(Bandit.amSetzen)
                {
                    Bandit.hinsetzen(feld);
                    if(Bandit.platte != null)
                    {
                        Bandit.platte.img = kreis;
                    }
                    Bandit.platte = dies;
                    img = kreis_bandit;
                }
            }
        });
    }

    @Override
    public void draw(Graphics2D g2)
    {
        try
        {
            if(inhalt != null)
            {
                g2.drawImage(img, rechteck.x, rechteck.y, rechteck.height, rechteck.width, null);
                g2.setFont(font);
                g2.setColor(color);
                g2.drawString(inhalt ,rechteck.x+rechteck.width/2-10, rechteck.y+rechteck.height/2+5);
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e);
        }
    }
}