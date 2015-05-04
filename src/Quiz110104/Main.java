package Quiz110104;


import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * LCD Display
 * PC/UVa ID: 110104/706
 */
public class Main implements Runnable{
    static String ReadLn(int maxLength){  // utility function to read from stdin,
                                          // Provided by Programming-challenges, edit for style only
        byte line[] = new byte [maxLength];
        int length = 0;
        int input = -1;
        try{
            while (length < maxLength){//Read untill maxlength
                input = System.in.read();
                if ((input < 0) || (input == '\n')) break; //or untill end of line ninput
                line [length++] += input;
            }

            if ((input < 0) && (length == 0)) return null;  // eof
            return new String(line, 0, length);
        }catch (IOException e){
            return null;
        }
    }

    public static void main(String args[])  // entry point from OS
    {
        Main myWork = new Main();  // Construct the bootloader
        myWork.run();            // execute
    }

    public void run() {
        new Executer().run();
    }
}

class Executer implements Runnable{


    public void run(){
        String line = null;
        StringTokenizer idata;
        while((line = Main.ReadLn(255)) != null) {
            line = line.trim();
            if (line.length() == 0 || !matched("^\\d+ +\\d+$", line)) {
                break;
            }
            idata = new StringTokenizer(line);

            int s = Integer.parseInt (idata.nextToken());
            int n = Integer.parseInt (idata.nextToken());

            if (s == 0 && n == 0) {
                break;
            }

            if ((s == 0 || s > 10) || (n > 99999999)) {
                break;
            }


        }
    }

    public boolean matched(String regex, String inputText) {
        return Pattern.matches(regex, inputText);
    }
}
