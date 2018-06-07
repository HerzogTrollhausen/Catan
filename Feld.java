import java.awt.Image;
/**
 * Ecke 0 ist oben links, danach im Uhrzeigersinn
 * Entsprechende Kante ist immer im Uhrzeigersinn der Ecke.
 * 
 * 
 *     1
 *    / \
 *   0   1
 *  /     \
 * 0       2
 * |       |
 * 5       2
 * |       |
 * 5       3
 *  \     /
 *   4   3
 *    \ /
 *     4
 *     
 * Felder gehen von links nach rechts
 */
public class Feld extends RectangleImage
{
    static Image holz = lade("Holz");//FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Wald-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
    static Image holz_hell = lade("Holz_hell");//FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Wald-hex_hell.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
    //static Image meer = lade("Meer");
    static Image meer = null;
    //static Image meer_hell = lade("Meer_hell");
    static Image meer_hell = null;
    static Image wueste = lade("Wueste");
    static Image wueste_hell = lade("Wueste_hell");
    static Image schaf = lade("Schaf");
    static Image schaf_hell = lade("Schaf_hell");
    static Image lehm = lade("Lehm");
    static Image lehm_hell = lade ("Lehm_hell");
    static Image erz = lade("Erz");
    static Image erz_hell = lade("Erz_hell");
    static Image weizen = lade("Weizen");
    static Image weizen_hell = lade("Weizen_hell");
    //static Stapel feldStapel = new Stapel("Feld");
    //static Stapel zahlStapel = new Stapel("Zahl");
    boolean besetzt;
    Ecke[] ecken;
    Kante[] kanten;
    int wert;
    int x;
    int y;
    int art = 6; // 0 Holz, 1 Lehm, 2 Schaf, 3 Weizen, 4 Erz, 5 Wueste, 6 Meer
    boolean hell;
    public Feld(double x, int y)
    {
        super(FileManager.createResizedCopy(holz, Bildschirm.feldb, Bildschirm.feldh, false), Bildschirm.ox+Bildschirm.feldb*x+y*0.5*Bildschirm.feldb, Bildschirm.oy+y*(int)(Bildschirm.feldh*0.75));
        //super(lade(art), Bildschirm.ox+Bildschirm.feldb*x+y*0.5*Bildschirm.feldb, Bildschirm.oy+y*(int)(Bildschirm.feldh*0.75));
        this.x = (int)x;
        this.y = y;
        ecken = new Ecke[6];
        kanten = new Kante[6];
        refreshBild();
    }

    public void refreshBild()
    {
        img = geladen(art+(hell?"_hell":""));
    }

    public static Image lade(String name)
    {
        return FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/"+name+"-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
    }

    public static Image geladen(String name)
    {
        switch(name)
        {
            case "0": 
            return holz;
            case "6":
            return meer;
            case "5":
            return wueste;
            case "2":
            return schaf;
            case "1":
            return lehm;
            case "4": 
            return erz;
            case "3":
            return weizen;
            case "0_hell":
            return holz_hell;
            case "6_hell":
            return meer_hell;
            case "5_hell":
            return wueste_hell;
            case "2_hell":
            return schaf_hell;
            case "1_hell":
            return lehm_hell;
            case "4_hell":
            return erz_hell;
            case "3_hell":
            return weizen_hell;
            default: 
            System.out.println("Im Switchzeug is was falsch: "+name);
            return meer;
        }
    }

    public static boolean gerade(int z)
    {
        return (double)z/2==(int)(z/2);
    }

    public void ecken()
    {
        for(int i = 0; i < 6; i++)
        {
            if(ecken[i] == null)
            {
                ecken[i] = new Ecke(this, i);
            }
        }
    }

    public void kanten()
    {
        for(int i = 0; i < 6; i++)
        {
            if(kanten[i] == null)
            {
                kanten[i] = new Kante(Kante.leer, this, i);
            }
        }
    }

    public void land()
    {
        art = Welt.felderArray[Welt.felderI];
        Welt.felderI++;
        if(art != 6 && art != 5)
        {
            wert = Welt.plaettchenArray[Welt.plaettchenI];
            Welt.plaettchenI++;
            Bildschirm.grafikobjekte.add(new Zahlplatte(this, wert, (rechteck.x+(rechteck.width/2))-(int)(0.5*(Bildschirm.feldb/Bildschirm.plattenr)), rechteck.y+rechteck.height/2-(int)(0.5*Bildschirm.feldh/Bildschirm.plattenr)));
        }
        refreshBild();
    }

    public static int weiter(int pos, int hoch)
    {
        for(int i = 0; i < hoch; i++)
        {
            if(pos < 5)
            {
                pos++;
            }
            else
            {
                pos = 0;
            }
        }
        return pos;
    }

    /**
     * Gibt den Index einer Kante im Kantenarray aus
     * @param kante
     * @return
     */
    int getKante(Kante kante){
        for(int i = 0; i < 6; i++)
        {
            if(kanten[i] == kante)
            {
                return i;
            }
        }
        System.err.println("Feld.java; int getKante(Kante kante): kante nicht gefunden.");
        throw new IllegalArgumentException("Feld.java; int getKante(Kante kante): kante nicht gefunden.");
    }

    void ausrauben()
    {
        for(int i = 0; i < 6; i++)
        {
            if(ecken[i].getSpieler() != null && ecken[i].getSpieler() != Main.spieler())
            {
                int rand = (int)(Math.random()*ecken[i].getSpieler().inv.anzahlAnRohstoffen());
                int[] r = ecken[i].getSpieler().inv.rohstoffe;
                int untergrenze = 0;
                for(int j = 0; j < 5; j++)
                {
                    if(rand >= untergrenze && rand <= untergrenze-1+r[j])
                    {
                        r[j]--;
                        Main.spieler().inv.rohstoffe[j]++;
                        new Fehler("Du hast ein "+Inventar.name(j)+" erbeutet!", "Raubzug");
                        Main.anderePanelAkt();
                        return;
                    }
                    else
                    {
                        untergrenze += r[j];
                    }
                }
            }
        }
    }
}