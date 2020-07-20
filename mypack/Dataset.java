package mypack;
import java.util.*;

public class Dataset
{
    ArrayList<Double> dArrL = new ArrayList<>();
    int numPts = 0;
    double mean = 0.0;
    
    // constructor
    public Dataset(ArrayList<Double> inArrL)
    {
        this.dArrL = inArrL;
        this.numPts = inArrL.size();
    }
    
    // no-argument constructor
    public Dataset()
    {
        this.dArrL.clear();
        this.numPts = 0;
    }
    
    // methods
    public int GetNumPts()
    {
        numPts = dArrL.size();
        
        return numPts;
    }
    
    public void ClearArray()
    {
        dArrL.clear();
        
        return;
    }
    
    public void AddArray(ArrayList<Double> inArrL)
    {
        dArrL = inArrL;
        numPts = dArrL.size();
        
        return;
    }
    
    public ArrayList<Double> GetArray()
    {
        return dArrL;
    }
    
    public int AddPoint(double x)
    {
        dArrL.add((Double) x);
        numPts = dArrL.size();
        
        return numPts;
    }
    
    public int AddPoint(int x)
    {
        dArrL.add(Double.valueOf(x));
        numPts = dArrL.size();
        
        return numPts;
    }
    
    public double GetPoint(int i)
    {
        double x = 0.0;
        numPts = dArrL.size();
        if (i >= 0 && i < numPts)
        {
            x = dArrL.get(i);
        }
        
        return x;
    }
    
    public double[] ComputeExtremes()
    {
        numPts = dArrL.size();
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        double[] extremes = {0.0, 0.0};
        
        for (int i = 0; i < numPts; i++)
        {
            double x = dArrL.get(i);
            if (x < min)
            {
                min = x;
            }
            if (x > max)
            {
                max = x;
            }
        }
        extremes[0] = min;
        extremes[1] = max;
        
        return extremes;
    }
    
    public double ComputeMean()
    {
        numPts = dArrL.size();
        double sum = 0.0;
        
        if (numPts > 0)    // mean only defined for numPts > 0
        {
            for (int i = 0; i < numPts; i++)
            {
                double term = dArrL.get(i); 
                sum += term; 
            }
            
            mean = sum / (double) numPts;
        }
        
        return mean;    // returns 0 if numPts < 1
    }
    
    public double ComputeStdev()
    {
        numPts = dArrL.size();
        double sum = 0.0, stdev = 0.0;
        
        if (numPts > 1)    // stdev only defined for numPts > 1
        {
            for (int i = 0; i < numPts; i++)
            {
                double term = dArrL.get(i) - mean; 
                sum += term * term; 
            }
            
            stdev = Math.sqrt(sum / (double) (numPts - 1));
        }
        
        return stdev;    // returns 0 if numPts < 2
    }
    
    public double ComputeMedian()
    {
        numPts = dArrL.size();
        double median = 0.0;
        
        if (numPts > 1)    // median only defined for numPts > 1
        {            
            Collections.sort(dArrL);    // first, sort the data points
            int m0 = numPts / 2;
            if (numPts % 2 == 0)    // if even number of pts, take average of the two middles
            {
                median = (dArrL.get(m0 - 1) + dArrL.get(m0)) / 2.0;
            }
            else    // if odd number of pts, take the middle one
            {
                median = dArrL.get(m0);
            }
            /*
            for (int i = 0; i < n; i++)
            {
                System.out.printf("Sorted data point %d: %.2f\n", i + 1, dArrL.get(i));
            }
            */
        }
        
        return median;    // returns 0 if numPts < 2
    }
    
}



