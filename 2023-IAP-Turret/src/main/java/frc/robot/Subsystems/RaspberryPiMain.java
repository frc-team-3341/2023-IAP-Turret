package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RaspberryPiMain {
    public static void main(String[] args) {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        inst.setServerTeam(3341); 
        NetworkTable table = inst.getTable("myTable");

        // Receive data from RoboRIO
        double receivedData = table.getEntry("dataFromRoboRIO").getDouble(0.0);
        System.out.println("Received data from RoboRIO: " + receivedData);

        // Send data to RoboRIO
        double dataToSend = 42.0;
        table.getEntry("dataFromRaspberryPi").setDouble(dataToSend);
    }
}
