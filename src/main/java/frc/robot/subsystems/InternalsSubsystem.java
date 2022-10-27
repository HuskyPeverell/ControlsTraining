// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class to hold all methods to invoke Internals for the 2022 GRT robot
 * (Ctrler). References image: <img src=
 * "https://media.discordapp.net/attachments/980228868805894175/1035023743606587452/unknown.png"
 * width=200px/>
 */
public class InternalsSubsystem extends SubsystemBase {
	// Interface
	private static final XboxController XBOX_CONTROLLER = new XboxController(0);

	// Output
	private static final WPI_TalonSRX UPPER_TSRX = new WPI_TalonSRX(14);
	private static final WPI_TalonSRX LOWER_TSRX = new WPI_TalonSRX(13);
	private static  final CANSparkMax FLYWHEEL_SPARKMAX = new CANSparkMax(15, MotorType.kBrushless);

	// Sensing
	private static final AnalogPotentiometer ENTRY_IR = new AnalogPotentiometer(0);
	private static final AnalogPotentiometer UPPER_IR = new AnalogPotentiometer(1);
	private static final ColorSensorV3 MIDDLE_COLOR = new ColorSensorV3(I2C.Port.kMXP);

	// Persistent(TM)
	private boolean shootQueued = false;
	private boolean ballTop = false;
	private boolean ballBottom = true;
	
	public InternalsSubsystem() {
		FLYWHEEL_SPARKMAX.restoreFactoryDefaults();
	}
	
	/**
	 * Sets Persistent(TM) class variables if the subsystem should be started at a particular state (for example, a ball already present).
	 * This command is meant for testing purposes and should not be used in production unless you know what you are doing.
	 * 
	 * @param shootQueued
	 * @param ballTop
	 * @param ballBottom
	 */
	public void setPersistentVariables(boolean shootQueued, boolean ballTop, boolean ballBottom) {
		
		this.shootQueued = shootQueued;
		this.ballBottom = ballBottom;
		this.ballTop = ballTop;
		
	}

	@Override
	// Runs every tick; 20ms tick length
	public void periodic() {
		//Queue or cancel a shoot operation
		if(!shootQueued) {
			shootQueued = XBOX_CONTROLLER.getAButtonPressed();
		}else {
			shootQueued = !XBOX_CONTROLLER.getBButtonPressed();
		}
		
		//Shoot and reset variables if shoot is queued.
		if(shouldShoot()) {
			if(!ballTop) {
				shootQueued = false;
				ballTop = false;
			}
		}
		
		//Move ball upward if there is no ball at top.
		if(ballBottom && !ballTop) {
			
		}
		
		//Move ball inward if there is space
		if(!ballBottom) {
			
		}
	}
	
	/**
	 * Modify this method directly if you need more steps to be completed (such as aiming) before you shoot.
	 * @return Whether the shoot should activate.
	 */
	public boolean shouldShoot() {
		//Returns true if there is a ball near the top IR and a shot is queued.
		return ballTop && shootQueued;
	}
}
