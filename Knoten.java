public class Knoten
{
    Knoten next;
    String stringInhalt;
    int intInhalt;
    public Knoten(String inhalt)
    {
        stringInhalt = inhalt;
    }
    
    public Knoten(int inhalt)
    {
        intInhalt = inhalt;
    }
}