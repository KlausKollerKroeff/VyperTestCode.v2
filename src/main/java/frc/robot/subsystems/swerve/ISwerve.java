package frc.robot.subsystems.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface ISwerve {
    void driveAlignAngleButton();
    void driveAlignAngleTarget(Rotation2d angleToTarget, ChassisSpeeds chassisSpeeds);
}