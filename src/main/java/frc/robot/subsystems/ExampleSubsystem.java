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

public class ExampleSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
	private final CANSparkMax motor1 = new CANSparkMax(1, MotorType.kBrushless);
	private final CANSparkMax motor2 = new CANSparkMax(17, MotorType.kBrushless);
	private final XboxController controller = new XboxController(0);
	private final Timer timer = new Timer();
	
	private final AnalogPotentiometer distance1 = new AnalogPotentiometer(0);
	
	private boolean power = true;
	private boolean polarity = true;
  public ExampleSubsystem() {
	  motor1.restoreFactoryDefaults();
	  motor2.restoreFactoryDefaults();
	  motor2.follow(motor1);
	  motor1.setIdleMode(IdleMode.kCoast);
	  motor2.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
//    System.out.println("uwu");
//    double pow = controller.getLeftY();
//    if(controller.getAButtonPressed()) {
//    	power = !power;
//    }
//    if(controller.getBButtonPressed()) {
//    	polarity = !polarity;
//    }
//    if(power) {
//    	if(polarity) {
//    		motor1.set(pow);
//    	}else {
//    		motor1.set(-pow);
//    	}
//    }else {
//    	motor1.set(0);
//    }
	  
//	  if(controller.getAButtonPressed()) {
//		  timer.start();
//		  timer.reset();
//	  }
//	  System.out.println(timer.get());
//	  if(timer.get() <= 4) {
//	  	motor1.set(timer.get()/4D);
//	  }else if (timer.get() <= 6) {
//		motor1.set(1);
//	  }else if(timer.get() <= 10) {
//		motor1.set(1-((timer.get()-6)/4D));
//	  }else {
//		  motor1.set(0);
//	  }
	  
	  motor1.set(1-distance1.get());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
