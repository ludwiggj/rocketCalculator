package com.bt.nasa;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RocketHeightCalculatorTest {

  public static final double DELTA = 0.0001;

  @Test
  public void kConstantShouldBeCorrect() throws Exception {
    double expectedKConstant = 0.0005750775;

    assertEquals(RocketConstants.K_CONSTANT, expectedKConstant, DELTA);
  }


  @Test
  public void shouldCalculateQConstantCorrectly() throws Exception {
    double thrust = RocketConstants.DOWNWARD_FORCE + RocketConstants.K_CONSTANT;

    double qValue = new RocketCalculator().calculateQValue(thrust);
    double expectedThrust = 1;

    assertEquals(expectedThrust, qValue, DELTA);
  }

  @Test
  public void shouldCalculateXCorrectly() throws Exception {
    double thrust = RocketConstants.DOWNWARD_FORCE + RocketConstants.K_CONSTANT;

    RocketCalculator rocketCalculator = new RocketCalculator();
    double qValue = rocketCalculator.calculateQValue(thrust);
    double xValue = rocketCalculator.calculateXValue(thrust);

    double expectedXValue = (2 * RocketConstants.K_CONSTANT * qValue) / RocketConstants.MASS;
    assertEquals(expectedXValue, xValue, DELTA);
  }

  @Test
  public void shouldCalculateMotorBurnTimeCorrectly() throws Exception {
    double motorImpulse = 2;
    double thrust = 4;

    double motorBurnTime = new RocketCalculator().calculateMotorBurnTime(motorImpulse, thrust);
    double expectedMotorBurnTime = 0.5;

    assertEquals(expectedMotorBurnTime, motorBurnTime, DELTA);
  }

  @Test
  public void shouldCalculateBurnoutVelocity() throws Exception {
    final double extValue = 0.5;

    double dummyThrust = 1;
    double dummyMotorImpulse = 1;

    RocketCalculator rocketCalculator = new RocketCalculator() {
      @Override
      protected double calculateEPowerXTValue(double xValue, double motorBurnTime) {
        return extValue;
      }
    };

    double burnoutVelocity = rocketCalculator.calculateBurnoutVelocity(dummyThrust, dummyMotorImpulse);

    double expectedBurnoutVelocity = rocketCalculator.calculateQValue(dummyThrust) * 0.5 / 1.5;

    assertEquals(expectedBurnoutVelocity, burnoutVelocity, DELTA);
  }

  @Test
  public void shouldCalculateEToPowerOfXT() throws Exception {
    double xValue = 1;
    double motorBurnTime = 2;

    double eXT = new RocketCalculator().calculateEPowerXTValue(xValue, motorBurnTime);

    double expected_eXT = 0.13533;

    assertEquals(expected_eXT, eXT, DELTA);
  }

  //TODO This test fails, need to calculate the actual expected result and plug it in
  @Test
  public void shouldCalculateBurnoutAltitude() throws Exception {
    double expectedBurnoutAltitude = 50;
    double thrust = 1;
    double motorImpulse = 1;

    double burnoutAltitude = new RocketCalculator().calculateBurnoutAltitude(thrust, motorImpulse);

    assertEquals(expectedBurnoutAltitude, burnoutAltitude, DELTA);
  }
}