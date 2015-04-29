package Quiz110101;


import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 3n+1
 * @author vamalboro
 *
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
	public static long[] cache = new long[1000000];
    public void run(){
    	String line;
    	StringTokenizer idata;
    	while((line = Main.ReadLn(255)) != null) {    		
    		line = line.trim();
    		if (line.length() == 0 || !matched("^\\d+ +\\d+$", line)) {
    			break;
    		}
    		idata = new StringTokenizer (line);
    		
    		int start = Integer.parseInt (idata.nextToken());
            int end = Integer.parseInt (idata.nextToken());
    		if (start <= 0 || start >= 1000000) {
    			break;
    		}
    		if (end <= 0 || end >= 1000000) {
    			break;
    		}
    		if (start > end) {
    			System.out.format("%d %d %d\n", start, end, getMaxCycleLength(end, start));			
    		} else {
    			System.out.format("%d %d %d\n", start, end, getMaxCycleLength(start, end));
    		}
    	}
    }
    
    public boolean matched(String regex, String inputText) {
		return Pattern.matches(regex, inputText);
	}
	
	public long getCycleLengthVia3n1(long n) {
		long count = 1;
		if (n == 1) {
			return 1;
		}
		if (n < 1000000 && cache[(int)n] != 0) {
			return cache[(int)n];
		}
		while(n != 1) {
			if (n < 1000000 && cache[(int)n] != 0) {
				count += cache[(int)n];
				break;
			}
			if ((n & 1) == 1) {
				n = n * 3 + 1;
				count++;
			}
			while ((n & 1) == 0) {
				n = n >> 1;
                count++;
			}
			
		}
		if (n < 1000000) {
			cache[(int)n] = count;
		}
		return count;
	}
	
	public long getMaxCycleLength(int start, int end) {
		long max = 0;
		for (int i = start; i <= end; i++) {
			long cycleLength = getCycleLengthVia3n1(i);
			if (max < cycleLength) {
				max = cycleLength;
			}
		}
		
		return max;
	}
    
}
