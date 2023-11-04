// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.PhotonDetection;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Turret;

public class RobotContainer {
  public Turret turret = new Turret();
  public PhotonVision photonVision = new PhotonVision();
  public PhotonDetection photonDetection = new PhotonDetection(turret, photonVision);
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return photonDetection;
  }
}
