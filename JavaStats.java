import java.util.*;
import java.io.*;

public class JavaStats
{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception
    {
        ArrayList<Double> dArrL = new ArrayList<>();    // initialize empty ArrayList
        int n = 0;
        String s = "";
        int sLen;
        
        System.out.print("Enter 1 for keyboard input, 2 for file input: ");
        int mode = sc.nextInt();
        sc.nextLine();  // this consumes the '\n' that follows the integer from the preceding call to sc.nextInt()
        
        if (mode == 1)    // data entered from keyboard
        {
            System.out.println("When no more data is left to enter, simply hit return.");

            do    // loop to get data entered from keyboard and store it in dArrL
            {
                System.out.printf("Data point %d: ", (n + 1));
                s = sc.nextLine();
                sLen = s.length();
                //System.out.println("You entered " + s + ", length " + sLen);
                if (sLen > 0)
                {
                    double term = Double.parseDouble(s);
                    dArrL.add(term);
                    n++;
                }
            } while (sLen > 0);
        }
        else if (mode == 2)    // data read from file
        {
            System.out.print("Enter pathname of data file: ");
            String fName = sc.nextLine();
            File f = new File(fName);
            if (f.exists())
            {
                Scanner scf = new Scanner(f);
                while (scf.hasNextLine())
                {
                    s = scf.nextLine();
                    sLen = s.length();
                    if (sLen > 0)
                    {
                        double term = Double.parseDouble(s);
                        dArrL.add(term);
                        n++;
                    }
                }
                PrintDataPoints(dArrL);
            }
            else
            {
                System.out.printf("File %s does not exist.\n", fName);
                return;
            }
        }
        else
        {
            System.out.println("Invalid mode; exiting program.");
            return;
        }
        
        PrintOutStats(dArrL);
    }
    
    
    private static void PrintDataPoints(ArrayList<Double> inArr)
    {
        int n = inArr.size();
        
        if (n > 0)
        {
            if (n < 10)    // print all data points
            {
                for (int i = 0; i < n; i++)
                {
                    System.out.printf("Data point %d: %.2f\n", i + 1, inArr.get(i));
                }
            }
            else    // just print first and last five data points
            {
                for (int i = 0; i < 5; i++)
                {
                    System.out.printf("Data point %d: %.2f\n", i + 1, inArr.get(i));
                }
                System.out.println("     ... ");
                for (int i = n - 5; i < n; i++)
                {
                    System.out.printf("Data point %d: %.2f\n", i + 1, inArr.get(i));
                }                    
            }
        }
        
        return;
    }
    
    private static double[] ComputeExtremes(ArrayList<Double> inArr)
    {
        double[] extremes = {0.0, 0.0};
        int n = inArr.size();
        
        if (n > 0)
        {
            double max = -1.0E10;
            double min = 1.0E10;
            double term;
            for (int i = 0; i < n; i++)
            {
                term = inArr.get(i);
                if (term > max)
                {
                    max = term;
                }
                if (term < min)
                {
                    min = term;
                }
            }
            extremes[0] = min;
            extremes[1] = max;
        }
        
        return extremes;
    }
    
    private static double ComputeMean(ArrayList<Double> inArr)
    {
        double mean = 0.0;
        int n = inArr.size();
        
        if (n > 0)
        {
            double sum = 0.0;
            for (int i = 0; i < n; i++)
            {
                sum += inArr.get(i);
            }
            mean = sum / (double) n;
        }
        
        return mean;    // returns 0 if n < 1
    }

    private static double ComputeStdev(ArrayList<Double> inArr, double mean)
    {
        double stdev = 0.0;
        int n = inArr.size();
        
        if (n > 1)
        {
            double sum = 0.0;
            double term = 0.0;
            for (int i = 0; i < n; i++)
            {
                term = inArr.get(i) - mean;
                sum += term * term;
            }
            stdev = Math.sqrt(sum / (double) (n - 1));
        }

        return stdev;    // returns 0 if n < 2
    }
    
    private static double ComputeMedian(ArrayList<Double> inArr)
    {
        double median = 0.0;
        int n = inArr.size();
        
        if (n > 1)
        {            
            Collections.sort(inArr);
            int m0 = n / 2;
            if (n % 2 == 0)
            {
                median = (inArr.get(m0 - 1) + inArr.get(m0)) / 2.0;
            }
            else
            {
                median = inArr.get(m0);
            }
            /*
            for (int i = 0; i < n; i++)
            {
                System.out.printf("Sorted data point %d: %.2f\n", i + 1, inArr.get(i));
            }
            */
        }
        
        return median;
    }
    
    private static void PrintOutStats(ArrayList<Double> inArr)
    {
        int n = inArr.size();
        
        if (n > 0)
        {
            double[] minmax = ComputeExtremes(inArr);
            double mean = ComputeMean(inArr);
            System.out.printf("For %d data point(s), \n", n);
            System.out.printf("    the maximum is %.2f\n", minmax[1]);
            System.out.printf("    the minimum is %.2f\n", minmax[0]);
            System.out.printf("    the mean (average) is %.2f\n", mean);
            if (n > 1)    // std dev is only defined if n > 1
            {
                double med = ComputeMedian(inArr);
                System.out.printf("    the median is %.2f\n", med);
                double stdev = ComputeStdev(inArr, mean);
                System.out.printf("    the std dev is %.2f\n", stdev);
            }        
        }
        else
        {
            System.out.println("No data points to be analyzed.");
        }    
    }
    
}

/*
 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 1
When no more data is left to enter, simply hit return.
Data point 1: 6
Data point 2: 1
Data point 3: 5
Data point 4: 2
Data point 5: 4
Data point 6: 3
Data point 7: 
For 6 data point(s), 
    the maximum is 6.00
    the minimum is 1.00
    the mean (average) is 3.50
    the median is 3.50
    the std dev is 1.87

 ----jGRASP: operation complete.
 
 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 1
When no more data is left to enter, simply hit return.
Data point 1: 7
Data point 2: 6
Data point 3: 5
Data point 4: 4
Data point 5: 3
Data point 6: 2
Data point 7: 1
Data point 8: 
For 7 data point(s), 
    the maximum is 7.00
    the minimum is 1.00
    the mean (average) is 4.00
    the median is 4.00
    the std dev is 2.16

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 1
When no more data is left to enter, simply hit return.
Data point 1: 3.4
Data point 2: 
For 1 data point(s), 
    the maximum is 3.40
    the minimum is 3.40
    the mean (average) is 3.40

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 1
When no more data is left to enter, simply hit return.
Data point 1: 
No data points to be analyzed.

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 2
Enter pathname of data file: testdata1.txt
Data point 1: 60.00
Data point 2: 62.00
Data point 3: 57.00
Data point 4: 58.00
Data point 5: 68.00
     ... 
Data point 6: 65.00
Data point 7: 63.00
Data point 8: 59.00
Data point 9: 60.00
Data point 10: 58.00
For 10 data point(s), 
    the maximum is 68.00
    the minimum is 57.00
    the mean (average) is 61.00
    the median is 60.00
    the std dev is 3.50

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 2
Enter pathname of data file: testdata2.txt
Data point 1: 23.50
For 1 data point(s), 
    the maximum is 23.50
    the minimum is 23.50
    the mean (average) is 23.50

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 2
Enter pathname of data file: testdata3.txt
No data points to be analyzed.

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 2
Enter pathname of data file: testdata4.txt
File testdata4.txt does not exist.

 ----jGRASP: operation complete.
 */
