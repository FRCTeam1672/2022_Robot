package frc.robot.vision;

import java.util.ArrayList;

public class VisionMathUtils {
    /**
     * @param values
     * @return standard deviation weighted mean
     */
    public static double getDeviationWeightedAverage(ArrayList<Double> values) {
        //stores unweighted mean
        double mean = getAverage(values);
        //stores standard deviation
        double standardDeviation = 0.0;
        //http://www.basic-mathematics.com/images/standard-deviation-formula.png
        //Summation of all (values subtracted by mean) squared
        for (double i : values) {
            standardDeviation += Math.pow((i - mean), 2);
        }
        //divided by number of values in the set
        standardDeviation /= values.size();
        //square root of result
        standardDeviation = Math.sqrt(standardDeviation);

        //calculates average with new bounds
        double average = 0.0;
        int numOfNonOutliers = 0;
        for (double i : values) {
            //outlier is a data point outside of 1.5 times standard deviation from the mean
            if (mean + standardDeviation * 1.5 > i && mean - standardDeviation * 1.5 < i) {
                average += i;
                numOfNonOutliers++;
            }
        }
        //divides by number of non-outliers
        average /= numOfNonOutliers;
        return average;
    }

    //returns mean
    public static double getAverage(ArrayList<Double> values) {
        //stores mean
        double average = 0.0;
        //adds all values together
        for (double i : values) {
            average += i;
        }
        //divides by number of values in the set
        average /= values.size();
        return average;
    }
}
