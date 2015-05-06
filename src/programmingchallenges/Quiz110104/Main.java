package programmingchallenges.Quiz110104;


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

/**
 * 각 숫자(0 ~ 9)에 대해서 각 위치별 display 상태를 bit flag로 처리한다.
 * start, middle, end, top left, top right, bottom left, bottom right순으로 정의한다.
 * 
 * ex)
 * start    ->     ---  
 * top left ->    |   | <- top right
 *                |   |
 *                |   |
 * middle   ->     --- 
 * bottom left -> |   | <- bottom right
 *                |   |
 *                |   |
 * end      ->     --- 
 * 0 -> 1011111
 * 1 -> 0000101
 * 2 -> 1110110
 * 3 -> 1110101
 * 4 -> 0101101
 * 5 -> 1111001
 * 6 -> 1111011
 * 7 -> 1000101
 * 8 -> 1111111
 * 9 -> 1111101
 * numbers : 10개의 숫자 bit flag를 정수화 하였을때의 값이며 순서대로 정의되어 있다.
 * LCD_MASK : 각 위치에 대한 flag를 정수화 하였을때의 값이며 이를 마스크로 이용한다.
 */
class Executer implements Runnable{
	
	public char initialChar = ' ';
	public char horizontalChar = '-';
	public char verticalChar = '|';
	
	public int[] numbers = new int[]{95, 5, 118, 117, 45, 121, 123, 69, 127, 125 };
	public int[] LCD_MASK = new int[]{64, 32, 16, 8, 4, 2, 1};

	public void initBuffer(char[][] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			for (int j = 0; j < buffer[i].length; j++) {
				buffer[i][j] = initialChar;
			}
		}
	}
	
	public void renderBuffer(char[][] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			for (int j = 0; j < buffer[i].length; j++) {
				System.out.print(buffer[i][j]);
			}
			System.out.println();
		}
	}
	
	public void render(int s, int[] data) {
		int row = 2 * s + 3;
		int col = s + 2;
		char[][] buffer = new char[row][(col) * data.length + (data.length - 1)];
		initBuffer(buffer);
		fillBuffer(row, col, data, buffer);
		renderBuffer(buffer);
	}
	
	public void fillBuffer(int row, int col, int[] data, char[][] buffer) {
		for (int i = 0; i < data.length; i++) {
			renderHotizontal(i * col + i, 0, col, data[i], buffer, LCD_MASK[0] == (numbers[data[i]] & LCD_MASK[0]) ? true : false);
			renderHotizontal(i * col + i, row / 2, col, data[i], buffer, LCD_MASK[1] == (numbers[data[i]] & LCD_MASK[1]) ? true : false);
			renderHotizontal(i * col + i, row - 1, col, data[i], buffer, LCD_MASK[2] == (numbers[data[i]] & LCD_MASK[2]) ? true : false);
		}
		
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 1; j < row / 2; j++) {
				renderVertical(i * col + i, j, col, data[i], buffer,
						LCD_MASK[3] == (numbers[data[i]] & LCD_MASK[3]) ? true : false,
						LCD_MASK[4] == (numbers[data[i]] & LCD_MASK[4]) ? true : false);
			}
			for (int j = row / 2 + 1; j < row - 1; j++) {
				renderVertical(i * col + i, j, col, data[i], buffer,
						LCD_MASK[5] == (numbers[data[i]] & LCD_MASK[5]) ? true : false,
						LCD_MASK[6] == (numbers[data[i]] & LCD_MASK[6]) ? true : false);
			}
		}
	}
	public void renderVertical(int startIdx, int row, int col, int n, char[][] buffer, boolean bStartFlag, boolean bEndFlag) {
		if (bStartFlag) {
			buffer[row][startIdx] = verticalChar;
		}
		if (bEndFlag) {
			buffer[row][startIdx + col - 1] = verticalChar;
		}
	}
	
	public void renderHotizontal(int startIdx, int row, int col, int n, char[][] buffer, boolean bFlag) {
		for (int j = startIdx + 1; j < startIdx + col - 1; j++) {
			if (bFlag) {
				buffer[row][j] = horizontalChar;
			}
		}
		
	}
    public void run(){
    	
    	int s = 3;
    	int[] data = new int[]{1,2,3,4,5,6,7,8,9,0};
    	render(s, data);
    	
    	
        /*String line = null;
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
        }*/
    }

    public boolean matched(String regex, String inputText) {
        return Pattern.matches(regex, inputText);
    }
}
