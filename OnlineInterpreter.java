public class OnlineInterpreter
{
    /**
     * aHallo wie geht's denn so?: Chatnachricht "Hallo wie geht's denn so"
     * b00332421: Felderstapel
     * c26a5ab349: Zahlenstapel (mit Zahlen in Hexadezimal)
     * d0453: Straße von 0 bei Feld 4|5 Position 3 gebaut
     * e0453: Siedlung von 0 bei Feld 4|5 Position 3 gebaut
     * f0453: Stadt von 0 bei Feld 4|5 Position 3 gebaut
     * g323: Spieler 3 zieht Entwicklungskarte 23
     * h12: Spieler würfelt 12
     * i23: Spieler 2 spielt Entwicklungskarte vom Typ 3 aus
     * j12563: Spieler 1 stellt den Räuber auf Feld 5|6 und klaut Spieler 2 eine Karte vom Typ 3
     * k2: ID wird auf 2 gesetzt
     * l23104: Hafenstapel
     * m: Starte das Spiel
     * n20|0|0|12|3: Spieler 2 bezahlt 12 Weizen und 3 Erz
     * o20|0|0|12|3: Spieler 2 bekommt 12 Weizen und 3 Erz
     */
    public static void interpret(String msg)
    {
        System.out.println(msg);
        switch (msg.charAt(0))
        {
            case 'a'://aHallo wie geht's denn so?: Chatnachricht "Hallo wie geht's denn so"
            {
                Main.bildschirm.chat.displayMessage(msg.substring(1));
                break;
            }
            case 'b'://b00332421: Felderstapel
            {
                int[] a = new int[19];
                char[] c = msg.toCharArray();
                for (int i = 1; i < 20; i++)
                {
                    a[i - 1] = Integer.parseInt("" + c[i]);
                }
                Welt.felderArray = a;
                if (Main.starter)
                {
                    Client.senden("c" + Welt.stapelZahlen("Zahl"));
                }
                break;
            }
            case 'c'://c26a5ab349: Zahlenstapel (mit Zahlen in Hexadezimal)
            {
                int[] a = new int[18];
                char[] c = msg.toCharArray();
                for (int i = 1; i < 19; i++)
                {
                    a[i - 1] = Integer.parseInt("" + c[i], 16);
                }
                Welt.plaettchenArray = a;
                if (Main.starter)
                {
                    Client.senden("l" + Welt.stapelZahlen("Hafen"));
                }
                break;
            }
            case 'd'://d0453: Straße von 0 bei Feld 4|5 Position 3 gebaut
            {
                Welt.addStrasse(Integer.parseInt("" + msg.charAt(2)), Integer.parseInt("" + msg.charAt(3)), Integer.parseInt("" + msg.charAt(4)), Integer.parseInt("" + msg.charAt(1)));
                break;
            }
            case 'e'://e0453: Siedlung von 0 bei Feld 4|5 Position 3 gebaut
            {
                Welt.addSiedlung(Integer.parseInt("" + msg.charAt(2)), Integer.parseInt("" + msg.charAt(3)), Integer.parseInt("" + msg.charAt(4)), Integer.parseInt("" + msg.charAt(1)));
                break;
            }
            case 'f'://f0453: Stadt von 0 bei Feld 4|5 Position 3 gebaut
            {
                Welt.zuStadt(Integer.parseInt("" + msg.charAt(2)), Integer.parseInt("" + msg.charAt(3)), Integer.parseInt("" + msg.charAt(4)), Integer.parseInt("" + msg.charAt(1)));
                break;
            }
            case 'g'://g323: Spieler 3 zieht Entwicklungskarte 23
            {
                break;//TODO
            }
            case 'h'://h12: Spieler würfelt 12
            {
                int tmp = Integer.parseInt(msg.substring(1));
                Main.nextPlayer(tmp);
                break;
            }
            case 'i'://i23: Spieler 2 spielt Entwicklungskarte vom Typ 3 aus
            {
                break; //TODO
            }
            case 'j'://j56: Räuber auf Feld 5|6
            {

                break;
            }
            case 'k'://k2: ID wird auf 2 gesetzt
            {
                Main.ich = Integer.parseInt(msg.substring(1));
                if (Main.ich == 0)
                {
                    Main.hauptmenue.add(Main.hauptmenue.starten);
                    Main.hauptmenue.revalidate();
                }
                break;
            }
            case 'l'://l23104: Hafenstapel
            {
                int[] a = new int[9];
                char[] c = msg.toCharArray();
                for (int i = 1; i < 10; i++)
                {
                    a[i - 1] = Integer.parseInt("" + c[i]);
                }
                Welt.haefenArray = a;
                Main.start();
                break;
            }
            case 'm'://m: Starte das Spiel
            {
                if (Main.starter)
                {
                    Client.senden("b" + Welt.stapelZahlen("Feld"));
                }
                break;
            }
            case 'n'://n20m0m0m12m3: Spieler 2 bezahlt 12 Weizen und 3 Erz
            {
                Spieler s = Main.spieler[Integer.parseInt(""+msg.charAt(1))];
                String[] sArray = msg.substring(2).split("m");
                int[] iArray = new int[5];
                for(int i = 0; i < 5; i++)
                {
                    iArray[i] = Integer.parseInt(sArray[i]);
                }
                s.inv.bezahl(iArray);
                break;
            }
            case 'o': //o20m0m0m12m3: Spieler 2 bekommt 12 Weizen und 3 Erz
            {
                Spieler s = Main.spieler[Integer.parseInt(""+msg.charAt(1))];
                String[] sArray = msg.substring(2).split("m");
                int[] iArray = new int[5];
                for(int i = 0; i < 5; i++)
                {
                    iArray[i] = Integer.parseInt(sArray[i]);
                }
                s.inv.hinzu(iArray);
                break;
            }
            default:
                System.out.println("Unpassende Nachricht: " + msg);
        }
    }
}
