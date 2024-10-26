package frc.Java_Is_UnderControl.Logging.EnhancedLoggers;

import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CustomStringLogger extends StringLogEntry {

  private static boolean isFmsMatch;

  private String name;

  public CustomStringLogger(String name) {
    super(DataLogManager.getLog(), name);
    this.name = name;
    CustomStringLogger.isFmsMatch = DriverStation.getMatchNumber() > 0;
  }

  @Override
  public void append(String value) {
    super.append(value);
    if (!CustomStringLogger.isFmsMatch) {
      SmartDashboard.putString(this.name, value);
    }
  }

}
