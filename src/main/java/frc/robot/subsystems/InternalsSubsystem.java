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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class to hold all methods to invoke Internals for the 2022 GRT robot
 * (Ctrler). References image: <img src=
 * "https://media.discordapp.net/attachments/980228868805894175/1035023743606587452/unknown.png"
 * width=200px/>
 */
public class InternalsSubsystem extends SubsystemBase {
	// Constants
	private static final Timer SHOOTER_TIMER = new Timer();
	
	// Interface
	private static final XboxController XBOX_CONTROLLER = new XboxController(0);

	// Output
	private static final WPI_TalonSRX UPPER_TSRX = new WPI_TalonSRX(14);
	private static final WPI_TalonSRX LOWER_TSRX = new WPI_TalonSRX(13);
	private static final CANSparkMax FLYWHEEL_SPARKMAX = new CANSparkMax(15, MotorType.kBrushless);

	// Sensing
	private static final AnalogPotentiometer ENTRY_IR = new AnalogPotentiometer(0);
	private static final AnalogPotentiometer UPPER_IR = new AnalogPotentiometer(1);
	private static final ColorSensorV3 MIDDLE_COLOR = new ColorSensorV3(I2C.Port.kMXP);

	// Persistent(TM)
	private boolean shootQueued = false;
	private boolean ballTop = false;
	private boolean ballBottom = true;
	private boolean shooting = false;
	private boolean intakeUp = false;
	private boolean intakeDown = false;

	public InternalsSubsystem() {
		FLYWHEEL_SPARKMAX.restoreFactoryDefaults();
	}

	/**
	 * Sets Persistent(TM) class variables if the subsystem should be started at a
	 * particular state (for example, a ball already present). This command is meant
	 * for testing purposes and should not be used in production unless you know
	 * what you are doing.
	 * 
	 * @param shootQueued
	 * @param ballTop
	 * @param ballBottom
	 */
	@Deprecated
	public void setPersistentVariables(boolean shootQueued, boolean ballTop, boolean ballBottom, boolean shooting, boolean intakeUp, boolean intakeDown) {
		this.shootQueued = shootQueued;
		this.ballBottom = ballBottom;
		this.ballTop = ballTop;
		
		
		this.shooting = shooting;
		this.intakeUp = intakeUp;
		this.intakeDown = intakeDown;
	}

	@Override
	// Runs every tick; 20ms tick length
	public void periodic() {
		// Update Persistent(TM) variables
		updatePersistent();

		// Set state variables (multiple states can coincide)
		if (shouldShoot()) {
			shooting = true;
			shootQueued = false;
			SHOOTER_TIMER.reset();
		}

		if(shouldAccept()) {
			intakeDown = true;
		}
		
		if(shouldMoveUp()) {
			intakeUp = true;
		}
		
		//Resolve state variables
		if(shooting) {
			UPPER_TSRX.set(0.5);
			FLYWHEEL_SPARKMAX.set(1);
			
			if(SHOOTER_TIMER.get() > 3D) {
				shooting = false;
				UPPER_TSRX.set(0);
				FLYWHEEL_SPARKMAX.set(0);
			}
		}
		
		if(intakeUp) {
			LOWER_TSRX.set(0.5);
			
			if(ballTop) {
				LOWER_TSRX.set(0);
				intakeUp = false;
			}
		}
		
		if(intakeDown) {
			LOWER_TSRX.set(0.5);
			
			if(ballBottom) {
				LOWER_TSRX.set(0);
				intakeDown = false;
			}
		}
	}

	/**
	 * Invoke top motors to stage, then flywheel to shoot the ball.
	 * 
	 * Modify this method directly if you need more steps to be completed (such as
	 * aiming) before you shoot.
	 * 
	 * @return Whether the shoot should activate.
	 */
	private boolean shouldShoot() {
		// Returns true if there is a ball near the top IR, a shot is queued, and the mech isn't already shooting.
		return ballTop && shootQueued && !shooting;
	}
	
	/**
	 * Invoke mechanism to activate bottom motor and store new ball at bottom position.
	 * 
	 * Modify this method directly if more steps need to be completed before accepting a ball from intake.
	 * 
	 * @return Whether ball should be taken in
	 */
	private boolean shouldAccept() {
		//If there's a ball at entry, there's no ball at the bottom position, and we aren't already accepting a ball
		return !ballBottom && ENTRY_IR.get() >= 0.7 && !intakeDown;
	}
	
	/**
	 * Invoke mechanism to activate bottom motor to store a ball at the top position
	 * 
	 * Modify this method directly if more steps need to be completed before moving a ball upward.
	 * 
	 * @return Whether the ball should move up
	 */
	private boolean shouldMoveUp() {
		//If there's a ball at the bottom, none at the top, and we aren't already raising a ball
		return ballBottom && !ballTop && !intakeUp;
	}

	private void updatePersistent() {
		// Queue or cancel a shoot operation
		if (!shootQueued) {
			shootQueued = XBOX_CONTROLLER.getAButtonPressed();
		} else {
			shootQueued = !XBOX_CONTROLLER.getBButtonPressed();
		}
		
		//Is there a ball at the bottom?
		if(MIDDLE_COLOR.getProximity() >= 1500) {
			ballBottom = true;
		}
		
		if(UPPER_IR.get() >= 0.7) {
			ballTop = true;
		}
	}
}
