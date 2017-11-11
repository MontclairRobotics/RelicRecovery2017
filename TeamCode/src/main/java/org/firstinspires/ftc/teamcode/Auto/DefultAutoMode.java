package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.ArmPosition;
import org.firstinspires.ftc.teamcode.Auto.Enums.JewelColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.PictogramResults;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

import static org.firstinspires.ftc.teamcode.Auto.Enums.JewelColor.BLUE;
import static org.firstinspires.ftc.teamcode.Auto.Enums.JewelColor.RED;

/**
 * Created by MHS Robotics on 11/6/2017.
 */

public class DefultAutoMode extends OpMode{

    //Vuforia Vars
    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    RelicRecoveryVuMark vuMark;
    PictogramResults pictogram;

    //Color Prox Vars
    ColorSensor sensorColor;
    JewelColor color;

    //auto mode objects
    DefultHardwareMap hardware;
    DefultMecanumMapper mapper;
    AllianceColor allianceColor;
    ElapsedTime timer;
    double startTime;

    //final
    //TODO: Get measurement @ MAX POWER
    public final double JEWEL_ARM_DOWN_POS = 0.59;
    public final double JEWEL_ARM_UP_POS = 0;
    public final double PAUSE_TIME = 0.5;
    public final String LSA = "LAST STATE ACHIEVED";
    public final double TICKS_PER_INCH = 1500/42.3;
    public final double TICKS_PER_DEGREE = (43.23/360)*TICKS_PER_INCH*2;
    public final double TICKS_PER_LIFT_INCH = 1000/1000;



    public void autoInit(){
        hardware = new DefultHardwareMap();
        hardware.init(hardwareMap);
        telemetry.addData("INFO","Hardware Map Init");
        mapper = new DefultMecanumMapper();

        setState(0);
        timer = new ElapsedTime();
        startTime = timer.milliseconds();
        visionInit();
        hardware.resetDriveEncoders();
        hardware.resetLiftEncoders();
        telemetry.addData("INFO", "INITIALIZED");
    }

    @Override
    public void start() {
        super.start();
        startTime = timer.milliseconds();
        hardware.resetDriveEncoders();
        hardware.resetLiftEncoders();
    }

    //vision
    public void visionInit(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(/*cameraMonitorViewId*/);
        parameters.vuforiaLicenseKey = "AVhGMov/////AAAAGUk16JthIkWst4BeQ3creo+NTUF+BxVD6iSoptSHES0tn3qxxl8EoEMBtZfR9lS5zeb8wa5m+susmQEk+ELlMZvkhfCo5hwgtQVQo95VhTaduQjLatwooAcigCDfAK19KDQPw7O4/Q0p0G79ni5UlnYrw/lF1ZC2iv+41EGTjOTT8yC6wWMzzi2ugWGtIYs9Qy62b9S+Jr2/JjoqtzoaeUX7cmshji5IRmPojALj71tKJb1Gay4XcCb7fMMkO10SDaY84E66Vt0aEhgyA4VY/ASABIEEBlpDoq7N/tTSMxDfahX0xP76BXUSNEug7Y378HPg9siRGv5AQns3Y44RfPqBu6kQN1yDXb+43Zl3ZkzF";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();
        telemetry.addData("INFO","Vision Init");
    }
    public boolean getPictogram(){
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            telemetry.addData("VuMark", "not visible");
            return false;
        } else {
            pictogram = PictogramResults.valueOf(vuMark.toString());
            telemetry.addData("VuMark", pictogram);
            relicTrackables.deactivate();
            return true;
        }
    }
    public boolean pictogramDrive(PictogramResults result){
        switch (result){
            case LEFT:
                telemetry.addData("Moving","LEFT");
                return autoDrive(new XY(-5,0),0.5);

            case CENTER:
                telemetry.addData("Moving","CENTER");
                return true;

            case RIGHT:
                telemetry.addData("Moving","Right");
                return autoDrive(new XY(5,0),0.5);


        }
        telemetry.addData("INFO","FAILED");
        return false;
    }

    //colorProx
    public boolean getJewel(){
        if(sensorColor.red() > sensorColor.blue()){
            color = RED;
        }else if(sensorColor.blue()> sensorColor.red()){
            color = BLUE;
        }else{
            color = JewelColor.UNKNOWN;
            return false;
        }
        telemetry.addData("Jewel Color", color);
//        switch (color){
//            case RED:
//                switch (allianceColor){
//                    case RED: //move forward
//                        break;
//
//                    case BLUE: //move backwards
//                        break;
//                }
//
//                break;
//
//            case BLUE:
//                switch (allianceColor){
//                    case RED: //move backwards
//                        break;
//
//                    case BLUE: //move forwards
//                        break;
//                }
//                break;
//        }
        return true;
    }

    //driving
    /*
    drive(new XY(0, 0.5), 0);
     */
    public void drive(Vector v, double turn) {
        mapper.map(new DTTarget(v, turn), hardware.driveModules);
    }

    public boolean autoDrive(Vector v, double speed) {
        if(hardware.getTicks() < v.getMagnitude()*TICKS_PER_INCH) {
            drive(v.normalize().scale(speed), 0);
        } else {
            drive(new XY(0, 0), 0);
            hardware.resetDriveEncoders();
            return true;
        }
        return false;
    }

    public boolean autoTurn(double degrees, double speed) {
        int ticks = hardware.frontLeft.getCurrentPosition();
        if(ticks < degrees*TICKS_PER_DEGREE) {
            hardware.frontLeft.setPower(speed);
            hardware.backLeft.setPower(speed);
            hardware.frontRight.setPower(-speed);
            hardware.backRight.setPower(-speed);
        } else {
            hardware.frontLeft.setPower(0);
            hardware.backLeft.setPower(0);
            hardware.frontRight.setPower(0);
            hardware.backRight.setPower(0);
            hardware.resetDriveEncoders();
            return true;
        }
        return false;
    }

    //jewel arm
    public boolean jewelArm(ArmPosition pos){
        if(pos == ArmPosition.UP){
            hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
            pause(5);
            return true;
        }else if(pos == ArmPosition.DOWN){
            hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
            pause(5);
            return true;
        }else
            return false;
    }

    //glyph
    public void glyphLiftPower(double power){
        hardware.glyphLeft.setPower(power);
        hardware.glyphRight.setPower(power);
    }

    public boolean setGlyphLiftPos(double inch, double power){
        if(inch>0){
            if (hardware.getLiftTicks()<inch*TICKS_PER_LIFT_INCH){
                glyphLiftPower(power);
                return false;
            }else {
                glyphLiftPower(0);
                hardware.resetLiftEncoders();
                return true;
            }
        } else {
            if (hardware.getLiftTicks()>inch*TICKS_PER_LIFT_INCH){
                glyphLiftPower(-power);
                return false;
            }else {
                glyphLiftPower(0);
                hardware.resetLiftEncoders();
                return true;
            }
        }

    }

    //State Machine
    public int state = 0;
    public void setState(int state){
        this.state = state;
    }
    public void nextState(boolean nextState){
        if(nextState){
            state++;
            telemetry.addData("Info","State "+state+" Achieved");
            startTime = timer.milliseconds();
        }

    }

    //Wait method
    public boolean pause(double seconds){
        if(getSeconds()>seconds){
            telemetry.addData("time_finished", getMillis());
            return true;
        }
        return false;
    }

    public double getMillis(){
        return timer.milliseconds() - startTime;
    }

    public double getSeconds(){
        return getMillis()/1000.0;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
