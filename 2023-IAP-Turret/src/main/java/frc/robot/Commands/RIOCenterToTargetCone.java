package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.DriveTrain;
import frc.robot.Subsystems.RoboRIOVision;

public class RIOCenterToTargetCone extends CommandBase {
  private final DriveTrain dt;
  private final RoboRIOVision roboRIOVision;

  public RIOCenterToTargetCone(DriveTrain dt, RoboRIOVision roboRIOVision) {
    this.dt = dt;
    this.roboRIOVision = roboRIOVision;
    addRequirements(dt, roboRIOVision);
  }

  @Override
  public void initialize() {
    dt.tankDrive(0,0);
  }

  @Override
  public void execute() {
    double centerX;
    // The synchronized block gets a snapshot of the last reading of centerX in the vision thread
    synchronized (roboRIOVision.getImgLock()) {
      centerX = this.roboRIOVision.getCenterX();
    }
    // As the robot gets closer to the centerX, the turn var gets smaller and goes to 0
    double turn = centerX - (roboRIOVision.getCenterX() / 2);
    dt.tankDrive(-0.3, turn * 0.005);
  }

  @Override
  public void end(boolean interrupted) {
    dt.tankDrive(0,0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
