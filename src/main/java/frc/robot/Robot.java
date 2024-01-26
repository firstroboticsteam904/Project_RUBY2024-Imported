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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor; 
import edu.wpi.first.wpilibj.Solenoid; 
import edu.wpi.first.wpilibj.PneumaticHub; 
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.BaseUnits;

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

  // Below are the joysticks for Driving and Operating
  public Joystick DriverStick;

  /*
   * This is a deadzone to help use use when using an "Axis" type of control,
   * to limit things like stick drift
   */
  double deadzone = 0.1;

  
  //Below are all of our components of our pneumatic system
  /* This is an example of us creating a new Compresser, named m_Compressor, 
   * attached to a REV Pneumatic Hub
   */
  Compressor m_Compressor = new Compressor(PneumaticsModuleType.REVPH);
  // This is us creating a new Pneumatic Hub, named m_pH, with the CANBus ID: 8
  static PneumaticHub m_pH = new PneumaticHub(8);

  // This is us creating a single solenoid, named "Slayenoid", it is plugged into channel 1 on the Pnuematic Hub
  Solenoid slayenoid = m_pH.makeSolenoid(1);

  /*
   * This is us creating Double Solenoids, named "tilt" & "grip".
   * Unlike single solenoids, Double solenoids are plugged into two slots on the Pneumatic Hub
   * 1 for a "reverse channel" and 1 for a "Forward Channel". Below you can see we have them plugged into
   * ports 14/2 & 0/15
   */
  DoubleSolenoid tilt = m_pH.makeDoubleSolenoid(14, 2);
  DoubleSolenoid grip = m_pH.makeDoubleSolenoid(0, 15);

  //Camera stuff to be described at a later date
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");

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

    DriverStick = new Joystick(0);
    DriverStick.setYChannel(1);
    DriverStick.setXChannel(4);
    drivetrain = new Drivetrain();

    m_Compressor.enableDigital();
    slayenoid.set(false);
    tilt.set(DoubleSolenoid.Value.kForward);
    grip.set(DoubleSolenoid.Value.kForward);

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);  


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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
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

  

  double throttledeadzone; 
  double turndeadzone;

  if (Math.abs(DriverStick.getY())>deadzone) {
    throttledeadzone = Math.pow(DriverStick.getY(), 3);
  } else {
    throttledeadzone = 0;
  }

if (Math.abs(DriverStick.getX())>deadzone){
  turndeadzone = Math.pow(DriverStick.getX(), 3);
} else {
  turndeadzone = 0;
}

drivetrain.arcadeDrive(-turndeadzone, -throttledeadzone);
  
if(DriverStick.getRawButton(5)){
  slayenoid.set(true);
}

if(DriverStick.getRawButton(6)){
  slayenoid.set(false);
}


if(DriverStick.getRawButton(1)){

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

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {

  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    
  }
}

