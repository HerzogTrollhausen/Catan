import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Handel extends JPanel
{
    JFrame f;
    Handelzeile[] zeilen;
    JButton ja, nein;
    JPanel buttons;

    public Handel(int art) //0-4 spezial, 5 3:1, 6 4:1, 7 Erfindung
    {
        framezeug();
        zeilen = new Handelzeile[5];
        for (int i = 0; i < 5; i++)
        {
            zeilen[i] = new Handelzeile(i, 6, this);
            add(zeilen[i]);
        }
        ja = new JButton("Ok");
        ja.addActionListener(new ActionListener()
                             {
                                 public void actionPerformed(ActionEvent e)
                                 {
                                     try
                                     {
                                         if (isBankhandelErlaubt(art))
                                         {
                                             int[] s1a = new int[5];
                                             for (int i = 0; i < 5; i++)
                                             {
                                                 s1a[i] = zeilen[i].wert();
                                             }

                                             Main.spieler().inv.hinzu(s1a);

                                             Main.anderePanelAkt();
                                             f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                         } else
                                         {
                                             new Fehler("Kein passendes Handelsverhältnis");
                                         }
                                     } catch (IllegalArgumentException ex)
                                     {
                                         new Fehler("Nicht genug Rohstoffe");
                                     }

                                 }
                             }
        );
        nein = new JButton("Abbrechen");
        nein.addActionListener(new ActionListener()
                               {
                                   public void actionPerformed(ActionEvent e)
                                   {
                                       f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                   }
                               }
        );

        buttons = new JPanel();
        add(buttons);
        buttons.add(ja);
        buttons.add(nein);

        f.setVisible(true);
    }

    /**
     *
     * @param art Die Art des Bankhandels (0-4 spezial, 5 3:1, 6 4:1)
     * @return Ob die eingegebenen Werte das richtige Verhältnis für den angegebenen Bankhandel haben.
     */
    private boolean isBankhandelErlaubt(int art)
    {
        int konto = 0;
        int anzahl = 0;
        if(art == 5)
        {
            anzahl = 3;
        }
        else if(art == 6)
        {
            anzahl = 4;
        }
        else if(art == 7)
        {
            return isErfindungErlaubt();
        }
        else
        {
            return isRohstoffPortHandelErlaubt(art);
        }
        for (int i = 0; i < zeilen.length; i++)
        {
            if(zeilen[i].wert() < 0 && zeilen[i].wert() > anzahl*-1)
            {
                return false;
            }
            konto += zeilen[i].wert() >= 0 ? zeilen[i].wert() : zeilen[i].wert() / anzahl;
        }
        return konto == 0;
    }

    private boolean isErfindungErlaubt()
    {
        int konto = 0;
        for(int i = 0; i < zeilen.length; i++)
        {
            if(zeilen[i].wert() < 0)
            {
                return false;
            }
            else
            {
                konto += zeilen[i].wert();
            }
        }
        return konto == 2;
    }

    private boolean isRohstoffPortHandelErlaubt(int art)
    {
        int konto = 0;
        boolean festgelegt = false; //sobald true, darf sich portZuSonstigem nicht mehr ändern
        boolean portZuSonstigem = true; //true, wenn bei einem Schafhafen zwei Schafe in ein Holz umgewandelt werden
        for(int i = 0; i < zeilen.length; i++)
        {
            if(i != art)
            {
                if(portZuSonstigem)
                {
                    if(zeilen[i].wert() > 0)
                    {
                        konto += zeilen[i].wert();
                        festgelegt = true;
                    }
                    else if(zeilen[i].wert() < 0)
                    {
                        if(festgelegt)
                        {
                            System.out.println("!= art; portZuSonstigem, wert < 0, festgelegt");
                            return false;
                        }
                        else
                        {
                            portZuSonstigem = false;
                            festgelegt = true;
                            konto += zeilen[i].wert()/2;
                        }
                    }
                }
                else
                {
                    if(zeilen[i].wert() > 0)
                    {
                        System.out.println("!= art, !portZuSonstigem, wert > 0");
                        return false;
                    }
                    else
                    {
                        konto += zeilen[i].wert()/2;
                    }
                }
            }
            else
            {
                if(portZuSonstigem)
                {
                    if(zeilen[i].wert() > 0)
                    {
                        if(festgelegt)
                        {
                            System.out.println("==art, portZuSonstigem, wert > 0, festgelegt");
                            return false;
                        }
                        else
                        {
                            portZuSonstigem = false;
                            festgelegt = true;
                            konto += zeilen[i].wert();//2;

                        }
                    }
                    else if(zeilen[i].wert() < 0)
                    {
                        konto += zeilen[i].wert()/2;
                        festgelegt = true;
                    }
                }
                else
                {
                    if(zeilen[i].wert() < 0)
                    {
                        System.out.println("==art, !portZuSonstigem, wert < 0");
                        return false;
                    }
                    else
                    {
                        konto += zeilen[i].wert();
                    }
                }
            }
        }
        System.out.println(konto);
        return konto == 0;
    }

    int bezahl()
    {
        int tmp = 0;
        for (int i = 0; i < 5; i++)
        {
            if (zeilen[i].wert() < 0)
            {
                tmp = tmp - zeilen[i].wert();
            }
        }
        return tmp;
    }

    int bekomm()
    {
        int tmp = 0;
        for (int i = 0; i < 5; i++)
        {
            if (zeilen[i].wert() > 0)
            {
                tmp += zeilen[i].wert();
            }
        }
        return tmp;
    }

    public void framezeug()
    {
        f = new JFrame("Handel zwischen Spielern");
        f.setContentPane(this);
        f.setSize(350, 500);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public Handel(Spieler s1, Spieler s2)
    {
        framezeug();
        zeilen = new Handelzeile[5];
        for (int i = 0; i < 5; i++)
        {
            zeilen[i] = new Handelzeile(i, s1, s2);
            add(zeilen[i]);
        }
        ja = new JButton("Ok");
        ja.addActionListener(new ActionListener()
                             {
                                 public void actionPerformed(ActionEvent e)
                                 {
                                     int[] s1a = new int[5];
                                     int[] s2a = new int[5];
                                     for (int i = 0; i < 5; i++)
                                     {
                                         s1a[i] = zeilen[i].wert();
                                         s2a[i] = zeilen[i].wert() * -1;
                                     }
                                     s1.inv.hinzu(s1a);
                                     s2.inv.hinzu(s2a);
                                     Main.anderePanelAkt();
                                     f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                 }
                             }
        );

        nein = new JButton("Abbrechen");
        nein.addActionListener(new ActionListener()
                               {
                                   public void actionPerformed(ActionEvent e)
                                   {
                                       f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                   }
                               }
        );

        buttons = new JPanel();
        add(buttons);
        buttons.add(ja);
        buttons.add(nein);

        f.setVisible(true);
    }
}