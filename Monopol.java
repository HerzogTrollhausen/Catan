import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Monopol extends JPanel
{
    JFrame f;

    public Monopol()
    {
        framezeug();
        String[] rohstoffe = {"Holz", "Lehm", "Schaf", "Weizen", "Erz"};
        JComboBox<String> box = new JComboBox<String>(rohstoffe);
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int r = box.getSelectedIndex();
                int tmp = 0;
                for(int i = 0; i < Main.anzahlSpieler; i++)
                {
                    tmp += Main.spieler[i].inv.rohstoffe[r];
                    Main.spieler[i].inv.rohstoffe[r] = 0;
                }
                Main.spieler().inv.rohstoffe[r] = tmp;
                Main.anderePanelAkt();
                f.dispose();
            }
        });
        add(box);
        add(ok);
        f.setVisible(true);
    }

    private void framezeug()
    {
        f = new JFrame("Monopolauswahl");
        f.setContentPane(this);
        f.setSize(350, 150);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
