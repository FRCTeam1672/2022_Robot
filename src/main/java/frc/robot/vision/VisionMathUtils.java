package frc.robot.vision;

import java.util.ArrayList;

import static frc.robot.Constants.Vision.CAMERA_IMG_WIDTH;

public class VisionMathUtils {
	/**
	 * Do not use this method. For some reason, it is not working.
	 *
	 * @param values The values to average
	 * @return standard deviation weighted mean
	 */
	@Deprecated()
	public static double getDeviationWeightedAverage(ArrayList<Double> values) {
		// stores unweighted mean
		double mean = getAverage(values);
		// stores standard deviation
		double standardDeviation = 0.0;
		// http://www.basic-mathematics.com/images/standard-deviation-formula.png
		// Summation of all (values subtracted by mean) squared
		for (double i : values) {
			standardDeviation += Math.pow((i - mean), 2);
		}
		// divided by number of values in the set
		standardDeviation /= values.size();
		// square root of result
		standardDeviation = Math.sqrt(standardDeviation);

		// calculates average with new bounds
		double average = 0.0;
		int numOfNonOutliers = 0;
		final double deviation = 1.2;
		for (double i : values) {
			if (((mean + standardDeviation) * deviation) > i && ((mean - standardDeviation) * deviation) < i) {
				average += i;
				numOfNonOutliers++;
			}
		}
		// divides by number of non-outliers
		average /= numOfNonOutliers;
		return average;
	}

	/**
	 *
	 * @param values The values to average
	 * @return The average of the values
	 */
	public static double getAverage(ArrayList<Double> values) {
		double average = 0.0;
		for (double i : values) {
			average += i;
		}
		average /= values.size();
		return average;
	}
	/** 
	 * Converts a pixel into the Aiming Coordinate System.
	 * This is very useful when calculating how much the robot should turn,
	 * and can be used for turning.
	 *
	 * If the turn amount is greater than 0.75, then the robot will forcefully only turn 0.75. This is to prevent the robot from turning too far.
	 * If the turn amount is less than 0.25, then the robot will forcefully only turn 0.25. This is to prevent issues of the robot not moving at all.
	 * @param pixel The pixel which you want to be coordinated 
	*/
	public static double pixelToRealWorld(double pixel){
		double realWorld = (pixel - (CAMERA_IMG_WIDTH / 2.0)) / (CAMERA_IMG_WIDTH / 2.0);
		//math (derogatory)
		//^^^ by Emilie

		if(realWorld > 0.75){
			//Forcefully make the robot not turn too far/fast
			realWorld = 0.75;
		}
		else if(realWorld < 0.25){
			//Forcefully make the robot not turn too little
			realWorld = 0.25;
		}
		return realWorld;
	}
}
