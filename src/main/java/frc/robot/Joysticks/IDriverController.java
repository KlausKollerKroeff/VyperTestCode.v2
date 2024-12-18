package frc.robot.Joysticks;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface IDriverController {
  double getXtranslation();

  double getYtranslation();

  double getCOS_Joystick();

  double getSIN_Joystick();

  boolean turboActivate();

  Trigger setHeadingFront();

  Trigger setHeadingRight();

  Trigger setHeadingBack();

  Trigger setHeadingLeft();

  boolean rotateLeft();

  boolean rotateRight();

  Trigger resetGyro();

  boolean isForcingDriverControl();
}
