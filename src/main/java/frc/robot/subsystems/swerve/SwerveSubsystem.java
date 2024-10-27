package frc.robot.subsystems.swerve;

import java.io.File;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
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
            this.swerveDrive = new SwerveParser(this.directory).createSwerveDrive(SwerveConstants.MAX_VEL);
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
        }
        if (controller.notUsingJoystick()) {
          this.desiredSpeeds = this.inputsToChassisSpeeds(controller.getYtranslation(),
              controller.getXtranslation());
          this.state = "ROBOT_MOVING";
        }
    }    

    @Override
    public void driveAlignAngleTarget(double driveAngleToTarget){
        this.desiredSpeeds = this.inputsToChassisSpeeds(controller.getYtranslation(), controller.getXtranslation());
        desiredSpeeds = new ChassisSpeeds(desiredSpeeds.vxMetersPerSecond, desiredSpeeds.vyMetersPerSecond, driveAngleToTarget * SwerveConstants.kP);
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
