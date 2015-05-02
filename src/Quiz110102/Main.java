package Quiz110102;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Minesweeper
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

/**
 * * is 42
 * . is 46
 * n is row
 * m is col
 * 0 < n, m <= 100
 */
class Executer implements Runnable{
	public static int MINE = 42;
    public static int SPACE = 46;
    public void run(){

        String line = null;
        StringTokenizer idata;
        boolean bFlag = false;
        int nCase = 0;
        while((line = Main.ReadLn(255)) != null) {
            line = line.trim();
            if (line.length() == 0 || !matched("^\\d+ +\\d+$", line)) {
                break;
            }
            idata = new StringTokenizer(line);

            int row = Integer.parseInt (idata.nextToken());
            int col = Integer.parseInt (idata.nextToken());

            if (row == 0 && col == 0) {
                break;
            }

            if (!((row > 0 || row <= 100) && (col > 0 || col <= 100))) {
                break;
            }

            try {
                int[][] m = getMineData(row, col);
                if (bFlag) {
                    System.out.println();
                }
                bFlag = true;
                System.out.format("Field #%d:\n", ++nCase);

                for (int i = 0; i < m.length; i++) {
                    int [] r = m[i];
                    for (int j = 0; j < r.length; j++) {
                        if (isMine(r[j])) {
                            System.out.print("*");
                        } else {
                            System.out.print(getMineCount(m, m.length, r.length, i, j));
                        }
                    }
                    System.out.println();
                }
            } catch(IllegalArgumentException e) {
                break;
            }
        }

    }

    public int getMineCount(int[][] m , int row, int col, int i, int j) {
        int count = 0;
        count += checkMineCount(m, row, col, i - 1, j - 1);
        count += checkMineCount(m, row, col, i - 1, j);
        count += checkMineCount(m, row, col, i - 1, j + 1);
        count += checkMineCount(m, row, col, i, j + 1);
        count += checkMineCount(m, row, col, i, j - 1);
        count += checkMineCount(m, row, col, i + 1, j - 1);
        count += checkMineCount(m, row, col, i + 1, j);
        count += checkMineCount(m, row, col, i + 1, j + 1);
        return count;
    }
    public int checkMineCount(int[][] m , int row, int col, int i, int j) {
        if ((row <= i || i < 0) || (col <= j || j < 0)) {
            return 0;
        }
        if (isMine(m[i][j])) {
            return 1;
        } else {
            return 0;
        }
    }
    public boolean isMine(int value) {
        return MINE == value;
    }

    public boolean isSpace(int value) {
        return SPACE == value;
    }

    public int[][] getMineData(int row, int col) {
        String line = null;
        int[][] mine = new int[row][col];
        for (int i = 0; i < row; i++) {
            line = Main.ReadLn(col + 1);
            if (line != null) {
                for (int j = 0; j < col; j++) {
                    int c = line.charAt(j);
                    if (isMine(c) || isSpace(c)) {
                        mine[i][j] = c;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
        return mine;
    }
    public boolean matched(String regex, String inputText) {
        return Pattern.matches(regex, inputText);
    }
}
