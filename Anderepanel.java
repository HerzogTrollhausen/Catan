import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Anderepanel extends JPanel
{
    Spieler spieler;
    JPanel farbe;
    JLabel[] rohstoffe;
    JLabel siegpunkte;
    static int width = 250;
    static int height = 200;
    JButton handel;
    public Anderepanel(Spieler spieler)
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.spieler = spieler;
        farbe = new JPanel();
        farbe.setMinimumSize(new Dimension(width, 50));
        farbe.setMaximumSize(new Dimension(width, 50));
        farbe.setBackground(spieler.farbe);
        
        farbe.setLayout(new BoxLayout(farbe, BoxLayout.Y_AXIS));
        handel = new JButton("Handel");
        handel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
                new Handel(Main.spieler(), spieler);
            }
        }
        );
        farbe.add(handel);
        
        farbe.setVisible(true);
        add(farbe);
        
        rohstoffe = new JLabel[5];
        siegpunkte = new JLabel();
        for(int i = 0; i < 5; i++)
        {
            rohstoffe[i] = new JLabel();
            add(rohstoffe[i]);
        }
        updateRohstoffe();
        add(siegpunkte);
    }
    
    public void updateRohstoffe()
    {
        handel.setVisible(spieler != Main.spieler());
        for(int i = 0; i < 5; i++)
        {
            rohstoffe[i].setText(Inventar.name(i)+" : "+spieler.inv.rohstoffe[i] + (spieler.letzteErnte == null || spieler.letzteErnte.rohstoffe[i] == 0 
            ? "" : "(+"+spieler.letzteErnte.rohstoffe[i]+")"));
        }
        spieler.letzteErnte = null;
        siegpunkte.setText("Siegpunkte: "+spieler.siegPunkte());
    }
    
}