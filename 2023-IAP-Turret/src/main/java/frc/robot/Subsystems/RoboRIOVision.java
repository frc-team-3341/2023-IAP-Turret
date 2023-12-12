package frc.robot.Subsystems;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Pipelines.ConeGripPipeline;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RoboRIOVision extends SubsystemBase{
  // **TO BE DONE**: change these values according to our robot
  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;
  private VisionThread visionThread;
  public static double centerX = 0.0;
  private final Object imgLock = new Object();

  public RoboRIOVision() {
    UsbCamera camera = CameraServer.startAutomaticCapture();
    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
  
    // Creates a new vision thread to run a given Computer Vision pipeline
    visionThread = new VisionThread(camera, new ConeGripPipeline(), pipeline -> {
    if (!pipeline.findContoursOutput().isEmpty()) {
        // Creates a rectangle around the target and gets the x coordinate of the center of rectangle
        Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
        synchronized (imgLock) {
        centerX = r.x + (r.width / 2);
        SmartDashboard.putNumber("centerX", centerX);
        }
    }
    });
    visionThread.start();

    // PortForwarder.add(8888, "wpilibpi.local", 80);
    PortForwarder.add(8888, "frcvision.local", 80);

  }

  public Object getImgLock(){
    return imgLock;
  }

  public double getCenterX(){
    return centerX;
  }

  public int getIMG_WIDTH(){
    return IMG_WIDTH;
  }
}
