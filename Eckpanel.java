import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Eckpanel extends JPanel
{
    public JLabel desc, ex;
    public JButton button;
    String exString;

    public Eckpanel()
    {
        setBackground(new Color(200, 200, 200));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void weg(Kante kante)
    {
        removeAll();
        if (Main.spieler().id == Main.ich)
        {
            desc = new JLabel();
            button = new JButton("Neue Straße bauen");

            if (kante.spieler == null)
            {
                if (Main.spieler().anzahlStrassen < Main.maxStrassen)
                {
                    if (Main.frueh)
                    {
                        if (!Main.fruehsiedlung)
                        {
                            if (fruehVerfuegbar(kante))
                            {
                                desc.setText("Du kannst hier eine freie Straße bauen");
                                button.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        //Main.fruehsiedlung = true;
                                        Client.senden("d"+Main.spieler().id+""+kante.feld1.x+""+kante.feld1.y+""+kante.pos);
                                        Client.senden("h" + 0);
                                        //weg(kante);
                                        //Spielerpanel.akt();
                                    }
                                });
                            } else
                            {
                                desc.setText("Baue eine Straße an die letzte Siedlung!");
                                button.setEnabled(false);
                            }
                        } else
                        {
                            desc.setText("Baue erst eine Siedlung!");
                            button.setEnabled(false);
                        }
                    } else
                    {
                        if (kante.nachbar(Main.spieler()))
                        {
                            if (Main.strassenbauinprogress == 0)
                            {
                                if (Main.spieler().inv.bezahlbar(new Inventar(0)))
                                {
                                    desc.setText("Du kannst hier eine Straße bauen");
                                    button.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent e)
                                        {
                                            //TODO
                                            Client.senden("d"+Main.spieler().id+""+kante.feld1.x+""+kante.feld1.y+""+kante.pos);
                                            Client.senden("n"+Main.spieler().id+"1m1m0m0m0");
                                        }
                                    });
                                } else
                                {
                                    desc.setText("Du hast nicht genug Rohstoffe!");
                                    button.setEnabled(false);
                                }
                            } else
                            {
                                desc.setText("Du kannst noch " + (Main.strassenbauinprogress == 1 ? "eine" : Main.strassenbauinprogress) + " freie " + (Main.strassenbauinprogress == 1 ? "Straße" : "Straßen") + " bauen!");
                                button.addActionListener(new ActionListener()
                                {
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        kante.spieler = Main.spieler();
                                        Main.spieler().anzahlStrassen++;
                                        Main.strassenbauinprogress--;
                                        kante.aktBild();
                                        weg(kante);
                                        Spielerpanel.akt();
                                    }
                                });
                            }
                        } else
                        {
                            desc.setText("Du kannst nur an eigene Straßen und Siedlungen anbauen!");
                            button.setEnabled(false);
                        }
                    }
                } else
                {
                    desc.setText("Keine Straßen mehr übrig!");
                    button.setEnabled(false);
                }
            } else
            {
                if (kante.spieler == Main.spieler())
                {
                    desc.setText("Dies ist bereits deine Straße!");
                } else
                {
                    desc.setText("Diese Straße gehört jemand anderem.");
                }
                button.setEnabled(false);
            }
            if (kante.hafen)
            {
                Hafen hafenkante = (Hafen) kante;
                desc.setText("<html>An diesem Hafen " + Hafen.stringAusTyp(hafenkante.typ) + " gehandelt.<br>" + desc.getText() + "</html");
                JButton handelbutton = new JButton("Handel!");
                handelbutton.setEnabled(kante.nachbar(Main.spieler()));
                handelbutton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        new Handel(hafenkante.typ);
                    }
                });
                add(handelbutton);
            }

            add(desc);
            add(button);
        }
        revalidate();
    }

    public static boolean fruehVerfuegbar(Kante kante)
    {
        return kante.ecke1 == Main.letzteSiedlung || kante.ecke2 == Main.letzteSiedlung;
    }

    public void leerEcke(Ecke ecke)
    {
        exString = "";
        removeAll();
        if (ecke.siedlung != null)
        {
            desc = new JLabel(ecke.siedlung.descs[ecke.siedlung.art]);
        } else
        {
            desc = new JLabel("Eine leere Fläche");
        }
        add(desc);
        if (Main.spieler().id == Main.ich)
        {
            button = new JButton("Neue Siedlung bauen!");

            if (ecke.siedlung != null && ecke.siedlung.spieler == Main.spieler())
            {
                if (Main.spieler().anzahlStaedte < Main.maxStaedte)
                {
                    button.setText("Siedlung zu Stadt ausbauen");
                    if (ecke.siedlung.art != 1)
                    {
                        if (Main.frueh)
                        {
                            exString = "Du kannst in der Startphase keine Siedlungen ausbauen!";
                            button.setEnabled(false);
                        } else
                        {
                            if (Main.spieler().inv.bezahlbar(new Inventar(2)))
                            {
                                button.addActionListener(new ActionListener()
                                                         {
                                                             public void actionPerformed(ActionEvent e)
                                                             {
                                                                 Client.senden("f"+Main.spieler().id+""+ecke.feld.x+""+ecke.feld.y+""+ecke.pos);
                                                                 Client.senden("n"+Main.spieler().id+"0m0m0m2m3");
                                                                 leerEcke(ecke);
                                                             }
                                                         }
                                );
                            } else
                            {
                                exString = "Du hast nicht genug Rohstoffe!";
                                button.setEnabled(false);
                            }
                        }
                    } else
                    {
                        exString = "Dies ist schon eine Stadt!";
                        button.setEnabled(false);
                    }
                } else
                {
                    exString = "Keine Städte mehr übrig";
                    button.setEnabled(false);
                }
            } else
            {
                if (Main.spieler().anzahlSiedlungen < Main.maxSiedlungen)
                {
                    if (ecke.keinNachbar())
                    {
                        if (ecke.siedlung == null || ecke.siedlung.spieler == Main.spieler())
                        {
                            if (Main.frueh)
                            {
                                if (Main.fruehsiedlung)
                                {
                                    button.addActionListener(new ActionListener()
                                                             {
                                                                 public void actionPerformed(ActionEvent e)
                                                                 {
                                                                     Client.senden("e"+Main.spieler().id+""+ecke.feld.x+""+ecke.feld.y+""+ecke.pos);
                                                                     Main.letzteSiedlung = ecke;
                                                                     Main.fruehsiedlung = false;
                                                                     Spielerpanel.akt();
                                                                     leerEcke(ecke);
                                                                 }
                                                             }
                                    );
                                } else
                                {
                                    exString = "Baue jetzt eine Straße an die neu gebaute Siedlung!";
                                    button.setEnabled(false);
                                }
                            } else
                            {
                                if (ecke.nachbar(Main.spieler()))
                                {
                                    if (Main.spieler().inv.bezahlbar(new Inventar(1)))
                                    {
                                        button.addActionListener(new ActionListener()
                                                                 {
                                                                     public void actionPerformed(ActionEvent e)
                                                                     {
                                                                         Client.senden("n"+Main.spieler().id+"1m1m1m1m0");
                                                                         Client.senden("e"+Main.spieler().id+""+ecke.feld.x+""+ecke.feld.y+""+ecke.pos);
                                                                         Spielerpanel.akt();
                                                                         leerEcke(ecke);
                                                                     }
                                                                 }
                                        );
                                    } else
                                    {
                                        exString = "Du hast nicht genug Rohstoffe!";
                                        button.setEnabled(false);
                                    }
                                } else
                                {
                                    exString = "Du kannst Siedlungen nur an eigene Straßen bauen!";
                                    button.setEnabled(false);
                                }
                            }
                        } else
                        {
                            exString = "Die Siedlung gehört jemand anderem!";
                            button.setEnabled(false);
                        }
                    } else
                    {
                        exString = "Es grenzen andere Siedlungen an dieses Feld an!";
                        button.setEnabled(false);
                    }
                } else
                {
                    exString = "Keine Siedlungen mehr übrig!";
                    button.setEnabled(false);
                }
            }
            add(button);
            ex = new

                    JLabel(exString);

            add(ex);
        }
        revalidate();
    }
}

