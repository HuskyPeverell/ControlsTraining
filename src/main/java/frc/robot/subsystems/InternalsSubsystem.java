// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class InternalsSubsystem extends SubsystemBase {
	private final XboxController controller = new XboxController(0);
	private final Timer timer = new Timer();
	
	private final AnalogPotentiometer distance1 = new AnalogPotentiometer(0);
	
	private boolean power = true;
	private boolean polarity = true;
  public InternalsSubsystem() {
	  
  }

  @Override
  public void periodic() {
	  
  }

  @Override
  public void simulationPeriodic() {
	  
  }
}
