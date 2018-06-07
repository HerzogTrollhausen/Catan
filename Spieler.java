import java.awt.Color;
import java.util.ArrayList;

public class Spieler
{
    Inventar inv;
    ArrayList<Siedlung> siedlungen = new ArrayList<Siedlung>();
    Color farbe;
    Inventar letzteErnte;
    byte id;//0 == rot, 1 == blau, 2 == weiß, 3 == orange
    int anzahlSiedlungen, anzahlStaedte, anzahlStrassen, anzahlGelegteRitter;
    public int rittermacht = 0;
    public int handelsstrasse;

    public Spieler(byte id)
    {
        this.id = id;
        switch (id)
        {
            case 0:
                farbe = new Color(255, 0, 0);
                break;
            case 1:
                farbe = new Color(0, 0, 255);
                break;
            case 2:
                farbe = new Color(255, 255, 255);
                break;
            case 3:
                farbe = new Color(255, 140, 0);
        }
        inv = new Inventar(5);
    }

    public void ernte(int wuerfel)
    {
        letzteErnte = new Inventar();
        for (int i = 0; i < siedlungen.size(); i++)
        {
            int[] tmp = siedlungen.get(i).ernte(wuerfel);
            inv.hinzu(tmp);
            letzteErnte.hinzu(tmp);
        }
    }

    public int anzahlEntwicklungskarten(int typ)
    {
        return inv.anzahlEntwicklungskarten(typ);
    }

    int randomRohstoff()
    {
        int rand = (int) (Math.random() * inv.anzahlAnRohstoffen());
        int[] r = inv.rohstoffe;
        int untergrenze = 0;
        for (int j = 0; j < 5; j++)
        {
            if (rand >= untergrenze && rand <= untergrenze - 1 + r[j])
            {
                return j;
            }
            else
            {
                untergrenze += r[j];
            }
        }
        return -1;
    }

    public int siegPunkte()
    {
        return anzahlSiedlungen+2*anzahlStaedte+anzahlEntwicklungskarten(1)+rittermacht+handelsstrasse;
    }
}