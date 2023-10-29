package frc.robot;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Pipelines.ConeGripPipeline;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.vision.VisionThread;

public class Robot extends TimedRobot {

  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;

  private VisionThread visionThread;
  private double centerX = 0.0;
  private DifferentialDrive drive;
  private PWMSparkMax left;
  private PWMSparkMax right;

  private final Object imgLock = new Object();
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
      UsbCamera camera = CameraServer.startAutomaticCapture();
      camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
  
      // Creates a new vision thread to run a given Computer Vision pipeline
      visionThread = new VisionThread(camera, new ConeGripPipeline(), pipeline -> {
          if (!pipeline.findContoursOutput().isEmpty()) {
              Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
              synchronized (imgLock) {
                  centerX = r.x + (r.width / 2);
              }
          }
      });
      visionThread.start();
  
      left = new PWMSparkMax(0);
      right = new PWMSparkMax(1);
      drive = new DifferentialDrive(left, right);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}