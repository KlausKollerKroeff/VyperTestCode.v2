package frc.Java_Is_UnderControl.Vision.Cameras;

import org.photonvision.PhotonUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import frc.Java_Is_UnderControl.Logging.EnhancedLoggers.CustomPose3dLogger;
import frc.Java_Is_UnderControl.Vision.PhotonVision;

public class LeftArducam {
    PhotonVision vision;
    AprilTagFieldLayout aprilTagFieldLayout;
    Transform3d cameraToRobot;
    CustomPose3dLogger pose3dLog;

    double distanceToTarget = 0;

    public LeftArducam(){
        vision = new PhotonVision("Microsoft_LifeCam_HD-3000 (1)");
        pose3dLog = new CustomPose3dLogger("Robot 3D Position");
        aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
        cameraToRobot = new Transform3d(new Translation3d(-0.185, 0.272, 0.28), new Rotation3d(0, Units.degreesToRadians(-37.5), Units.degreesToRadians(70)));
    }

    public Pose3d robot3DPoseEstimation(){
        if (aprilTagFieldLayout.getTagPose(vision.getTarget().getFiducialId()).isPresent()) {
            Pose3d robot3DPose = PhotonUtils.estimateFieldToRobotAprilTag(vision.getTarget().getBestCameraToTarget(), aprilTagFieldLayout.getTagPose(vision.getTarget().getFiducialId()).get(), cameraToRobot);
            pose3dLog.appendDegrees(robot3DPose);
            return robot3DPose;
        } else {
            return null;
        }
    }

    public double getAngleToTarget(){
        return vision.getTarget().getYaw();
    }
}
