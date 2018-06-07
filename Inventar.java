public class Inventar
{
    int[] rohstoffe;//0 holz, 1 lehm, 2 schaf, 3 weizen, 4 erz
    int[] entwicklungskarten; // 0 Ritter, 1 Siegpunkt, 2 Straßenbau, 3 Erfindung, 4 Monopol

    public Inventar()
    {
        rohstoffe = new int[5];
        entwicklungskarten = new int[5];//0 holz, 1 lehm, 2 schaf, 3 weizen, 4 erz
    }

    public Inventar(int[] arg)
    {
        rohstoffe = arg;
        entwicklungskarten = new int[5];
    }

    public Inventar(int arg)//0 Straße, 1 Siedlung, 2 Stadt, 3 Entwicklung, 4 test
    {
        rohstoffe = new int[5];
        entwicklungskarten = new int[5];
        switch (arg)
        {
            case 1:
                rohstoffe[3] = 1;
                rohstoffe[2] = 1;
                rohstoffe[0] = 1;
                rohstoffe[1] = 1;
                break;
            case 0:
                rohstoffe[0] = 1;
                rohstoffe[1] = 1;
                break;
            case 2:
                rohstoffe[3] = 2;
                rohstoffe[4] = 3;
                break;
            case 3:
                rohstoffe[2] = 1;
                rohstoffe[3] = 1;
                rohstoffe[4] = 1;
                break;
            case 4:
                for (int i = 0; i < 5; i++)
                {
                    rohstoffe[i] = 10;
                }
                for (int i = 0; i < 5; i++)
                {
                    entwicklungskarten[i] = 10;
                }
                break;
            case 5:
                for (int i = 0; i < 5; i++)
                {
                    rohstoffe[i] = 1;
                }
                break;
        }
    }

    public boolean bezahlbar(Inventar ab)
    {
        for (int i = 0; i < 5; i++)
        {
            if (rohstoffe[i] < ab.rohstoffe[i])
            {
                return false;
            }
        }
        return true;
    }

    public void bezahl(int[] ab)
    {
        for (int i = 0; i < 5; i++)
        {
            rohstoffe[i] = rohstoffe[i] - ab[i];
        }
        Main.anderePanelAkt();
    }

    public void bezahl(Inventar ab)
    {
        for (int i = 0; i < 5; i++)
        {
            rohstoffe[i] = rohstoffe[i] - ab.rohstoffe[i];
        }
    }

    public static String name(int nr)
    {
        switch (nr)
        {
            case 0:
                return "Holz";
            case 1:
                return "Lehm";
            case 2:
                return "Schaf";
            case 3:
                return "Weizen";
            case 4:
                return "Erz";
            default:
                throw new IllegalArgumentException("" + nr);
                //return "Default in Inventar.name(int)";
        }
    }

    public int anzahlAnRohstoffen()
    {
        int tmp = 0;
        for (int i = 0; i < 5; i++)
        {
            tmp += rohstoffe[i];
        }
        return tmp;
    }

    public void hinzu(int[] zu) throws IllegalArgumentException
    {
        for (int i = 0; i < 5; i++)
        {
            if (rohstoffe[i] + zu[i] >= 0)
            {
                rohstoffe[i] = rohstoffe[i] + zu[i];
            } else
            {
                throw new IllegalArgumentException("Rohstoffe gehen unter null");
            }
        }
    }

    public int anzahlEntwicklungskarten(int typ)
    {
        return entwicklungskarten[typ];
    }
}