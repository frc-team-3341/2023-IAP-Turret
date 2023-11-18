package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Turret;


public class PhotonDetection extends CommandBase {
    public Turret turret;
    public PhotonVision photonVision;
    public Constants c = new Constants();
    public int turretSpeed = 1;

    public PhotonDetection(Turret turret, PhotonVision photonVision) {
        this.turret = turret;
        this.photonVision = photonVision;
        addRequirements();
    }

    @Override
    public void initialize() {
        //Initialize the turret position. Move until it hits a limit switch
        while (turret.getLimitValue("r")){
            turret.rotateTurret(-c.DefaultTurretSpeed);
        }
        //Reset encoder at limit switch position
        turret.resetEncoders();
    }

    @Override
    public void execute() {
       if (!turret.getLimitValue("r")){
           turretSpeed *= -1;
       }
       turret.rotateTurret(turretSpeed * c.DefaultTurretSpeed);
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
