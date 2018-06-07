public class Welt
{
    static Feld[][] felder;
    int yn;
    int xn;
    static int[] haefenArray, felderArray, plaettchenArray;
    static int plaettchenI, felderI;

    public Welt(int basis)
    {
        yn = basis * 2 + 1;
        xn = yn;
        felder = new Feld[yn][xn];
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j] = new Feld(j, i);
            }
        }
        insel(basis);
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j].ecken();
            }
        }
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j].kanten();
            }
        }
        //Client.senden(hafenStapelZahlen());
        haefen(haefenArray);
    }

    public void dunkel()
    {
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j].hell = false;
                felder[i][j].refreshBild();
            }
        }
    }

    public void insel(int basis)
    {
        for (int i = 0; i < basis; i++)
        {
            for (int j = basis - i; j < 2 * basis; j++)
            {
                felder[i + 1][j].land();
            }
        }
        for (int i = 0; i < basis - 1; i++)
        {
            for (int j = 1; j < 2 * (basis - 1) - i + 1; j++)
            {
                felder[basis + i + 1][j].land();
            }
        }
    }

    public static String stapelZahlen(String typ)
    {
        String tmp = "";
        Stapel stapel = new Stapel(typ);
        while (stapel.oben != null)
        {
            if (!typ.equals("Zahl"))
            {
                tmp = tmp + stapel.randomZahl();
            } else
            {
                tmp = tmp + Integer.toHexString(stapel.randomZahl());
            }
        }
        return tmp;
    }

    static void haefen(int[] a)
    {
        kZH(4, 0, 4, a[0]);
        kZH(5, 1, 2, a[1]);
        kZH(5, 3, 3, a[2]);
        kZH(3, 5, 4, a[3]);
        kZH(0, 5, 2, a[4]);
        kZH(1, 2, 3, a[5]);
        kZH(2, 1, 3, a[6]);
        kZH(5, 2, 2, a[7]);
        kZH(2, 5, 4, a[8]);
    }

    static void kZH(int x, int y, int pos, int typ)
    {
        felder[y][x].kanten[pos].zuHafen(typ);
    }

    static void addStrasse(int x, int y, int pos, int spieler)
    {
        Kante kante = felder[y][x].kanten[pos];
        kante.spieler = Main.spieler[spieler];
        Main.spieler[spieler].anzahlStrassen++;
        Main.fruehsiedlung = true;
        kante.aktBild();
        Main.bildschirm.eckpanel.weg(kante);
        if (Main.frueh)
        {
            Spielerpanel.akt();
        }
    }

    static void addSiedlung(int x, int y, int pos, int spieler)
    {
        Ecke ecke = felder[y][x].ecken[pos];
        ecke.addSiedlung(Main.spieler[spieler]);
        Main.spieler[spieler].anzahlSiedlungen++;
        if(Main.frueh)
        {
            Main.anderePanelAkt();
        }
    }

    static void zuStadt(int x, int y, int pos, int spieler)
    {
        Ecke ecke = felder[y][x].ecken[pos];
        Main.spieler[spieler].anzahlSiedlungen--;
        Main.spieler[spieler].anzahlStaedte++;
        ecke.zuStadt();
    }
}