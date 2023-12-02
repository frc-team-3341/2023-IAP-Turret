// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ProtoTurret;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Turret;

public class RobotContainer {
  public Turret turret = new Turret();

  public PhotonVision photonVision = new PhotonVision();
  public XboxController controller = new XboxController(Constants.USBOrder.Zero);
  public ProtoTurret protoTurret = new ProtoTurret(turret, photonVision, controller);
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return protoTurret;
  }
}
