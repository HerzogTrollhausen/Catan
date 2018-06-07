import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable
{

    private Socket socket;//MAKE SOCKET INSTANCE VARIABLE
    static PrintWriter out;

    public Client(Socket s)
    {
        socket = s;//INSTANTIATE THE INSTANCE VARIABLE
        try
        {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try
        {
            Scanner in = new Scanner(socket.getInputStream());//GET THE CLIENTS INPUT STREAM (USED TO READ DATA SENT FROM THE SERVER)

            while (true)//WHILE THE PROGRAM IS RUNNING
            {
                if (in.hasNext())//IF THE SERVER SENT US SOMETHING
                    OnlineInterpreter.interpret(in.nextLine());//PRINT IT OUT
            }
        } catch (Exception e)
        {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }

    public static void senden(String msg)
    {
        if (Main.lokal)
        {
            OnlineInterpreter.interpret(msg);
        } else
        {
            out.println(msg);
            out.flush();
        }
    }

}
