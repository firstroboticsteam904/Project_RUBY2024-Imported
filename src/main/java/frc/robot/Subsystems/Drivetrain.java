// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

/* Every link below is a link to a Class that can be found toward the top of the Documentation websites of
 * WPILib (https://github.wpilib.org/allwpilib/docs/release/java/index.html) 
 * or RevRobotics (https://codedocs.revrobotics.com/java/)
*/

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkBase.ExternalFollower;

public class Drivetrain extends SubsystemBase {

  //Creates 3 different brushess motors that are connected to SparkMax Motorcontrollers.
  //each ID is determined by CANBus ID set in Rev Hardware application
  private CANSparkMax m_leftmotor1 = new CANSparkMax(2,MotorType.kBrushless);
  private CANSparkMax m_leftmotor2 = new CANSparkMax(3,MotorType.kBrushless);
  private CANSparkMax m_leftmotor3 = new CANSparkMax(4,MotorType.kBrushless);
  private MotorControllerGroup Lgroupmotor = new MotorControllerGroup(m_leftmotor1,m_leftmotor2, m_leftmotor3);
 
  /* Taking a look at things for future use, if it has a line crossing it out, it is atrisk of being deleted
   * over the course of the next few years as the libraries get updated, so just trying to stay 
   * ahead of the game here
   */
  //private static final CANSparkBase.ExternalFollower LeftFollower1 = new ExternalFollower(1, 2);
  //private static final CANSparkBase.ExternalFollower LeftFollower2 = new ExternalFollower(1, 3);

  


  private CANSparkMax m_rightmotor1 = new CANSparkMax(5,MotorType.kBrushless);
  private CANSparkMax m_rightmotor2 = new CANSparkMax(6,MotorType.kBrushless);
  private CANSparkMax m_rightmotor3 = new CANSparkMax(7,MotorType.kBrushless);
  private MotorControllerGroup Rgroupmotor = new MotorControllerGroup(m_rightmotor1,m_rightmotor2, m_rightmotor3);
  
    /* Taking a look at things for future use, if it has a line crossing it out, it is atrisk of being deleted
   * over the course of the next few years as the libraries get updated, so just trying to stay 
   * ahead of the game here
   */
    //private static final CANSparkBase.ExternalFollower RightFollower1 = new ExternalFollower(5, 6);
    //private static final CANSparkBase.ExternalFollower RightFollower2 = new ExternalFollower(5, 7);
  
   public DifferentialDrive m_drive = new DifferentialDrive(Lgroupmotor, Rgroupmotor);

   //public DifferentialDrive m_drive = new DifferentialDrive(m_leftmotor1, m_rightmotor1);


   public void arcadeDrive(double zRotation, double xSpeed){
    m_drive.arcadeDrive(zRotation, xSpeed, false);
   }

  /** Creates a new Drivetrain. */
  public Drivetrain() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
