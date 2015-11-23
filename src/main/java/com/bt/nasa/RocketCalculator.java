package com.bt.nasa;

public class RocketCalculator {
  public double calculateQValue(double thrust) {
    return Math.sqrt((thrust - RocketConstants.DOWNWARD_FORCE ) / RocketConstants.K_CONSTANT);
  }

  public double calculateXValue(double thrust) {
    return 2 * RocketConstants.K_CONSTANT * calculateQValue(thrust) / RocketConstants.MASS;
  }

  public double calculateMotorBurnTime(double motorImpulse, double thrust) {
    return motorImpulse / thrust;
  }

  //TODO Refactor parameter positions so they are in the same order for all methods
  public double calculateBurnoutVelocity(double thrust, double motorImpulse) {
    double qValue = calculateQValue(thrust);
    double xValue = calculateXValue(thrust);
    double motorBurnTime = calculateMotorBurnTime(motorImpulse, thrust);

    double ePowerXTValue = calculateEPowerXTValue(xValue, motorBurnTime);

    return qValue * (1 - ePowerXTValue) / (1 + ePowerXTValue);
  }

  //TODO Refactor naming of eXT variables to get them consistent across methods and variable names
  protected double calculateEPowerXTValue(double xValue, double motorBurnTime) {
    return Math.exp(-xValue * motorBurnTime);
  }

  public double calculateBurnoutAltitude(double thrust, double motorImpulse) {
    double burnoutVelocity = calculateBurnoutVelocity(thrust, motorImpulse);

    //TODO This expression appears in other calculations above, so make it a private method
    double relativeThrust = thrust - RocketConstants.DOWNWARD_FORCE;

    double burnoutAltitudeThrust =
        (relativeThrust - (RocketConstants.K_CONSTANT * burnoutVelocity * burnoutVelocity)) / relativeThrust;

    double burnoutAltitude = -RocketConstants.MASS / (2 * RocketConstants.K_CONSTANT) * Math.log(burnoutAltitudeThrust);

    return burnoutAltitude;
  }
}
