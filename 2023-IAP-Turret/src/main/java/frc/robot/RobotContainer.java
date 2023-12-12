// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Commands.RIOCenterToTargetCone;
import frc.robot.Subsystems.DriveTrain;
import frc.robot.Subsystems.RoboRIOVision;

public class RobotContainer {

  private final DriveTrain dt = new DriveTrain();
  private final RoboRIOVision RRVision = new RoboRIOVision();
  private final RIOCenterToTargetCone RCTTC = new RIOCenterToTargetCone(dt, RRVision);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null;
    //return RCTTC;
  }
}
