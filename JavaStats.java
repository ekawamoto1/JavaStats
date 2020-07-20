import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import mypack.Dataset;

public class JavaStats
{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception
    {
        if (args.length > 0)    // run with a command-line argument: 1 for manual input, 2 for file input
        {
            JavaStatsGUIApp(args[0]);
        }
        else    // run without command-line arguments, or from IDE
        {
            JavaStatsConsoleApp();
        }
    }
    
    
    public static void JavaStatsGUIApp(String sMode) throws Exception
    {
        Dataset ds = new Dataset();
        String outStr = "";
        String fName = "";
        int n = 0;
        int mode = Integer.parseInt(sMode);
        
        switch (mode)
        {
            case 1:
                String s1 = "Enter values separated by commas: ";
                String s2 = JOptionPane.showInputDialog(null, s1, "Data entry", JOptionPane.OK_CANCEL_OPTION);
                //JOptionPane.showMessageDialog(null, s2);
                if (s2.length() == 0)
                {
                    outStr = "No data points to be analyzed.\n";
                }
                else
                {
                    String[] s0 = s2.split(",");    // returns 1 even if s2 is empty
                    n = s0.length;
                    if (n > 0)
                    {
                        for (int i = 0; i < n; i++)
                        {
                            int sLen = s0[i].length();
                            //System.out.println("You entered " + s + ", length " + sLen);
                            if (sLen > 0)
                            {
                                double term = Double.parseDouble(s0[i]);
                                ds.AddPoint(term);
                            }
                        }
      
                        outStr = PrintDataPoints(ds);
                        outStr += PrintOutStats(ds);     
                    }
                }
                JOptionPane.showMessageDialog(null, outStr, "Data and stats", JOptionPane.PLAIN_MESSAGE);                  
                break;
            case 2:
                JFileChooser j = new JFileChooser();
                j.setCurrentDirectory(new File(System.getProperty("user.dir")));
                
                // from https://www.geeksforgeeks.org/java-swing-jfilechooser/
                // requires import of javax.swing.filechooser.*
                // restrict the user to select files of all types 
                j.setAcceptAllFileFilterUsed(false); 
                // set a title for the dialog 
                j.setDialogTitle("Select a .txt file"); 
                // only allow files of .txt extension 
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt"); 
                j.addChoosableFileFilter(restrict);
                
                int result = j.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File f = j.getSelectedFile();
                    fName = f.getAbsolutePath();
                    ds = GetDataPointsFromFile(fName);
                    
                    outStr = PrintDataPoints(ds);
                    outStr += PrintOutStats(ds);                    
                }
                else
                {
                    outStr = "No file chosen.";
                }
                JOptionPane.showMessageDialog(null, outStr, "Data and stats", JOptionPane.PLAIN_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid mode; exiting program.", "Oops", JOptionPane.WARNING_MESSAGE);
                return;
        }
    }
    
    public static void JavaStatsConsoleApp() throws Exception
    {
        Dataset ds = new Dataset();

        System.out.print("Enter 1 for keyboard input, 2 for file input: ");
        int mode = sc.nextInt();
        sc.nextLine();  // this consumes the '\n' that follows the integer from the preceding call to sc.nextInt()
        
        if (mode == 1)    // data entered from keyboard
        {
            ds = GetDataPointsFromConsole();
        }
        else if (mode == 2)    // data read from file
        {
            ds = GetDataPointsFromFile("");
            System.out.print(PrintDataPoints(ds));
        }
        else
        {
            System.out.println("Invalid mode; exiting program.");
            return;
        }
        
        System.out.print(PrintOutStats(ds));
    }
    
    private static Dataset GetDataPointsFromConsole()
    {
        Dataset ds = new Dataset();
        int n = 0;
        int sLen;
        
        System.out.println("\nWhen no more data is left to enter, simply hit return.");
        do    // loop to get data entered from keyboard and store it in outArrL
        {
            System.out.printf("Data point %d: ", (n + 1));
            String s = sc.nextLine();
            sLen = s.length();
            //System.out.println("You entered " + s + ", length " + sLen);
            if (sLen > 0)
            {
                double term = Double.parseDouble(s);
                ds.AddPoint(term);
                n++;
            }
        } while (sLen > 0);  
        
        return ds;  
    }
    
    private static Dataset GetDataPointsFromFile(String fName) throws Exception
    {
        Dataset ds = new Dataset();
        if (fName.length() == 0)
        {
            System.out.print("\nEnter pathname of data file: ");
            fName = sc.nextLine();
        }
        File f = new File(fName);
        
        if (f.exists())
        {        
            Scanner scf = new Scanner(f);
            while (scf.hasNextLine())
            {
                String s = scf.nextLine();
                int sLen = s.length();
                if (sLen > 0)
                {
                    double term = Double.parseDouble(s);
                    ds.AddPoint(term);
                }
            }
            if (ds.GetNumPts() == 0)
            {
                System.out.printf("Data file %s is empty.\n", fName);
            }
        }
        else
        {
            System.out.printf("File %s does not exist.\n", fName);
        }
        
        return ds;
    }
    
    private static String PrintDataPoints(Dataset ds)
    {
        String outStr = "";
        int n = ds.GetNumPts();
        
        if (n > 0)
        {
            if (n < 10)    // print all data points
            {
                for (int i = 0; i < n; i++)
                {
                    outStr += String.format("Data point %d: %.2f\n", i + 1, ds.GetPoint(i));
                }
            }
            else    // just print first and last five data points
            {
                for (int i = 0; i < 5; i++)
                {
                    outStr += String.format("Data point %d: %.2f\n", i + 1, ds.GetPoint(i));
                }
                outStr += String.format("     ... \n");
                for (int i = n - 5; i < n; i++)
                {
                    outStr += String.format("Data point %d: %.2f\n", i + 1, ds.GetPoint(i));
                }                    
            }
        }
        
        return outStr;
    }
     
    private static String PrintOutStats(Dataset ds)
    {
        String outStr = "";
        int n = ds.GetNumPts();
        
        if (n > 0)
        {
            double[] minmax = ds.ComputeExtremes();
            double mean = ds.ComputeMean();
            outStr += String.format("\nFor %d data point(s), \n", n);
            outStr += String.format("    the maximum is %.2f\n", minmax[1]);
            outStr += String.format("    the minimum is %.2f\n", minmax[0]);
            outStr += String.format("    the mean (average) is %.2f\n", mean);
            if (n > 1)    // median and stdev are only defined if n > 1
            {
                double med = ds.ComputeMedian();
                outStr += String.format("    the median is %.2f\n", med);
                double stdev = ds.ComputeStdev();
                outStr += String.format("    the std dev is %.2f\n", stdev);
            }        
        }
        else
        {
            outStr += String.format("\nNo data points to be analyzed.\n");
        }
        
        return outStr;  
    }
    
}



/*
 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 1

When no more data is left to enter, simply hit return.
Data point 1: 7
Data point 2: 1
Data point 3: 6
Data point 4: 2
Data point 5: 5
Data point 6: 3
Data point 7: 4
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
Data file testdata3.txt is empty.

No data points to be analyzed.

 ----jGRASP: operation complete.

 ----jGRASP exec: java JavaStats
Enter 1 for keyboard input, 2 for file input: 2

Enter pathname of data file: testdata4.txt
File testdata4.txt does not exist.

No data points to be analyzed.

 ----jGRASP: operation complete.
*/
