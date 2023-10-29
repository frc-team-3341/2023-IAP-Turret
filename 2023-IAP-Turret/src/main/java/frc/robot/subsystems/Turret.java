package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.

    private final WPI_TalonSRX talon;
    public String isDetected;
    private ShuffleboardTab DTTab = Shuffleboard.getTab("Turret");
    private GenericEntry objectDetected = DTTab.add("ObjectDetected", "No").getEntry();
    private final int talonPort = 0;

    public Turret() {

        talon = new WPI_TalonSRX(talonPort);
        //?? v
        talon.setNeutralMode(NeutralMode.Coast);
        //Default turn is counter-clockwise
        //Don't we need gear-ratios?
    }

    public void RotateTurret(double talonSpeed){
        talon.set(talonSpeed);
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("ObjectDetected", isDetected);
    }
}

