package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.List;


public class PhotonDetection extends CommandBase {
    public Turret turret;
    public PhotonCamera photonCamera = new PhotonCamera("Ayaans_iPhone_Camera");
    public boolean containsTargets;
    public PhotonPipelineResult result;
    public PhotonDetection(Turret turret) {
        this.turret = turret;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        turret.RotateTurret(0.7);
        result = photonCamera.getLatestResult();
        containsTargets = result.hasTargets();

        if (containsTargets){
            List<PhotonTrackedTarget> targets = result.getTargets();
            PhotonTrackedTarget bestTarget = result.getBestTarget();
            photonCamera.takeInputSnapshot();
            photonCamera.takeOutputSnapshot();
            turret.isDetected = "Yes";
        }

    }

    @Override
    public boolean isFinished() {
        return containsTargets;
    }

    @Override
    public void end(boolean interrupted) {
        turret.RotateTurret(0);
    }
}
