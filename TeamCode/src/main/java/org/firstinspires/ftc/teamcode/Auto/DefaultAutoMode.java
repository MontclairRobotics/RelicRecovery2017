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
import org.firstinspires.ftc.teamcode.Auto.Enums.PictogramResults;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/6/2017.
 */

public class DefaultAutoMode extends OpMode{

    //Vuforia Vars
    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    RelicRecoveryVuMark vuMark;
    PictogramResults pictogram;

    //Color Prox Vars
    ColorSensor sensorColor;
    AllianceColor color;

    //auto mode objects
    DefaultHardwareMap hardware;
    DefaultMecanumMapper mapper;
    AllianceColor allianceColor;
    ElapsedTime timer;
    double startTime;

    //final
    //TODO: Get measurement @ MAX POWER
    public final double JEWEL_ARM_DOWN_POS = 1;
    public final double JEWEL_ARM_UP_POS = 0;
    public final String LSA = "LAST STATE ACHIEVED";
    public final double TICKS_PER_INCH = 1500/42.3;
    public final double TICKS_PER_DEGREE = (43.23/360)*TICKS_PER_INCH*2.5;
    public final double TICKS_PER_LIFT_INCH = 1400/6.5;



    public void autoInit(){
        hardware = new DefaultHardwareMap();
        hardware.init(hardwareMap);
        hardware.lift.closeAll();
//        hardware.jewelArm.setPosition(0);
        telemetry.addData("INFO","Hardware Map Init");
        mapper = new DefaultMecanumMapper();

        sensorColor = hardware.sensorColor;

        setState(0);
        timer = new ElapsedTime();
        startTime = timer.milliseconds();
//        visionInit();  //TODO: uncomment one is mounted on other side
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
    public boolean getJewelColor(){
        if(sensorColor.red() > sensorColor.blue()){
            color = AllianceColor.RED;
        }else if(sensorColor.blue()> sensorColor.red()){
            color = AllianceColor.BLUE;
        }else{
            telemetry.addData("INFO","ID FAILED");
            return false;
        }
        telemetry.addData("Jewel Color", color);
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
        if(Math.abs(hardware.getTicks()) < v.getMagnitude()*TICKS_PER_INCH) {
            drive(v.normalize().scale(speed), 0);
        } else {
            drive(new XY(0, 0), 0);
            hardware.resetDriveEncoders();
            return true;
        }
        return false;
    }

    public boolean autoTurn(double degrees, double speed) {
        int ticks = Math.abs(hardware.frontLeft.getCurrentPosition());
        if(degrees < 0) {
            speed *= -1;
        }

        if(ticks < Math.abs(degrees*TICKS_PER_DEGREE)) {
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
    public void nextState(boolean nextState,int nextStateNumber){
        if(nextState){
            state = nextStateNumber;
            telemetry.addData("Info","State "+state+" Achieved");
            startTime = timer.milliseconds();
        }

    }

    public void nextState(boolean nextState) {
        nextState(nextState, state + 1);
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
