package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTablesSubsystem extends SubsystemBase {
    private NetworkTable table;

    public NetworkTablesSubsystem() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        inst.setServerTeam(3341); 
        table = inst.getTable("myTable");
    }

    // Methods to send/receive data to/from Raspberry Pi
    public void sendData(double data) {
        table.getEntry("dataFromRoboRIO").setDouble(data);
    }

    public double receiveData() {
        return table.getEntry("dataFromRaspberryPi").getDouble(0.0);
    }
}
