import java.awt.Image;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Kante extends RectangleImage
{
    static Image leer = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Kanten/Weg.gif"), Bildschirm.kanteb, Bildschirm.kanteh, false);
    static Image[] wege;
    Feld feld1, feld2;
    Ecke ecke1, ecke2;
    int pos;

    JButton button;

    Spieler spieler;

    boolean hafen = false;

    public Ecke gegen(Ecke ecke)
    {
        return ecke == ecke1 ? ecke2 : ecke == ecke2 ? ecke1 : null;
    }

    public Kante(Image img)
    {
        super(img, 0, 0);
    }

    public Kante(Image img, Feld feld, int pos)
    {
        super(img, 0, 0);

        /*if(wege == null)
        {
            wege = new Image[4];
            for(int i = 0; i < 4; i++)
            {
                wege[i] = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Kanten/Weg"+i+".gif"), Bildschirm.kanteb, Bildschirm.kanteh, false);
            }
        }*/

        feld1 = feld;
        this.pos = pos;
        ecke1 = feld1.ecken[pos];
        ecke2 = feld1.ecken[pos < 5 ? pos + 1 : 0];

        if (pos == 0 || pos == 3)
        {
            feld2 = feld1.ecken[pos].felder[1];
        } else if (pos == 1 || pos == 2)
        {
            feld2 = feld1.ecken[pos].felder[2];
        } else if (pos == 4 || pos == 5)
        {
            feld2 = feld1.ecken[pos].felder[0];
        }
        if (feld2 != null)
        {
            feld2.kanten[feld2.weiter(pos, 3)] = this;
        }

        ecke1.addKanten(this);
        ecke2.addKanten(this);

        if ((feld2 != null && feld1 != null) && !(feld1.art == 6 && feld2.art == 6))
        {
            double xr = 0;
            double yr = 0;
            if (pos == 0 || pos == 4)
            {
                xr = 0.25;
            } else if (pos == 1 || pos == 3)
            {
                xr = 0.75;
            } else if (pos == 2)
            {
                xr = 1;
            }
            if (pos == 0 || pos == 1)
            {
                yr = 0.125;
            } else if (pos == 4 || pos == 3)
            {
                yr = 0.875;
            } else if (pos == 2 || pos == 5)
            {
                yr = 0.5;
            }
            bewegen((feld.rechteck.getX() + xr * Bildschirm.feldb) - 0.5 * Bildschirm.eckeb, (feld.rechteck.getY() + yr * Bildschirm.feldh) - 0.5 * Bildschirm.eckeh);
            Bildschirm.grafikobjekte.add(this);

            buttonStuff();
        }
    }

    public void aktBild()
    {
        if (spieler == null)
        {
            img = leer;
        } else
        {
            img = wege[spieler.id];
        }

    }

    /**
     * @param gesucht Der gesuchte Spieler
     * @return Gibt zurück, ob der gesuchte Spieler eine Siedlung oder Stadt an einer angrenzenden Ecke hat.
     */
    public boolean nachbar(Spieler gesucht)
    {
        if (ecke1.siedlung != null && ecke1.siedlung.spieler == gesucht)
        {
            return true;
        } else if (ecke2.siedlung != null && ecke2.siedlung.spieler == gesucht)
        {
            return true;
        } else if (ecke1.nachbar(gesucht))
        {
            return true;
        } else return ecke2.nachbar(gesucht);
    }

    /**
     * @return Gibt den Besitzer der anliegenden Siedlung/Stadt zurück. Gibt es keinen Besitzer, gibt null zurück.
     */
    Spieler nachbar()
    {
        if (ecke1.getSpieler() != null)
        {
            return ecke1.getSpieler();
        } else return ecke2.getSpieler();
    }

    /**
     * Ersetzt diese Kante überall, wo sie referenziert wird, durch einen Hafen
     */
    public void zuHafen(int typ)
    {
        Hafen tmp = new Hafen(this, typ);
        tmp.feld1 = feld1;
        tmp.feld2 = feld2;
        tmp.ecke1 = ecke1;
        tmp.ecke2 = ecke2;
        tmp.pos = pos;
        tmp.rechteck.setBounds(rechteck.x, rechteck.y, tmp.rechteck.width, tmp.rechteck.height);
        Main.bildschirm.remove(button);
        tmp.buttonStuff();
        ecke1.kanten[ecke1.getKante(this)] = tmp;
        ecke2.kanten[ecke2.getKante(this)] = tmp;
        feld1.kanten[feld1.getKante(this)] = tmp;
        feld2.kanten[feld2.getKante(this)] = tmp;
        Main.bildschirm.replaceGrafikObjekt(this, tmp);
    }

    public void buttonStuff()
    {
        button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        Main.bildschirm.add(button);
        button.setBounds((int) rechteck.getX(), (int) rechteck.getY(), (int) rechteck.getWidth(), (int) rechteck.getHeight());

        Kante tmp = this;

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.welt.dunkel();
                if (feld1 != null)
                {
                    feld1.hell = true;
                    feld1.refreshBild();
                }
                if (feld2 != null)
                {
                    feld2.hell = true;
                    feld2.refreshBild();
                }
                Main.bildschirm.eckpanel.weg(tmp);
            }
        });
    }
}