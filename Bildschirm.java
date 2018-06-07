import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bildschirm extends JPanel
{
    JFrame f;
    static ArrayList<RectangleImage> grafikobjekte;
    static int ox = 0;
    static int oy = 0;
    static int feldb = 150;
    static int feldh = 150;
    static int eckeb = 30;
    static int eckeh = 30;
    static int kanteb = 20;
    static int kanteh = 20;
    static double plattenr = 3;
    Eckpanel eckpanel;
    Spielerpanel spielerpanel;
    Anderepanel[] anderePanel;
    JPanel farbe;
    Chat chat;
    JButton naechster;
    JButton handel;
    JButton entwicklung;
    JLabel momentanSpielerLabel;

    public Bildschirm(JFrame frame)
    {

        f = frame;
        f.setContentPane(this);

        setLayout(null);
        setBackground(new Color(4417163));
        grafikobjekte = new ArrayList<RectangleImage>();

        eckpanel = new Eckpanel();
        eckpanel.setBounds(f.getWidth() - 600, 0, 600, 200);
        add(eckpanel);

        spielerpanel = new Spielerpanel();
        spielerpanel.setBounds(0, 0, 300, 300);
        add(spielerpanel);

        momentanSpielerLabel = new JLabel();

        farbe = new JPanel();
        farbe.setBounds(f.getWidth() - 200, f.getHeight() - 170, 200, 170);
        farbe.add(momentanSpielerLabel);
        add(farbe);

        naechster = new JButton("Nächster Spieler");
        naechster.setBounds(f.getWidth() - 350, f.getHeight() - 170, 150, 170);
        naechster.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent ev)
                                        {
                                            //Main.nextPlayer();
                                            Client.senden("h"+Main.wuerfel());
                                        }
                                    }
        );
        naechster.setEnabled(false);
        add(naechster);

        handel = new JButton("4:1 Handel");
        handel.setBounds(f.getWidth() / 2 - 100, 0, 200, 100);
        handel.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed(ActionEvent e)
                                     {
                                         new Handel(6);
                                     }
                                 }
        );
        add(handel);

        entwicklung = new JButton("Entwicklung");
        {
            entwicklung.setBounds(0, 300, 200, 100);
        }
        entwicklung.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new Entwicklungskarten();
            }
        });
        add(entwicklung);

        if (!Main.lokal)
        {
            chat = new Chat();
            chat.setBounds(0, 600, 350, f.getHeight() - 600 - 50);
            add(chat);
        }


        farbe.setBackground(Main.spieler().farbe);
        momentanSpielerLabel.setText("Momentaner Spieler: "+Main.spieler().id);

        addAnderePanel();
        //f.setVisible(true);
        new Timer((int) 100 / 6, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
            }
        }).start();
    }


    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        try
        {
            for (int i = 0; i < Welt.felder.length; i++)
            {
                for (int j = 0; j < Welt.felder[i].length; j++)
                {
                /*System.out.println("welt: "+welt);
                System.out.println("welt.felder: "+welt.felder);
                System.out.println("welt.felder[j][i]"+welt.felder[j][i]);*/
                    Welt.felder[i][j].draw(g2);
                }
            }

            for (int i = 0; i < grafikobjekte.size(); i++)
            {
                grafikobjekte.get(i).draw(g2);
            }
        } catch (NullPointerException ex)
        {

        }
    }

    void replaceGrafikObjekt(RectangleImage toReplace, RectangleImage replacement)
    {
        grafikobjekte.remove(toReplace);
        grafikobjekte.add(replacement);
    }

    public void addAnderePanel()
    {
        anderePanel = new Anderepanel[Main.anzahlSpieler];
        for (int i = 0; i < Main.anzahlSpieler; i++)
        {
            anderePanel[i] = new Anderepanel(Main.spieler[i]);
            anderePanel[i].setBounds((int) (f.getWidth() / 2 - Main.anzahlSpieler * 0.5 * Anderepanel.width + Anderepanel.width * i), f.getHeight() - Anderepanel.height, Anderepanel.width, Anderepanel.height);
            anderePanel[i].setVisible(true);
            add(anderePanel[i]);
        }
    }
}