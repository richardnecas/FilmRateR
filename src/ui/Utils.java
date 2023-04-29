package ui;

import java.util.Scanner;

public class Utils {
    private static Scanner in;

    public static void print(String dat){
        System.out.println(dat);
    }
    public static String stringInput() throws Exception{
        in = new Scanner(System.in);
        return in.nextLine();
    }

    public static int intInput() throws Exception{
        in = new Scanner(System.in);
        int dat = 0;
        try {
            dat = in.nextInt();

        } catch (Exception e){
            print("Zadejte cislo!");
            intInput();
        }
        return dat;
    }
}
