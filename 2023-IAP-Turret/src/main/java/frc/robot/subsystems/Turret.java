package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

    public DigitalInput limitSwitch;

    private final WPI_TalonSRX talon;
    private final int TalonPort = 0;
    private final int LimitSwitchPort = 0;

    public Turret() {
        talon = new WPI_TalonSRX(TalonPort);
        talon.setNeutralMode(NeutralMode.Coast);
        limitSwitch = new DigitalInput(LimitSwitchPort);
    }

    public void rotateTurret(double talonSpeed){
        talon.set(talonSpeed);
    }
    public boolean getLimitValue(){
        return limitSwitch.get();
    }

}

