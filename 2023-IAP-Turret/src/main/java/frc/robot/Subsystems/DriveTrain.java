// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {

  private final WPI_TalonSRX leftDriveTalon;
  private final WPI_TalonSRX rightDriveTalon;

  public DriveTrain() {
    // Creates talon objects
    leftDriveTalon = new WPI_TalonSRX(Constants.DriveTrainPorts.LeftDriveTalonPort);
    rightDriveTalon = new WPI_TalonSRX(Constants.DriveTrainPorts.RightDriveTalonPort);

    // Sets default mode for talon objects
    leftDriveTalon.setNeutralMode(NeutralMode.Coast);
    rightDriveTalon.setNeutralMode(NeutralMode.Coast);

    // Sets whether or not the talons in inverted directions
    leftDriveTalon.setInverted(true);
    rightDriveTalon.setInverted(false);

    // True means the sensor reads in the opposite direction of the motor
    // False means the sensor reads in the default direction of the motor
    leftDriveTalon.setSensorPhase(true); 
    rightDriveTalon.setSensorPhase(true);

    // Initialize encoders
    leftDriveTalon.configFactoryDefault();
    leftDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    rightDriveTalon.configFactoryDefault();
    rightDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
  }

  public void tankDrive(double leftSpeed, double rightSpeed){
    leftDriveTalon.set(leftSpeed);
    rightDriveTalon.set(rightSpeed);
  }

  public void resetEncoders(){
    leftDriveTalon.setSelectedSensorPosition(0,0,10);
    rightDriveTalon.setSelectedSensorPosition(0,0,10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
