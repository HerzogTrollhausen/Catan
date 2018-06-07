import java.awt.*;

public class Hafen extends Kante
{
    int typ; //0-4 entsprechendes Gut 2:1, 5 ist alles 3:1
    static Image[] haefen;
    static Stapel hafenStapel = new Stapel("Hafen");

    public Hafen(Feld feld, int pos, int typ)
    {
        super(haefen[typ]);
        this.typ = typ;
        hafen = true;
        //img = FileManager.createResizedCopy(img, 50, 50, true);
        setSize(40, 40);
    }

    public Hafen(Kante kante, int typ)
    {
        this(kante.feld1, kante.pos, typ);
    }

    /**
     * @param typ Hafentyp, um den es geht
     * @return Ein String, der im Eckpanel genutzt wird, um die Hafenrolle genauer zu erklären
     */
    static String stringAusTyp(int typ){
        switch (typ)
        {
            case 0: return "wird Holz im Verhältnis 2:1";
            case 1: return "wird Lehm im Verhältnis 2:1";
            case 2: return "werden Schafe im Verhältnis 2:1";
            case 3: return "wird Weizen im Verhältnis 2:1";
            case 4: return "wird Erz im Verhältnis 2:1";
            case 5: return "werden beliebige Rohstoffen im Verhältnis 3:1";
            default: throw new IllegalArgumentException("Hafen.java; String stringAusTyp(int typ); typ ist "+typ);
        }
    }

    public Hafen(Kante kante)
    {
        this(kante, hafenStapel.randomZahl());
    }

    @Override
    public void aktBild()
    {
        img = haefen[typ];
    }
}
