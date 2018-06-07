import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat extends JPanel
{
    JTextArea area;
    JPanel unten;
    JTextField field;
    JButton button;
    public Chat()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setAutoscrolls(true);
        unten = new JPanel();
        field = new JTextField();
        field.setPreferredSize(new Dimension(200, 40));
        button = new JButton("Senden");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Client.senden("aSpieler "+Main.ich+": "+field.getText());
                field.setText("");
            }
        });
        button.setMaximumSize(new Dimension(150, 40));
        unten.setMaximumSize(new Dimension(20000, 45));
        unten.add(field);
        unten.add(button);
        add(area);
        add(unten);
    }

    public void displayMessage(String msg)
    {
        area.append('\n'+msg);
    }
}
