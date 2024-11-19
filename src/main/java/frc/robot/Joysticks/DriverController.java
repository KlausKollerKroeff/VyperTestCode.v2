package frc.robot.Joysticks;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.constants.JoystickConstants;

public class DriverController implements IDriverController {

  private static DriverController mInstance = null;

  public static DriverController getInstance() {
    if (mInstance == null) {
      mInstance = new DriverController();
    }

    return mInstance;
  }

  final CommandXboxController driverController;
  final XboxController driverControllerBoolean;

  private DriverController() {
    driverController = new CommandXboxController(0);
    driverControllerBoolean = new XboxController(0);
  }

  @Override
  public double getXtranslation() {
    if (turboActivate()) {
      return -MathUtil.applyDeadband(performAllianceSpeedDirectionCorrection(driverController.getLeftX()),
          JoystickConstants.DEADBAND);
    }
    return -MathUtil.applyDeadband(performAllianceSpeedDirectionCorrection(driverController.getLeftX()),
        JoystickConstants.DEADBAND) * 0.6;
  }

  @Override
  public double getYtranslation() {
    if (turboActivate()) {
      return -MathUtil.applyDeadband(performAllianceSpeedDirectionCorrection(driverController.getLeftY()),
          JoystickConstants.DEADBAND);
    }
    return -MathUtil.applyDeadband(performAllianceSpeedDirectionCorrection(driverController.getLeftY()),
        JoystickConstants.DEADBAND) * 0.6;
  }

  @Override
  public double getCOS_Joystick() {
    return -driverController.getRightX();
  }

  @Override
  public double getSIN_Joystick() {
    return -driverController.getRightY();
  }

  @Override
  public boolean turboActivate() {
    return 0.2 < driverController.getRightTriggerAxis();
  }

  @Override
  public Trigger setHeadingBack() {
    return driverController.a();
  }

  @Override
  public Trigger setHeadingFront() {
    return driverController.y();
  }

  @Override
  public Trigger setHeadingLeft() {
    return driverController.x();
  }

  @Override
  public Trigger setHeadingRight() {
    return driverController.b();
  }

  @Override
  public boolean rotateLeft() {
    return driverControllerBoolean.getLeftBumper();
  }

  @Override
  public boolean rotateRight() {
    return driverControllerBoolean.getRightBumper();
  }

  @Override
  public boolean isForcingDriverControl() {
    return 0.2 < driverController.getLeftTriggerAxis();
  }

  @Override
  public Trigger resetGyro() {
    return driverController.back();
  }

  private double performAllianceSpeedDirectionCorrection(Double value) {
    Alliance alliance = DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get()
        : DriverStation.Alliance.Red;
    if (alliance == Alliance.Red) {
      return -value;
    }
    return value;
  }

}
