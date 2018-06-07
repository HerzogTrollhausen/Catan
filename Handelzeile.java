import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Handelzeile extends JPanel
{
    JLabel name, linkslabel, rechtslabel;
    JSpinner betrag;

    Handelzeile(int gut, Spieler s1, Spieler s2)
    {
        normal(s1, gut);
        betrag = new JSpinner(new SpinnerNumberModel(0, -1 * s1.inv.rohstoffe[gut], s2.inv.rohstoffe[gut], 1));
        add(betrag);
        rechtslabel = new JLabel("" + s2.inv.rohstoffe[gut]);
        add(rechtslabel);
    }

    void normal(Spieler s1, int gut)
    {
        name = new JLabel(Inventar.name(gut) + ":");
        add(name);
        linkslabel = new JLabel("" + s1.inv.rohstoffe[gut]);
        add(linkslabel);
    }

    Handelzeile(int gut, int art, Handel handel)
    {
        normal(Main.spieler(), gut);
        if (art == 6)
        {
            betrag = new JSpinner();//new SpinnerNumberModel(0, -1 * Main.spieler().inv.rohstoffe[gut], 0, 1));
        }
        add(betrag);
    }

    int wert()
    {
        return (int) betrag.getValue();
    }
}