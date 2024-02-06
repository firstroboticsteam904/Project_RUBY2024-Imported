// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/* Every link below is a link to a Class that can be found toward the top of the Documentation websites of
 * WPILib (https://github.wpilib.org/allwpilib/docs/release/java/index.html) 
 * RevRobotics (https://codedocs.revrobotics.com/java/)
 * CTRE (https://api.ctr-electronics.com/phoenix/release/java/)
*/

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.Optional;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;
import frc.robot.Constants.ControllerConfig;
import frc.robot.Constants.LimelightControll;
import frc.robot.Constants.PnuematicConfig;
import frc.robot.Constants.DriverStationInfo;
import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation.Alliance;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
       

  // The following final Strings below are examples of how to introduce new autonomous modes into the code
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;

  // This is creating a new Autonomous Chooser (aka Sendable Chooser)
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Below are where we first intoduce our different subsystems (Drivetrains, Arms, Etc.)
  public static Drivetrain drivetrain; 

  // Below are where we introduce our different classes in Constants.java
  public Constants constants;
  public ControllerConfig controllerConfig;
  public LimelightControll limelightControll;
  public PnuematicConfig pnuematicConfig;
  public DriverStationInfo driverstationInfo;


  // Below are the joysticks for Driving and Operating
  public Joystick DriverStick;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit(  ) {


    /*This is where we actually add the different Autonomous selections to smartdashboard
    */
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
   
    //this is us creating new instances of our constants classes
    constants = new Constants();
    controllerConfig = new ControllerConfig();
    limelightControll = new LimelightControll();
    pnuematicConfig = new PnuematicConfig();
    driverstationInfo = new DriverStationInfo();

    /*
     * Here we are assigning our DriverStick to port 0 (port numbers can be found in the DriverStation
     * under the USB connections section). 
     */
    DriverStick = new Joystick(controllerConfig.DriverStickPort);

    // This is us creating new instances of our subsystems
    drivetrain = new Drivetrain();




    /*
     * Here we turn on our compressor, and also set the starting positions for our solenoids.
     * single solenoids are a boolean (true or false). Double Solenoids will be either a value of
     * Forward or Reverse. You can find out which direction you want by testing the robot.
     */
    pnuematicConfig.m_Compressor.enableDigital();
    pnuematicConfig.slayenoid.set(false);
    pnuematicConfig.tilt.set(pnuematicConfig.Forward);
    pnuematicConfig.grip.set(pnuematicConfig.Forward);

    //This is a command for us to force off the LED lights on the Limelight
    limelightControll.LEDoff.booleanValue();

      if(driverstationInfo.allianceColor.isPresent()){
        if(driverstationInfo.allianceColor.get() == Alliance.Red){
        limelightControll.RedPipeline.booleanValue();
        SmartDashboard.putString("Alliance Color", "Red");
        System.out.printf("Red Alliance Pipeline", true);
      }
        if(driverstationInfo.allianceColor.get() == Alliance.Blue){
        limelightControll.BluePipeline.booleanValue();
        SmartDashboard.putString("Alliance Color", "Blue");
        System.out.printf("Blue Alliance Pipeline", true);
      }
    } else{
      limelightControll.DefaultPipeline.booleanValue();
      SmartDashboard.putString("Alliance Color", "Default");
      System.out.printf("Default Pipeline", true);
      //System.out.print("Debug Reading");
    }
    
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    if(driverstationInfo.allianceColor.isPresent()){
      if(driverstationInfo.allianceColor.get() == DriverStation.Alliance.Red){
       limelightControll.RedPipeline.booleanValue();
       SmartDashboard.putString("Alliance Color", "Red");
       System.out.println("(Red Alliance)" + limelightControll.RedPipeline);
      }
      if(driverstationInfo.allianceColor.get() == Alliance.Blue){
       limelightControll.BluePipeline.booleanValue();
       SmartDashboard.putString("Alliance Color", "Blue");
        System.out.println("(Blue Alliance)" + limelightControll.BluePipeline);
      }
    } else{
       limelightControll.DefaultPipeline.booleanValue();
       System.out.print(limelightControll.DefaultPipeline.booleanValue());
       System.out.println("(Default Alliance)" + limelightControll.DefaultPipeline);
      //System.out.print("Debug Reading Periodic");
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // This is code written to get the selected Autonomous function from SmartDashboard, and run it.
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*
     * This is where we set the different cases for different autonomous mode selections.
    */
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {



  /*
   * This is an equation that is saying if the absoulute value of
   * the y-channel/x-channel on the DriverStick is greater than the
   * "deadzone" varible we created earlier, then set the power
   * of the motors to the value of the input channel and set it to
   * the power of 3. The reason we do this is to create a quadratic
   * formula, which will give us more precise control at higher speeds
   */

  if (Math.abs(DriverStick.getRawAxis(controllerConfig.DriverYAxis))>constants.deadzone) {
   controllerConfig.ThrottleSpeed = Math.pow(DriverStick.getRawAxis(controllerConfig.DriverYAxis), 3);
   System.out.print("(Manual Y-Axis)");
  } else {
   controllerConfig.ThrottleSpeed = 0;
   System.out.print("(Y-Axis Stopped)");
  }

  if (Math.abs(DriverStick.getRawAxis(controllerConfig.DriverXAxis))>constants.deadzone){
  controllerConfig.TurningSpeed = Math.pow(DriverStick.getRawAxis(controllerConfig.DriverXAxis), 3);
  System.out.print("(Manual X-Axis)");
  } // Here we create an else if that when button 1 is held down it gives turning power to limelight
   else {
  controllerConfig.TurningSpeed = 0;
  System.out.print("(X-Axis Stopped)");
  //System.out.print("limelight control reading");
  }

  if(DriverStick.getRawButton(1)){ 
  SmartDashboard.putNumber("limelightTX", limelightControll.TX);
  //controllerConfig.TurningSpeed = -limelightControll.limecontrol;
  drivetrain.arcadeDrive(-limelightControll.limecontrol, -controllerConfig.ThrottleSpeed);
  System.out.print("(limelight control reading)");
  } else {
    limelightControll.Seeza.reset();
    drivetrain.arcadeDrive(-controllerConfig.TurningSpeed, -controllerConfig.ThrottleSpeed);
    System.out.print("(RegControlls, Seeza Reset()");
  }

/*
 * These are buttons we are creating on the DriverStick to shift
 * inbetween low and high gear.
 */
  
 // This is the if statement to shift us into low gear
if(DriverStick.getRawButton(5)){
  pnuematicConfig.slayenoid.set(true);
  SmartDashboard.putString("Current Gear", "Low Gear");
  System.out.print("(Low Gear)?");
}

  // This is the if statement to shift us into high gear
if(DriverStick.getRawButton(6)){
  pnuematicConfig.slayenoid.set(false);
  SmartDashboard.putString("Current Gear", "High Gear");
  System.out.print("(High Gear?)");
}




} 

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {

  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {

  }

  @Override
  public void simulationInit(){

  }

  @Override
  public void simulationPeriodic(){

  }

}

