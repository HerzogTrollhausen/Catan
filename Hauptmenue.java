import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO: Das ganze sehr, sehr viel hübscher machen, "Zurück"-Knöpfe etc.
 */
public class Hauptmenue extends JPanel
{
    JButton local;
    JButton online;
    JButton settings;//TODO
    JButton quit;

    JTextField hostField = new JTextField("localhost");
    JTextField portField = new JTextField("6677");
    JLabel hostLabel = new JLabel("Host:");
    JLabel portLabel = new JLabel("Port");
    JPanel hostPanel = new JPanel();
    JPanel portPanel = new JPanel();
    JButton hostPortButton = new JButton("Verbinden");
    JButton starten = new JButton("Starte... das... SPIEL DU HURENSOHN!!!");

    public Hauptmenue()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        local = new JButton("Lokales Spiel");
        local.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.starter = true;
                Client.senden("m");
            }
        });

        online = new JButton("Online");
        online.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                remove(local);
                remove(quit);
                remove(online);
                Main.lokal = false;
                hostPanel.setMaximumSize(new Dimension(200, 40));
                portPanel.setMaximumSize(new Dimension(200, 40));
                add(hostPanel);
                add(portPanel);
                add(hostPortButton);
                revalidate();
                repaint();
            }
        });

        quit = new JButton("Beenden");
        quit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.fenster.dispose();
            }
        });
        hostPanel.add(hostLabel);
        hostPanel.add(hostField);
        portPanel.add(portLabel);
        portPanel.add(portField);
        hostPortButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Main.mitServerVerbinden(hostField.getText(), Integer.parseInt(portField.getText()));
                }catch (NumberFormatException ex){
                    new Fehler("Bitte eine gültige Zahl eingeben");
                }
            }
        });

        starten.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                Main.starter = true;
                Client.senden("m");
            }
        });

        add(local);
        add(online);
        add(quit);

        /*new Timer((int)100/6, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
            }
        }).start();*/
    }
}
