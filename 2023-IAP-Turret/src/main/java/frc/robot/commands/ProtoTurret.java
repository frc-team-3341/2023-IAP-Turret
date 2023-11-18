package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Turret;


public class ProtoTurret extends CommandBase {
    public Turret turret;
    public PhotonVision photonVision;
    public Constants c = new Constants();
    public int turretSign = 1;
    public double turretSpeed = 0.2;
    //0.2 is the max speed
    public int threshold = 10;
    //start slowing down turret 10 degrees from limit switch

    public ProtoTurret(Turret turret, PhotonVision photonVision) {
        this.turret = turret;
        this.photonVision = photonVision;
        addRequirements();
    }

    @Override
    public void initialize() {
        while (!turret.getLimitValue("l")){
            turret.rotateTurret(-0.2);
        }
        turret.resetEncoders();
        turret.setAngle(22.5);
    }

    @Override
    public void execute() {
        while (!photonVision.targetExists()){ //All code should run while the target has not yet been found
            while (!turret.getLimitValue("r") && !turret.getLimitValue("l")) {
                turret.rotateTurret(turretSpeed * turretSign);
                double angle = turret.getTicks() * 4;
                if ((0 <= angle && angle <= threshold) || (180-threshold <= angle && angle <= 180)){
                    turretSpeed = 0.05;
                }else{
                    turretSpeed = 0.2;
                }
            }
            turretSign *= -1;
        }
    }

    @Override
    public boolean isFinished() {
        return photonVision.targetExists();
    }

    @Override
    public void end(boolean interrupted) {
        turret.rotateTurret(0);
    }
}
