// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class to hold all methods to invoke Internals for the 2022 GRT robot (Ctrler).
 * References image:
 * <img src="https://media.discordapp.net/attachments/980228868805894175/1035023743606587452/unknown.png" width=200px/>
 */
public class InternalsSubsystem extends SubsystemBase {
	private final XboxController controller = new XboxController(0);
	
	private final AnalogPotentiometer distance1 = new AnalogPotentiometer(0);
	

  public InternalsSubsystem() {
	  
  }

  @Override
  //Runs every tick; 20ms tick length
  public void periodic() {
	  
  }
  
}
