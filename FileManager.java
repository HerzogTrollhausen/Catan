import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;
public class FileManager implements java.io.Serializable
{
    public String Inhalt;
    public BufferedImage tempBild;
    public String[] randomTownNames = new String[26*26*26];
    public static Image Normal, Stich, Roboter, Speer;
    
    public FileManager()
    {

    }

    public static void writeToFile(String path, String textLine, boolean ersetzen) throws IOException {
        FileWriter write = new FileWriter( path , !ersetzen);
        PrintWriter print_line = new PrintWriter( write );
        print_line.println(textLine);
        print_line.close();
    }

    public String fileReading(String path)
    throws java.io.IOException
    {
        return new String(Files.readAllBytes(Paths.get(path)));
        //return null;
    }

    public static BufferedImage bildLaden(String path)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
            return img;
        } catch (IOException e) {
            System.out.println("Die Datei existiert nicht!");
            return null;
        }
    }

    public static int[] farben(String path, int x, int y)
    {
        try{
            Color pixelfarbe = new Color(bildLaden(path).getRGB(x,y));
            int[] tmp = {pixelfarbe.getRed(),pixelfarbe.getGreen(),pixelfarbe.getBlue()};
            return tmp; 
        }
        catch(NullPointerException e){
            System.out.println("Geht nicht, wahrscheinlich ist der Dateipfad falsch");
            return null;
        }
    }

    public static int[] farben(BufferedImage img, int x, int y)
    {
        Color pixelfarbe = new Color(img.getRGB(x,y));
        int[] tmp = {pixelfarbe.getRed(),pixelfarbe.getGreen(),pixelfarbe.getBlue()};
        return tmp; 
    }

    public static void bildZuText(String path)
    {
        int momFarbe = 0;
        int tmp = 0;
        BufferedImage img = bildLaden(path);
        if(img == null)
            return;
        String[] Zeile = new String[img.getHeight()];    
        try{
            FileWriter write = new FileWriter("Leveldateien/Testlevel.txt");
            write.close();
        }
        catch(IOException e)
        {
            System.out.println("'Leveldateien' existiert nicht.");
        }
        for (int j = 0; j < img.getHeight(); j++)
        {
            Zeile[j] = "";
            tmp = 0;
            for(int i = 0; i <img.getWidth(); i++)
            {
                if(momFarbe == 0)
                {
                    momFarbe=img.getRGB(i,j);
                    tmp = 1;
                }
                else if(img.getRGB(i,j)!=momFarbe)
                {
                    Zeile[j] = Zeile[j]+tmp+":"+momFarbe+";";
                    tmp = 1;
                    momFarbe=img.getRGB(i,j);
                }
                else tmp ++;
            }
            Zeile[j] = Zeile[j]+tmp+":"+momFarbe+";";
            try{
                writeToFile("Leveldateien/Testlevel.txt",Zeile[j],false);
            }
            catch (IOException e){
                System.out.println("DAFUQ");
            }
            System.out.println(j);
        }
        System.out.println("Bing!");
    }

    public static String zeileAuslesen(String path, int Zeile)
    throws IOException, ArrayIndexOutOfBoundsException
    {
        //return Files.readAllLines(Paths.get(path)).get(Zeile);
        return "Umändern!";
    }

    public static int[] imageScale(String path)
    {

        int[] temp = new int[2];
        temp[0] = bildLaden(path).getWidth();
        temp[1] = bildLaden(path).getHeight();
        return temp;
    }

    public void randomTownNames()
    {
        String[] eins = new String[26];
        int tmp = 0;
        for(int i = 0; i < eins.length; i++)
        {
            eins[i] = Character.toString((char)(65+i));
        }
        for(int i = 0; i < 26; i++)
        {
            for(int j = 0; j < 26; j++)
            {
                for(int k = 0; k < 26; k++)
                {
                    randomTownNames[tmp] = eins[i]+eins[j]+eins[k];
                    tmp++;
                }
            }
        }
    }

    public static double randomgen(long seed)
    {
        Random generator = new Random(seed);
        double num = generator.nextDouble();
        return num;
    }

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
    
    public static String wort(String ganz, int Nummer)
    {
        String tmp = "";
        int temp = 0;
        for(int i = 1; i<=Nummer;i++)
        {
            tmp = "";

            if(temp  >ganz.length())
                return "";
            while(ganz.charAt(temp) != 32)
            {
                tmp = tmp+ganz.charAt(temp);
                temp++;
                if(!(temp<ganz.length()))
                {
                    break;
                }
            }
            temp++;
        }
        return tmp;
    }
    
    public static BufferedImage createResizedCopy(Image originalImage, 
    		int scaledWidth, int scaledHeight, 
    		boolean preserveAlpha)
    {
    	//System.ou//t.println("resizing...");
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    }
    
    public static Image FeldLaden(String name)
    {
        return FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/"+name+".gif"), Bildschirm.feldb, Bildschirm.feldh, false);
    }
}