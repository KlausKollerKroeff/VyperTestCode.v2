package frc.Java_Is_UnderControl.Vision.Cameras;

import org.photonvision.PhotonUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
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

    public Pose2d robot2dPoseEstimation(){
        Pose2D robotPose = PhotonUtils.estimateFieldToRobot(kCameraHeight, kTargetHeight, kCameraPitch, Rotation2d.fromDegrees(vision.targetPositionY()), Rotation2d.fromDegrees(-vision.targetPositionX()), gyro.getRotation2d(), targetPose, cameraToRobot);
        return robot2DPose;
    }

    public double getAngleToTarget(){
        return vision.getTarget().getYaw();
    }
}
