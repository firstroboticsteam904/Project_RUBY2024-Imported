// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation.Alliance;


/** Add your docs here. */
public class Constants {

      /*
   * This is a deadzone to help use use when using an "Axis" type of control,
   * to limit things like stick drift
   */
    public double deadzone = 0.1;


    public static class DriverStationInfo{
        public static Optional<Alliance> allianceColor = DriverStation.getAlliance();
        public AllianceStationID allianceStationID = DriverStationJNI.getAllianceStation();
    }
    

    //Below are all of our different 
    public static class ControllerConfig{

        public final int DriverStickPort = 0;
            public final int DriverYAxis = 1;
            public final int DriverXAxis = 4;

            /* these are two varibles that we have created that are technically 
            * what our robot is reading as the input from the controller
            * to the motor */
            public double ThrottleSpeed; 
            public double TurningSpeed;

        public final static int OperatorStickPort = 0;


    }

    //Below are all of our components of our Limelight
    public static class LimelightControll{
        public final Boolean LEDoff = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
        public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        public static NetworkTableEntry tx = table.getEntry("tx");
        public static Double TX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(5);
        public static Boolean DefaultPipeline = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
        public static Boolean RedPipeline = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
        public static Boolean BluePipeline = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
        public static PIDController Seeza = new PIDController(0.007, 0.0045, 0.0007);
        public static double limecontrol = Seeza.calculate(0, TX);

    }

    //Below are all of our components of our pneumatic system
    public static class PnuematicConfig{
        /* This is an example of us creating a new Compresser, named m_Compressor, 
        attached to a REV Pneumatic Hub */
        public Compressor m_Compressor = new Compressor(PneumaticsModuleType.REVPH);

        // This is us creating a new Pneumatic Hub, named m_pH, with the CANBus ID: 8
        public static PneumaticHub m_pH = new PneumaticHub(8);

        // This is us creating a single solenoid, named "Slayenoid", it is plugged into channel 1 on the Pnuematic Hub
        public Solenoid slayenoid = m_pH.makeSolenoid(1);

          /* This is us creating Double Solenoids, named "tilt" & "grip".
          * Unlike single solenoids, Double solenoids are plugged into two slots on the Pneumatic Hub
          * 1 for a "reverse channel" and 1 for a "Forward Channel". Below you can see we have them plugged into
          * ports 14/2 & 0/15 */
        public DoubleSolenoid tilt = m_pH.makeDoubleSolenoid(14, 2);
        public DoubleSolenoid grip = m_pH.makeDoubleSolenoid(0, 15);

        /* This is us creating a variable for a Forward/Reverse shortcut
         * to be used in Robot.java */
        public Value Forward = (DoubleSolenoid.Value.kForward);
        public Value Reverse = (DoubleSolenoid.Value.kReverse);
    }

}
