import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Ecke 0 ist oben links, danach im Uhrzeigersinn
 * 
 * 
 *     1
 *    / \
 *   /   \
 *  /     \
 * 0       2
 * |       |
 * |       |1.154
 * |       |
 * 5       3
 *  \     /
 *   \   /
 *    \ /
 *     4
 *     
 * Felder gehen von links nach rechts
 */
public class Ecke extends RectangleImage
{
    Feld[] felder = new Feld[3];
    Kante[] kanten = new Kante[3];
    static Image leer = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Siedlungen/leer.gif"), Bildschirm.eckeb, Bildschirm.eckeh, false);
    static Image[][] siedlungen = new Image[4][2];
    Siedlung siedlung;
    JButton button;
    Feld feld;
    int pos;
    public Ecke(Feld feld, int pos)
    {
        super(leer, 0, 0);
        this.pos = pos;
        this.feld = feld;
        if(siedlungen[0][0] == null)
        {
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 2; j++)
                {
                    //System.out.println("Bilder/Siedlungen/"+i+"_"+j+".gif");
                    siedlungen[i][j] = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Siedlungen/"+i+"_"+j+".gif"), Bildschirm.eckeb, Bildschirm.eckeh, false);
                }
            }
        }

        addFelder(feld, pos);
        if((felder[0] != null && felder[1] != null && felder[2] != null)&&!(felder[0].art == 6 && felder[1].art == 6 && felder[2].art == 6))
        {
            double xr = 0;
            double yr = 0;
            if(pos == 1 || pos == 4)
            {
                xr = 0.5;
            }
            else if(pos == 2 || pos == 3)
            {
                xr = 1;
            }
            if (pos == 0 || pos == 2)
            {
                yr = 0.25*1.154;
            }
            else if (pos == 5 || pos == 3)
            {
                yr = 0.75*1.154;
            }
            else if(pos == 4)
            {
                yr = 1;
            }
            bewegen((feld.rechteck.getX()+xr*Bildschirm.feldb)-0.5*Bildschirm.eckeb, (feld.rechteck.getY()+yr*Bildschirm.feldh)-0.5*Bildschirm.eckeh);
            Bildschirm.grafikobjekte.add(this);

            button = new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            Main.bildschirm.add(button);
            button.setBounds((int)rechteck.getX(), (int)rechteck.getY(), (int)rechteck.getWidth(), (int)rechteck.getHeight());
            Ecke tmp = this;
            button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        Main.welt.dunkel();
                        for(int i = 0; i < 3; i++)
                        {
                            if(felder[i]!= null)
                            {
                                felder[i].hell=true;
                                felder[i].refreshBild();
                            }
                        }
                        Main.bildschirm.eckpanel.leerEcke(tmp);
                    }
                });
        }

    }

    public void addSiedlung(Spieler spieler)
    {
        if(siedlung == null)
        {
            siedlung = new Siedlung(this, spieler);
        }
        else
        {
            System.out.println("Hier existiert schon eine Siedlung!");
        }
        aktBild();
    }

    public boolean keinNachbar()
    {
        for(int i = 0; i < 3; i++)
        {
            if(kanten[i] != null && kanten[i].gegen(this).siedlung != null)
            {
                return false;
            }
        }
        return true;
    }

    public void addKanten(Kante kante)
    {
        for(int i = 0; i < 3; i++)
        {
            if (kanten[i] == null)
            {
                kanten[i] = kante;
                return;
            }
        }
        System.err.println("Ecke.java; void addKanten(Kante kante): Alle Kanten voll!");
        throw new IllegalArgumentException("Ecke.java; void addKanten(Kante kante): Alle Kanten voll!");
    }

    public void zuStadt()
    {
        siedlung.zuStadt();
        aktBild();
    }

    public int[] ernte(int wuerfel)
    {
        int[] tmp = new int[7];
        for(int i = 0; i < 3; i++)
        {
            if(felder[i].wert == wuerfel && !felder[i].besetzt)
            {
                tmp[felder[i].art]++;
            }
        }
        return tmp;
    }

    /**
     * @return Gibt den Besitzer der zugehörigen Siedlung zurück. Gibt null zurück, wenn es keine Siedlung gibt.
     */
    Spieler getSpieler()
    {
        if(siedlung != null)
        {
            return siedlung.spieler;
        }
        else
        {
            return null;
        }
    }

    public void addFelder(Feld feld, int pos)
    {
        if(pos == 0)
        {
            felder[2] = feld;
            try
            {
                felder[1] = Welt.felder[feld.y-1][feld.x];
                felder[1].ecken[4] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            try
            {
                felder[0] = Welt.felder[feld.y][feld.x-1];
                felder[0].ecken[2] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
        }
        if(pos == 1)
        {
            felder[1] = feld;
            try
            {
                felder[2] = Welt.felder[feld.y-1][feld.x+1];
                felder[2].ecken[5] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            try
            {
                felder[0] = Welt.felder[feld.y-1][feld.x];
                felder[0].ecken[3] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
        }
        if(pos == 2)
        {
            felder[0] = feld;
            try
            {
                felder[1] = Welt.felder[feld.y-1][feld.x+1];
                felder[1].ecken[4] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            try
            {
                felder[2] = Welt.felder[feld.y][feld.x+1];
                felder[2].ecken[0] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
        }
        if(pos == 3)
        {
            felder[0] = feld;
            try
            {
                felder[1] = Welt.felder[feld.y+1][feld.x];
                felder[1].ecken[1] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            try
            {
                felder[2] = Welt.felder[feld.y][feld.x+1];
                felder[2].ecken[5] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
        }
        if(pos == 4)
        {
            felder[1] = feld;
            try
            {
                felder[0] = Welt.felder[feld.y+1][feld.x-1];
                felder[0].ecken[2] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            try
            {
                felder[2] = Welt.felder[feld.y+1][feld.x];
                felder[2].ecken[0] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
        }
        if(pos == 5)
        {
            felder[2] = feld;
            try
            {
                felder[1] = Welt.felder[feld.y+1][feld.x-1];
                felder[1].ecken[1] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            try
            {
                felder[0] = Welt.felder[feld.y][feld.x-1];
                felder[0].ecken[3] = this;
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
        }
    }

    public void aktBild()
    {
        if(siedlung == null)
        {
            img = leer;
        }
        else
        {
            img = siedlungen[siedlung.spieler.id][siedlung.art];
        }
    }

    public boolean nachbar(Spieler spieler)
    {
        for(int i = 0; i < 3; i++)
        {
            if(kanten[i].spieler == spieler)
            {
                return true;
            }
        }
        return false;
    }

    int getKante(Kante kante){
        for(int i = 0; i < 3; i++)
        {
            if(kanten[i] == kante)
            {
                return i;
            }
        }
        System.err.println("Ecke.java; int getKante(Kante kante): kante nicht gefunden.");
        throw new IllegalArgumentException("Ecke.java; int getKante(Kante kante): kante nicht gefunden.");
    }
}