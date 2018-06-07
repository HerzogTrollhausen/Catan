import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Entwicklungskarten extends JPanel// 14 Ritter, 6 Fortschritt, 5 Siegpunkt
{// 0 Ritter, 1 Siegpunkt, 2 Straßenbau, 3 Erfindung, 4 Monopol
    JFrame f;
    static Stapel stapel = new Stapel("Entwicklung");

    public Entwicklungskarten()
    {
        framezeug();
        JPanel ritterpanel = new JPanel();
        JLabel ritterlabel = new JLabel("Ritter: " + Main.spieler().anzahlEntwicklungskarten(0));
        JButton ritterbutton = new JButton("Ausschicken!");
        JLabel ausgeschicktlabel = new JLabel("Ausgeschickt: "+Main.spieler().rittermacht);
        ritterbutton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Bandit.ausschicken();
                Main.spieler().inv.entwicklungskarten[0]--;
                Main.spieler().anzahlGelegteRitter++;
                Main.rittermacht(Main.spieler().anzahlGelegteRitter);
                f.dispose();
            }
        });
        if (Main.spieler().anzahlEntwicklungskarten(0) == 0)
        {
            ritterbutton.setEnabled(false);
        }

        JLabel rittermachtlabel = new JLabel("Setze drei Ritter ein, um die Größte Rittermacht für 2 Siegpunkte zu bekommen");
        if (Main.rittermacht != null)
        {
            rittermachtlabel.setText("Die größte Rittermacht hat Spieler "
                    + Main.rittermacht.id + " mit " + Main.rittermacht.rittermacht + " gelegten Rittern.");
        }

        JPanel strassenbaupanel = new JPanel();
        JLabel strassenbaulabel = new JLabel("Straßenbau: " + Main.spieler().anzahlEntwicklungskarten(2));
        JButton strassenbaubutton = new JButton("Bauen!");
        strassenbaubutton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.strassenbau();
                Main.spieler().inv.entwicklungskarten[2]--;
                f.dispose();
            }
        });
        if (Main.spieler().anzahlEntwicklungskarten(2) == 0)
        {
            strassenbaubutton.setEnabled(false);
        }

        JPanel erfindungpanel = new JPanel();
        JLabel erfindunglabel = new JLabel("Erfindung: " + Main.spieler().anzahlEntwicklungskarten(3));
        JButton erfindungbutton = new JButton("2 Karten nehmen!");
        erfindungbutton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new Handel(7);
                Main.spieler().inv.entwicklungskarten[3]--;
                f.dispose();
            }
        });
        if (Main.spieler().anzahlEntwicklungskarten(3) == 0)
        {
            erfindungbutton.setEnabled(false);
        }

        JPanel monopolpanel = new JPanel();
        JLabel monopollabel = new JLabel("Monopol: " + Main.spieler().anzahlEntwicklungskarten(4));
        JButton monopolbutton = new JButton("Monopol aussuchen!");
        monopolbutton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new Monopol();
                Main.spieler().inv.entwicklungskarten[4]--;
                f.dispose();
            }
        });
        if (Main.spieler().anzahlEntwicklungskarten(4) == 0)
        {
            monopolbutton.setEnabled(false);
        }

        JPanel siegpunktpanel = new JPanel();
        JLabel siegpunktlabel = new JLabel("Siegpunktkarten: " + Main.spieler().anzahlEntwicklungskarten(1));

        JButton kaufen = new JButton("Entwicklungskarte kaufen");
        kaufen.setEnabled(Main.spieler().inv.bezahlbar(new Inventar(3)));
        kaufen.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.spieler().inv.bezahl(new Inventar(3));
                Main.spieler().inv.entwicklungskarten[stapel.randomZahl()]++;
                kaufen.setEnabled(Main.spieler().inv.bezahlbar(new Inventar(3)));
                Main.anderePanelAkt();
                f.dispose();
            }
        });
        add(kaufen);

        ritterpanel.add(ritterlabel);
        ritterpanel.add(ritterbutton);
        strassenbaupanel.add(strassenbaulabel);
        strassenbaupanel.add(strassenbaubutton);
        erfindungpanel.add(erfindunglabel);
        erfindungpanel.add(erfindungbutton);
        monopolpanel.add(monopollabel);
        monopolpanel.add(monopolbutton);
        siegpunktpanel.add(siegpunktlabel);

        add(ritterpanel);
        add(rittermachtlabel);
        add(strassenbaupanel);
        add(erfindungpanel);
        add(monopolpanel);
        add(siegpunktpanel);

        f.setSize(getLayout().minimumLayoutSize(this).width+30, getLayout().minimumLayoutSize(this).height+30);
        f.setVisible(true);

    }

    public void framezeug()
    {
        f = new JFrame("Entwicklungskarten");
        f.setContentPane(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
