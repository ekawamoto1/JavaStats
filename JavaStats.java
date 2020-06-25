import java.util.*;

public class JavaStats
{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        ArrayList<Double> dArrL = new ArrayList<>();    // initialize empty ArrayList
        int n = 0;
        double term = 0.0;
        double sum = 0.0;
        double mean = 0.0;
        double max = -1.0E10;
        double min = 1.0E10;
        double stdev = 0.0;
        
        System.out.println("Enter 1 for keyboard input, 2 for file input: ");
        int mode = sc.nextInt();
        sc.nextLine();  // this consumes the '\n' that follows the integer from the preceding entry
        
        if (mode == 1)    // data entered from keyboard
        {
            System.out.println("When no more data is left to enter, simply hit return.");
            String s = "";
            int sLen = 1;

            do
            {
                System.out.printf("Data point %d: ", (n + 1));
                s = sc.nextLine();
                sLen = s.length();
                //System.out.println("You entered " + s + ", length " + sLen);
                if (sLen > 0)
                {
                    term = Double.parseDouble(s);
                    if (term > max)
                    {
                        max = term;
                    }
                    if (term < min)
                    {
                        min = term;
                    }
                    //dArr[n] = term;
                    dArrL.add(term);
                    n++;
                    sum += term;
                }
            } while (sLen > 0);
            if (n > 0)
            {
                mean = sum / (double) n;
                System.out.printf("For %d data point(s), \n", n);
                System.out.printf("    the maximum is %.2f\n", max);
                System.out.printf("    the minimum is %.2f\n", min);
                System.out.printf("    the mean (average) is %.2f\n", mean);
                if (n > 1)    // std dev is only defined if n > 1
                {
                    sum = 0.0;
                    for (int i = 0; i < n; i++)
                    {
                        //term = dArr[i] - mean;
                        term = dArrL.get(i) - mean;
                        sum += term * term;
                        stdev = Math.sqrt(sum / (double) (n - 1));
                    }
                    System.out.printf("    the std dev is %.2f\n", stdev);
                }
            }
            else
            {
                System.out.println("No data was entered.");
            }
        }
        else if (mode == 2)    // data read from file
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
Data point 1: 1
Data point 2: 2
Data point 3: 3
Data point 4: 4
Data point 5: 5
Data point 6: 6
Data point 7: 
For 6 data points, 
    the maximum is 6.00
    the minimum is 1.00
    the mean (average) is 3.50
    the std dev is 1.87

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 
1
When no more data is left to enter, simply hit return.
Data point 1: 2
Data point 2: 
For 1 data points, 
    the maximum is 2.00
    the minimum is 2.00
    the mean (average) is 2.00

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 
1
When no more data is left to enter, simply hit return.
Data point 1: 
No data was entered.

 ----jGRASP: operation complete.

*/
