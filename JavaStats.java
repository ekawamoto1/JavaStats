import java.util.*;

public class JavaStats
{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        System.out.println("Enter 1 for keyboard input, 2 for file input: ");
        int mode = sc.nextInt();
        sc.nextLine();
        if (mode == 1)
        {
            System.out.println("When no more data is left to enter, simply hit return.");
            String s = "";
            int sLen = 1;
            double n = 0.0;
            double term = 0.0;
            double sum = 0.0;
            double mean = 0.0;
            double max = -1.0E10;
            double min = 1.0E10;
            double stdev = 0.0;
            do
            {
                System.out.printf("Data point %.0f: ", (n + 1));
                s = sc.nextLine();
                sLen = s.length();
                //System.out.println("You entered " + s + ", length " + sLen);
                if (sLen > 0)
                {
                    n++;
                    term = Double.parseDouble(s);
                    if (term > max)
                    {
                        max = term;
                    }
                    if (term < min)
                    {
                        min = term;
                    }
                    sum += term;
                }
            } while (sLen > 0);
            if (n > 0)
            {
                mean = sum / n;
                System.out.printf("For %.0f data points, \n", n);
                System.out.printf("The mean (average) is %.2f\n", mean);
                System.out.printf("The maximum is %.2f\n", max);
                System.out.printf("The minimum is %.2f\n", min);
            }
            else
            {
                System.out.println("No data was entered.");
            }
        }
        else if (mode == 2)
        {
        }
        else
        {
            System.out.println("Invalid mode; exiting program.");
        }
    }
}

/*
 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 
1
When no more data is left to enter, simply hit return.
Data point 1: 32
Data point 2: 33
Data point 3: 34
Data point 4: 
For 3 data points, 
The mean (average) is 33.00
The maximum is 34.00
The minimum is 32.00

 ----jGRASP: operation complete.

*/
