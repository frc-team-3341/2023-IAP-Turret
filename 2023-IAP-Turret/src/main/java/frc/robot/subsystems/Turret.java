package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.opencv.core.Mat;

import java.util.Objects;

public class Turret extends SubsystemBase {
    public Constants c = new Constants();
    public final WPI_TalonSRX talon;
    //https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/encoders-software.html
    public Encoder encoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);

    public Turret() {
        encoder.reset();

        talon = new WPI_TalonSRX(c.TalonPort);

        talon.configFactoryDefault();
        talon.setInverted(false);
        talon.setNeutralMode(NeutralMode.Coast);


        talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 4);
        talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 4);
        talon.configForwardSoftLimitEnable(false);
        talon.configReverseSoftLimitEnable(false);
    }

    public void rotateTurret(double talonSpeed){
        talon.set(talonSpeed);
    }

//    public void rotateDegrees(int degreeVal){
//        double ticksPerRev = (c.smallWheelDiameter * 3.14) / c.encoderTicksPerRev;
//        double currentTicks = getTicks();
//
//    }
    public double getCenterEncoderDistance(){return encoder.getDistance();}
    public void setAngle(double ticks){talon.setSelectedSensorPosition(ticks);}
    //1 tick per 4 degrees
    public double getAngle(){
        return talon.getSelectedSensorPosition(0);
    }

    public void resetEncoders(){    //Gear ration ~5.75:1
        talon.setSelectedSensorPosition(0, 0, 10);
        encoder.reset();
    }

    public double getTicks() {
        return talon.getSelectedSensorPosition(0);
    }

    public boolean getLimitValue(String limitSwitch){
        //Accept "r" or "l"
        if (Objects.equals(limitSwitch, "r")){
            return talon.isRevLimitSwitchClosed() == 1;
        }else if (Objects.equals(limitSwitch, "l")){
            return talon.isFwdLimitSwitchClosed() == 1;
        }
        return false;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Ticks", getTicks());
        SmartDashboard.putBoolean("Left_Limit", getLimitValue("l"));
        SmartDashboard.putBoolean("Right_Limit", getLimitValue("r"));
        SmartDashboard.putNumber("Raw Angle", talon.getSelectedSensorPosition());
    }
}

