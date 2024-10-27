package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import frc.Java_Is_UnderControl.Vision.Cameras.LeftArducam;
import frc.robot.subsystems.swerve.ISwerve;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class AimAtTargetCmd extends Command{
    private ISwerve swerve;
    private LeftArducam camera;

  public AimAtTargetCmd(SwerveSubsystem swerve) {
    this.swerve = swerve;
    addRequirements(swerve);
  }

  @Override
  public void execute() {
    swerve.driveAlignAngleTarget(camera.getAngleToTarget());;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
