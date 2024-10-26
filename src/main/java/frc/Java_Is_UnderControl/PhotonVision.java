package frc.Java_Is_UnderControl;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.math.geometry.Pose2d;
import frc.Java_Is_UnderControl.Logging.EnhancedLoggers.CustomBooleanLogger;
import frc.Java_Is_UnderControl.Logging.EnhancedLoggers.CustomDoubleLogger;

public class PhotonVision {
    PhotonCamera camera;

    private CustomDoubleLogger targetPositionXLogEntry;

    private CustomDoubleLogger targetPositionYLogEntry;

    private CustomBooleanLogger hasTargetLogEntry;

    private PhotonTrackedTarget bestTarget;

    private MedianFilter filterTY = new MedianFilter(4);

    public PhotonVision(String cameraName){
       camera = new PhotonCamera(cameraName);
       this.hasTargetLogEntry = new CustomBooleanLogger("/Cameras/" + cameraName + "/hasTarget");
       targetPositionXLogEntry = new CustomDoubleLogger("/Cameras/" + cameraName + "/targetPositionX");
       this.targetPositionYLogEntry = new CustomDoubleLogger("/Cameras/" + cameraName + "/targetPositionY");
    }

    private PhotonPipelineResult getResult() {
        return this.camera.getLatestResult();
    }

    public double targetPositionY(){
        bestTarget = this.getResult().getBestTarget();
        if(bestTarget != null){
            double targetPositionY = bestTarget.getPitch();
            this.targetPositionYLogEntry.append(targetPositionY);
            return filterTY.calculate(targetPositionY);
        } else {
            return 0;
        }   
    }

    public double targetPositionX(){
        bestTarget = this.getResult().getBestTarget();
        if(bestTarget != null){
            double targetPositionX = bestTarget.getYaw();
            this.targetPositionXLogEntry.append(targetPositionX);
            return filterTY.calculate(targetPositionX);
        } else {
            return 0;
        }   
    }

    public boolean hasTarget(){
        boolean hasTarget = this.getResult().hasTargets();
        this.hasTargetLogEntry.append(hasTarget);
        return hasTarget;
    }

    public PhotonTrackedTarget getTarget(){
        return bestTarget = this.getResult().getBestTarget();
    }
}
