package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    public boolean manualControl = true;
    public XboxController controller;
    PIDController pid = new PIDController(0,0,0);

    public ProtoTurret(Turret turret, PhotonVision photonVision, XboxController controller) {
        this.controller = controller;
        this.turret = turret;
        this.photonVision = photonVision;
        addRequirements(this.turret, this.photonVision);
    }

    @Override
    public void initialize() {

//        while (turret.getLimitValue("l")){
//            turret.rotateTurret(0.3);
//        }
//        turret.rotateTurret(0);

//        //rotate positive to left, negative to right
//
//        turret.resetEncoders();
//
////        pid.setSetpoint(0.0);
//        while (turret.getTicks()*4 < 90){
//            turret.rotateTurret(-0.2);
//        }
    }

    public boolean checkManualControl(){
        boolean ManualControl;
        if (controller.getAButton()){
            ManualControl = true;
        }else{
            ManualControl = false;
        }
        return ManualControl;
    }

    @Override
    public void execute() {
        turret.resetEncoders();
        manualControl = checkManualControl();

        if (!manualControl) {
            while (!photonVision.targetExists()) { //All code should run while the target has not yet been found
                while (!turret.getLimitValue("r") && !turret.getLimitValue("l")) {
                    SmartDashboard.putNumber("Speed", turretSpeed);
                    SmartDashboard.putNumber("Sign", turretSign);
                    turret.rotateTurret(turretSpeed * turretSign);

                    double angle = turret.getTicks() * 4;
                    if ((0 <= angle && angle <= threshold) || (180 - threshold <= angle && angle <= 180)) {
                        turretSpeed = 0.1;
                    } else {
                        turretSpeed = 0.3;
                    }

                    if (checkManualControl()){
                        break;
                    }
                }
                if (checkManualControl()){
                    break;
                }
                turretSign *= -1;
                turret.rotateTurret(turretSpeed * turretSign);
            }
            if(photonVision.targetExists()){
                turret.rotateTurret(0);
                SmartDashboard.putBoolean("Target Exists: ", photonVision.targetExists());
            }
//            if(photonVision.targetExists()){
//
////                double speed = pid.calculate(photonVision.getYaw());
////                turret.rotateTurret(-speed);
//            }
        } else {
            double axis = controller.getRawAxis(0);
            SmartDashboard.putNumber("axis", axis);
            turret.rotateTurret(axis * -1.002);
        }
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        turret.rotateTurret(0);
    }


}
