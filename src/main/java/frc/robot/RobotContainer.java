package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Joysticks.ControlBoard;
import frc.robot.commands.swerve.AimAtTargetCmd;
import frc.robot.commands.swerve.SwerveTeleopControl;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class RobotContainer {

  SwerveSubsystem swerve = new SwerveSubsystem();
  ControlBoard controller = ControlBoard.getInstance();

  public RobotContainer() {
    configureBindings();
    swerve.setDefaultCommand(new SwerveTeleopControl(swerve));
  }

  private void configureBindings() {
    controller.getIntake().toggleOnTrue(new AimAtTargetCmd(swerve));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
