package com.elle.cellularautomata;
/**
 * Binary class for toBinarySet function
 */
public class Binary {
    /**
     * Converts decimal input into binary int array
     * @param num in decimal 0-255 inclusive
     * @return num as binary array
     */
    public static int[] toBinarySet(int num) {
        int[] bin = new int[0];
        if (num < 0 || num > 255) {
            System.out.println("toBinarySet requires an integer from 0 to 255");
        }
        else {
            bin = new int[8];
            int newnum = num;
            for (int i = bin.length - 1; i >= 0; i--) {
                bin[i] = newnum % 2;
                newnum /= 2;
            }
        }
        return bin;
    }

    public static void main(String[] args) {
        //System.out.print(Arrays.toString(toBinarySet(62)));
    }
}
