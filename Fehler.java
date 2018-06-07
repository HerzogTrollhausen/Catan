import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fehler extends JFrame{

    public Fehler(String nachricht)
    {
        setAlwaysOnTop(true);
        setTitle("Fehler!");
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));
        setContentPane(mainpanel);
        JLabel label = new JLabel(nachricht);
        JButton button = new JButton("Ok");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mainpanel.add(label);
        mainpanel.add(button);
        Dimension groesse = getLayout().preferredLayoutSize(this);
        groesse.setSize(groesse.getWidth()+25, groesse.getHeight()+40);
        setSize(groesse);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public Fehler(String nachricht, String titel)
    {
        this(nachricht);
        setTitle(titel);
    }

}
