package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
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
    public Joystick joy;
    PIDController pid = new PIDController(0,0,0);

    public ProtoTurret(Turret turret, PhotonVision photonVision, Joystick joy) {
        this.joy = joy;
        this.turret = turret;
        this.photonVision = photonVision;
        addRequirements();
    }

    @Override
    public void initialize() {
//        while (!turret.getLimitValue("l")){
//            turret.rotateTurret(-0.2);
//        }
//        turret.resetEncoders();
//        pid.setSetpoint(0.0);
//        turret.setAngle(22.5);
    }

    @Override
    public void execute() {
        if (joy.getRawButtonReleased(2)){
            manualControl = false;
        }else{
            manualControl = true;
        }

        if (!manualControl) {
            while (!photonVision.targetExists()) { //All code should run while the target has not yet been found
                while (!turret.getLimitValue("r") && !turret.getLimitValue("l")) {
                    int setPoint = 0;
                    if (turretSign == 1){
                        setPoint = 180;
                    }
                    double calc = pid.calculate(turret.getCenterEncoderDistance(), setPoint);
                    //turretSpeed * turretSign
                    turret.rotateTurret(calc);


                    double angle = turret.getTicks() * 4;
                    if ((0 <= angle && angle <= threshold) || (180 - threshold <= angle && angle <= 180)) {
                        turretSpeed = 0.05;
                    } else {
                        turretSpeed = 0.2;
                    }
                }
                turretSign *= -1;
            }
        } else {
            double axis = joy.getRawAxis(0);
            turret.rotateTurret(axis*1.15);
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
