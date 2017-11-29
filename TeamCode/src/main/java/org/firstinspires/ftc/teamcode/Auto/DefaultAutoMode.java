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
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;
import org.firstinspires.ftc.teamcode.Gyro;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */


public class DefaultAutoMode extends OpMode{

    //Vuforia Vars
    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    RelicRecoveryVuMark vuMark;
    PictogramResults pictogram;

    //Color Prox Vars
    ColorSensor colorSensor;
    AllianceColor color;

    //auto mode objects
    Gyro gyro;
    DefaultHardwareMap hardware;
    DefaultMecanumMapper mapper;
    AllianceColor allianceColor;
    StartPosition startPosition;
    ElapsedTime timer;
    double startTime;

    //final
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
        telemetry.addData("INFO","Hardware Map Init");
        mapper = new DefaultMecanumMapper();
        colorSensor = hardware.colorSensor;
        colorSensor.enableLed(true);
        setState(0);
        timer = new ElapsedTime();
        startTime = timer.milliseconds();
        visionInit();
        hardware.resetDriveEncoders();
        hardware.resetLiftEncoders();
        telemetry.addData("INFO", "INITIALIZED");
    }

    //vision
    public void visionInit(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId); //comment cameraMonitorViewID for competition
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
            telemetry.addData("VuMark", "UNKNOWN");
            return false;
        } else {
            pictogram = PictogramResults.valueOf(vuMark.toString());
            telemetry.addData("VuMark", pictogram);
            relicTrackables.deactivate();
            return true;
        }
    }

    public boolean pictogramDrive(PictogramResults image){
        telemetry.addData("Pictogram",pictogram);
        switch (image){
            case LEFT:
                telemetry.addData("Moving","LEFT");
                return autoDrive(new XY(-7.5,0),1);

            case CENTER:
                telemetry.addData("Moving","CENTER");
                return true;

            case RIGHT:
                telemetry.addData("Moving","Right");
                return autoDrive(new XY(7.5,0),1);

        }
        telemetry.addData("INFO","FAILED");
        return false;
    }

    //colorProx
    public boolean getJewelColor(){
        if(colorSensor.red() > colorSensor.blue()){
            color = AllianceColor.RED;
        }else if(colorSensor.blue()> colorSensor.red()){
            color = AllianceColor.BLUE;
        }else{
            telemetry.addData("INFO","ID FAILED");
            return false;
        }
        telemetry.addData("Jewel Color", color);
        return true;
    } //pt1
    private boolean moveJewel(){
        if(getJewelColor()){
            if(color != allianceColor){
                return autoTurn(-30,0.5);
            } else {
                return autoTurn(30,0.5);
            }
        } else {
            return false;
        }
    } //pt2
    private boolean jewelArmUp(){
        if(moveJewel()){
            hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
            return (pause(2));
        } else {
            return false;
        }
    } //pt3
    public boolean getJewel(){
        if(jewelArmUp()){
            if(color != allianceColor){
                return autoTurn(30,0.5);
            } else {
                return autoTurn(-30,0.5);
            }
        } else {
            return false;
        }
    } //pt4

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
    public boolean driveTurn(){
        switch (startPosition){
            case CLOSE:
                return autoTurn(90,0.5);

            case FAR:
                return autoTurn(-90,0.5);
        }
        return false;
    }
    public boolean safeZoneDrive(){
        switch (startPosition){
            case CLOSE:
                return true;

            case FAR:
                return autoDrive(new XY(0,12),0.5);
        }
        return false;
    }
    public boolean cryptoBoxTurn(){
        switch(allianceColor){
            case RED:
                switch (startPosition){
                    case CLOSE:
                        return true;

                    case FAR:
                        return autoTurn(90,0.5);
                }
                break;

            case BLUE:
                switch (startPosition){
                    case CLOSE:
                        return true;

                    case FAR:
                        return autoTurn(-90,0.5);
                }
                break;
        }
        return false;
    }

    //glyph
    public void glyphLiftPower(double power){
        hardware.glyphLeft.setPower(power);
        hardware.glyphRight.setPower(power);
    }
    private boolean setGlyphLiftPos(double inch, double power){
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

    public boolean raiseGlyph(){
        return setGlyphLiftPos(5,1);
    }
    public boolean lowerGlyph(){
        return setGlyphLiftPos(-5,1);
    }

    //State Machine
    public int state = 0;
    public void setState(int state){
        this.state = state;
    }
    public void nextState(boolean nextState, int nextStateNumber){
        if(nextState){
            state = nextStateNumber;
            telemetry.addData("Info","State "+state+" Achieved");
            telemetry.addData("Time Elapsed",getRuntime());
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
        autoInit();
    }

    @Override
    public void start() {
        super.start();
        startTime = timer.milliseconds();
        hardware.resetDriveEncoders();
        hardware.resetLiftEncoders();
    }

    @Override
    public void loop() {

    }
}
