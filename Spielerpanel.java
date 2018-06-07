import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Spielerpanel extends JPanel
{
    JLabel label;

    public Spielerpanel()
    {
        setBackground(Main.spieler[Main.ich].farbe);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel();
        add(label);
    }

    public static void akt()
    {
        if(Main.frueh)
        {
            Main.bildschirm.spielerpanel.start();
        }
        else
        {
            System.err.println("Spielerpanel.akt()"+Main.frueh);
        }
    }

    public void start()
    {
        label.setText(Main.fruehsiedlung?"Du kannst noch "+(Main.fruehvor+3)/2+" Siedlungen bauen!":"Baue eine Straße an die Siedlung!");
        revalidate();
    }
}