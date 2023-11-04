package frc.robot.subsystems;


import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;


public class PhotonVision extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.
    public PhotonCamera photonCamera;
    public PhotonPipelineResult result;
    public PhotonTrackedTarget target;
    public static boolean hasTarget;
    public static double yaw;
    public static double pitch;
    public static double area;
    public static double skew;
    public PhotonVision() {
        photonCamera = new PhotonCamera("Microsoft_LifeCam_HD-3000");
        PortForwarder.add(5800, "photonvision", 5800);
        photonCamera.setPipelineIndex(0);
    }

    public boolean targetExists(){
        return hasTarget;
    }
    public double getYaw(){
        return yaw;
    }
    public double getPitch(){
        return pitch;
    }
    public double getArea(){
        return area;
    }
    public double getSkew(){
        return skew;
    }

    @Override
    public void periodic() {
        result = photonCamera.getLatestResult();
        hasTarget = result.hasTargets();
        if (hasTarget){
            target = result.getBestTarget();
            yaw = target.getYaw();
            pitch = target.getPitch();
            area = target.getArea();
            skew = target.getSkew();
            //Skew doesn't work with April-Tags
        }

        SmartDashboard.putNumber("Yaw", yaw);
        SmartDashboard.putNumber("Pitch", pitch);
        SmartDashboard.putNumber("Area", area);
        SmartDashboard.putNumber("Skew", skew);

    }
}

