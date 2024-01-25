// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends SubsystemBase {

  private CANSparkMax m_leftmotor1 = new CANSparkMax(2,MotorType.kBrushless);
  private CANSparkMax m_leftmotor2 = new CANSparkMax(3,MotorType.kBrushless);
  private CANSparkMax m_leftmotor3 = new CANSparkMax(4,MotorType.kBrushless);
  private MotorControllerGroup Lgroupmotor = new MotorControllerGroup(m_leftmotor1,m_leftmotor2, m_leftmotor3);


  private CANSparkMax m_rightmotor1 = new CANSparkMax(5,MotorType.kBrushless);
  private CANSparkMax m_rightmotor2 = new CANSparkMax(6,MotorType.kBrushless);
  private CANSparkMax m_rightmotor3 = new CANSparkMax(7,MotorType.kBrushless);
  private MotorControllerGroup Rgroupmotor = new MotorControllerGroup(m_rightmotor1,m_rightmotor2, m_rightmotor3);
  
   public DifferentialDrive m_drive = new DifferentialDrive(Lgroupmotor, Rgroupmotor);


   public void arcadeDrive(double xspeed, double zrotation){
    m_drive.arcadeDrive(xspeed, zrotation, false);
   }

  /** Creates a new Drivetrain. */
  public Drivetrain() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
