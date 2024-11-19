package frc.robot.subsystems.swerve;

import java.io.File;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Java_Is_UnderControl.Logging.EnhancedLoggers.CustomStringLogger;
import frc.robot.Joysticks.ControlBoard;
import frc.robot.constants.SwerveConstants;

public class SwerveSubsystem extends SubsystemBase implements ISwerve{

    SwerveDrive  swerveDrive;
    ControlBoard controller = ControlBoard.getInstance();
    CustomStringLogger stringLogger = new CustomStringLogger("Robot State");
    private String state = "ROBOT_NOT_MOVING";
    File directory;
    private static SwerveSubsystem swerveInstance = null;
    private ChassisSpeeds desiredSpeeds;
    Pose2d targetAimPose;

    public static SwerveSubsystem getInstance() {
        if (swerveInstance == null) {
            swerveInstance = new SwerveSubsystem();
        }
        return swerveInstance;
    }

    public SwerveSubsystem(){
        createSwerveDriveYAGSL();
    }

    private void createSwerveDriveYAGSL(){
        try {
            this.swerveDrive = new SwerveParser(new File("deploy/swerve/swerve")).createSwerveDrive(SwerveConstants.MAX_VEL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void driveAlignAngleButton() {
        if (this.isControllerAskingForRotation()) {
          this.driveRotating();
          this.state="ROBOT_MOVING_AND_ROTATING";
          return;
        } else {
          this.desiredSpeeds = this.inputsToChassisSpeeds(controller.getYtranslation(),controller.getXtranslation());
          swerveDrive.driveFieldOriented(desiredSpeeds);
          this.state = "ROBOT_MOVING";
        }
    }    

    @Override
    public void driveAlignAngleTarget(Rotation2d angleToTarget, ChassisSpeeds chassisSpeeds){
        desiredSpeeds = new ChassisSpeeds(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond, angleToTarget.getRadians() * SwerveConstants.kP);
        swerveDrive.driveFieldOriented(desiredSpeeds);
    }

    @Override
    public void driveAlignAngleSpeaker(){
        Alliance alliance = DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() : Alliance.Red;
        ChassisSpeeds chassisSpeeds = this.inputsToChassisSpeeds(controller.getYtranslation(), controller.getXtranslation());
        Rotation2d spinCompensationAngle = Rotation2d.fromDegrees(-7);
        if(alliance == Alliance.Red){
            Translation2d positionDifference = this.getPose().getTranslation().minus(targetPosition);
            Rotation2d targetAngle = positionDifference.getAngle().plus(offsetAngle);
            this.driveAlignAngleTarget(targetAngle, chassisSpeeds);
        }
    }

    protected SwerveSubsystem getPose(){
        return swerveInstance.getPose();
    }

    protected SwerveSubsystem getTranslation(){
        return swerveDrive.
    }

    protected void driveAimingAtPosition(ChassisSpeeds targetSpeeds, Translation2d targetPosition, Rotation2d offsetAngle) {
    this.targetAimPose = new Pose2d(targetPosition, new Rotation2d());
    Translation2d positionDifference = this.getPose().getTranslation().minus(targetPosition);
    Rotation2d targetAngle = positionDifference.getAngle().plus(offsetAngle);
    this.driveFieldOrientedLockedAngle(targetSpeeds, targetAngle);
    }

    private void driveRotating() {
        this.desiredSpeeds = this.inputsToChassisSpeeds(controller.getYtranslation(), controller.getXtranslation());
        if (this.controller.rotateLeft()) {
            desiredSpeeds = new ChassisSpeeds(desiredSpeeds.vxMetersPerSecond, desiredSpeeds.vyMetersPerSecond,
            SwerveConstants.ROTATION_BUTTON_SPEED);
        } else if (this.controller.rotateRight()) {
            desiredSpeeds = new ChassisSpeeds(desiredSpeeds.vxMetersPerSecond, desiredSpeeds.vyMetersPerSecond,
            -SwerveConstants.ROTATION_BUTTON_SPEED);
        }
        swerveDrive.driveFieldOriented(desiredSpeeds);
    }

    private boolean isControllerAskingForRotation() {
        return controller.rotateLeft() || controller.rotateRight();
    }

    protected ChassisSpeeds inputsToChassisSpeeds(double xInput, double yInput) {
        return new ChassisSpeeds(xInput * SwerveConstants.MAX_VEL, yInput * SwerveConstants.MAX_VEL, 0);
    }



    @Override
    public void periodic(){
        this.stringLogger.append(state);
    }
}
