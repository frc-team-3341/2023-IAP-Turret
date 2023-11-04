// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Subsystems.DriveTrain;

public class TankDrive extends CommandBase {
  public DriveTrain dt;
  public Joystick joy;

  public TankDrive(DriveTrain dt, Joystick joy) {
    this.dt = dt;
    this.joy = joy;
    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    dt.tankDrive(0,0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPowerRaw = joy.getRawAxis(Constants.DrivePorts.LeftJoystickPort);
    double rightPowerRaw = joy.getRawAxis(Constants.DrivePorts.RightJoystickPort);
    dt.tankDrive(leftPowerRaw*0.3, rightPowerRaw*0.3);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    dt.tankDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
