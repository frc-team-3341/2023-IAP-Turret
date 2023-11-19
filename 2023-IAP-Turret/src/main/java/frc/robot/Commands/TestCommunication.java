package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.NetworkTablesSubsystem;

public class TestCommunication extends CommandBase {
    private final NetworkTablesSubsystem networkTablesSubsystem;

    public TestCommunication(NetworkTablesSubsystem networkTablesSubsystem) {
        this.networkTablesSubsystem = networkTablesSubsystem;
        addRequirements(networkTablesSubsystem);
    }

    @Override
    public void initialize() {
        // Use NetworkTablesSubsystem methods to send/receive data
        double dataToSend = 123.45;
        networkTablesSubsystem.sendData(dataToSend);

        double receivedData = networkTablesSubsystem.receiveData();
        System.out.println("Received data in command: " + receivedData);
    }

    @Override
    public void execute() {
        // Additional execution logic if needed
    }

    @Override
    public boolean isFinished() {
        return true; // Modify as needed
    }
}
