public class Stapel
{
    Knoten oben;
    Knoten zeiger;

    public Stapel(String typ)
    {
        switch (typ)
        {
            case "Feld":
                for (int i = 0; i < 4; i++)
                {
                    add(0);
                    add(2);
                    add(3);
                    if (i != 3)
                    {
                        add(1);
                        add(4);
                    }
                }
                add(5);
                break;

            case "Zahl":
                for (int i = 3; i < 7; i++)
                {
                    add(i);
                    add(i);
                }
                for (int i = 8; i < 12; i++)
                {
                    add(i);
                    add(i);
                }
                add(2);
                add(12);
                break;

            case "Hafen":
                for (int i = 0; i < 9; i++)
                {
                    if (i < 6)
                    {
                        add(i);
                    } else
                        add(5);
                }
                break;

            case "Entwicklung":
                for (int i = 0; i < 14; i++)
                {
                    add(0);
                }
                for (int i = 0; i < 5; i++)
                {
                    add(1);
                }
                add(2);
                add(2);
                add(3);
                add(3);
                add(4);
                add(4);
                break;

            default:
                System.out.println("Falscher \"Typ\" im Stapel");
                throw new IllegalArgumentException("Falscher Typ im Stapel");
        }
    }

    public void add(Knoten neu)
    {
        neu.next = oben;
        oben = neu;
    }

    public void add(String inhalt)
    {
        add(new Knoten(inhalt));
    }

    public void add(int inhalt)
    {
        add(new Knoten(inhalt));
    }

    public int zaehl()
    {
        int i = 0;
        if (oben != null)
        {
            zeiger = oben;
            i = 1;
            while (zeiger.next != null)
            {
                zeiger = zeiger.next;
                i++;
            }
        }
        return i;
    }

    public Knoten get(int stelle, boolean loeschen) throws NullPointerException
    {
        Knoten tmp = null;
        if (oben != null)
        {
            if (stelle == 0)
            {
                tmp = oben;
                if (loeschen)
                {
                    oben = oben.next;
                }
            } else
            {
                zeiger = oben;
                for (int i = 0; i < stelle - 1; i++)
                {
                    zeiger = zeiger.next;
                }
                tmp = zeiger.next;
                if (loeschen)
                {
                    zeiger.next = zeiger.next.next;
                }
            }
        }
        return tmp;
    }

    public int randomFeld() throws NullPointerException
    {
        return get((int) (Math.random() * zaehl()), true).intInhalt;
    }

    public int randomZahl() throws NullPointerException
    {
        return get((int) (Math.random() * zaehl()), true).intInhalt;
    }
}