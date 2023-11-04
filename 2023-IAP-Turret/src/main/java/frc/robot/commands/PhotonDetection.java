package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Turret;


public class PhotonDetection extends CommandBase {
    public Turret turret;
    public PhotonVision photonVision;
    public int turretSpeed = 1;

    public PhotonDetection(Turret turret, PhotonVision photonVision) {
        this.turret = turret;
        this.photonVision = photonVision;
        addRequirements();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
       if (turret.getLimitValue()){
           turretSpeed *= -1;
       }
       turret.rotateTurret(turretSpeed * 0.7);
    }

    @Override
    public boolean isFinished() {
        return photonVision.targetExists();
    }

    @Override
    public void end(boolean interrupted) {
        turret.rotateTurret(0.0);
    }
}
