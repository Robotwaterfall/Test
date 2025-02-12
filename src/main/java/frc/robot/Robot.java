// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  private TalonSRX LeftMasterMotor1 = new TalonSRX(18);
  private VictorSP LeftMasterMotor2 = new VictorSP(19);
  private VictorSP RightMasterMotor1 = new VictorSP(2);
  private TalonSRX RightMasterMotor2 = new TalonSRX(1);


  private Joystick joy1 = new Joystick(0);

  private double startTime;

  private Spark LeftIntakeMotor = new Spark(4);
  private Spark RightIntakeMotor = new Spark(5);

  private Spark ElevatorMotor = new Spark(6);


  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();

if (time - startTime < 3) {
    LeftMasterMotor1.set(ControlMode.PercentOutput, 0.5); //drive forward for 3 seconds at 50% speed
    LeftMasterMotor2.set(0.5);
    RightMasterMotor1.set(-0.5);
    RightMasterMotor2.set(ControlMode.PercentOutput, -0.5);
  } else {
    LeftMasterMotor1.set(ControlMode.PercentOutput, 0); //stop if time is over 3 seconds
    LeftMasterMotor2.set(0);
    RightMasterMotor1.set(0);
    RightMasterMotor2.set(ControlMode.PercentOutput, 0);
    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    double speed = -joy1.getRawAxis(1)*0.6;
    double turn = joy1.getRawAxis(4)*0.3;

    double left = speed + turn;  //calculate left and right motor speeds
    double right = speed - turn;

    LeftMasterMotor1.set(ControlMode.PercentOutput, left); //set motor speeds
    LeftMasterMotor2.set(left);
    RightMasterMotor1.set(-right);
    RightMasterMotor2.set(ControlMode.PercentOutput, -right);

    if (joy1.getRawButton(1)) { //intake
      LeftIntakeMotor.set(0.5);
      RightIntakeMotor.set(-0.5);
    } else {
      LeftIntakeMotor.set(0);
      RightIntakeMotor.set(0);
    }

    if (joy1.getRawButton(2)) { //elevator motor open
      ElevatorMotor.set(0.5);
    } else {
      ElevatorMotor.set(0);
    }

    if (joy1.getRawButton(3)) { //elevator motor close
      ElevatorMotor.set(-0.5);
    } else {
      ElevatorMotor.set(0);
    }

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}