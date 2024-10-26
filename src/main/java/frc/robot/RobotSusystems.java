package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swerve.ISwerve;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class RobotSusystems extends SubsystemBase{
    public ISwerve swerve;
    private SubsystemBase swerveSubsystem;

    public RobotSusystems(){
        this.swerve = SwerveSubsystem.getInstance();
        this.swerveSubsystem = SwerveSubsystem.getInstance();
    }

    @Override
    public void periodic(){
        swerveSubsystem.periodic();
    }

}
