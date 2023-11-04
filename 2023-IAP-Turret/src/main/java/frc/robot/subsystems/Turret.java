package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

    public DigitalInput limitSwitch;
    public Constants c = new Constants();
    private final WPI_TalonSRX talon;

    public Turret() {
        talon = new WPI_TalonSRX(c.TalonPort);
        talon.setNeutralMode(NeutralMode.Coast);
        limitSwitch = new DigitalInput(c.LimitSwitchChannel);
    }

    public void rotateTurret(double talonSpeed){
        talon.set(talonSpeed);
    }

    public boolean getLimitValue(){
        return limitSwitch.get();
    }

}

