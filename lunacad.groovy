import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
//import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
import com.neuronrobotics.bowlerstudio.vitamins.*;
import com.neuronrobotics.sdk.addons.kinematics.AbstractLink
import com.neuronrobotics.sdk.addons.kinematics.DHLink
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics
import com.neuronrobotics.sdk.addons.kinematics.LinkConfiguration
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR

import java.nio.file.Paths;

import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Cube
import eu.mihosoft.vrl.v3d.FileUtil;
import eu.mihosoft.vrl.v3d.Transform;
import javafx.scene.transform.Affine;
import com.neuronrobotics.bowlerstudio.physics.TransformFactory;
import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine
println "Loading STL file"
// Load an STL file from a git repo
// Loading a local file also works here

return new ICadGenerator(){
	
	private CSG moveDHValues(CSG incoming,DHLink dh ){
		TransformNR step = new TransformNR(dh.DhStep(0)).inverse()
		Transform move = TransformFactory.nrToCSG(step)
		return incoming.transformed(move)
		
	}

	@Override 
	public ArrayList<CSG> generateCad(DHParameterKinematics d, int linkIndex) {
		return new ArrayList<>();
	}
	@Override 
	public ArrayList<CSG> generateBody(MobileBase b ) {
		ArrayList<CSG> allCad=new ArrayList<>();

		

		// Load the .CSG from the disk and cache it in memory
	
		CSG srv  = Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Servo Cover.stl"))
			.roty(90)
			.movex(20-6.75)
			.movez(-83)
			.movey(42.5)
			.setColor(javafx.scene.paint.Color.YELLOW)
			
		ArrayList<CSG> movedSHoulder=[]
		for(DHParameterKinematics leg:b.getLegs()){
			Transform t = TransformFactory.nrToCSG(leg.getRobotToFiducialTransform())
			movedSHoulder.add(srv.transformed(t))
			//movedSHoulder.add(new Cube(0.1,0.1,200).toCSG().transformed(t))
		}	
		ArrayList<CSG> myMovedLinks =[
			
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Body.stl"))
			.setColor(javafx.scene.paint.Color.DARKGRAY),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Body Battery Cover.stl"))
			.setColor(javafx.scene.paint.Color.YELLOW),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Body Cover Left.stl"))
			.setColor(javafx.scene.paint.Color.DARKGRAY),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Body Cover Right.stl"))
			.setColor(javafx.scene.paint.Color.DARKGRAY),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Tail Head Encoder Limb.stl"))
			.setColor(javafx.scene.paint.Color.YELLOW)
			.rotx(-90)
			.movex(-92.5)
			.movez(12.825)
			.movey(-16.5),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Tail Head Encoder Limb.stl"))
			.setColor(javafx.scene.paint.Color.YELLOW)
			.rotx(-90)
			.rotz(180)
			.movex(92.5)
			.movez(12.825)
			.movey(16.5),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Tail Head Servo Horn Limb.stl"))
			.rotx(-90)
			.movex(92.5)
			.movez(12.825)
			.movey(-16.5)
			.setColor(javafx.scene.paint.Color.YELLOW),
			Vitamins.get(ScriptingEngine.fileFromGit(
			"https://github.com/OperationSmallKat/Luna.git",
			"Tail Head Servo Horn Limb.stl"))
			.rotx(-90)
			.rotz(180)
			.movex(-92.5)
			.movez(12.825)
			.movey(16.5)
			.setColor(javafx.scene.paint.Color.YELLOW)
			
			].collect{
			it.movez(106.75)
		} 
		
		myMovedLinks.addAll(movedSHoulder)
		
		for(def part:myMovedLinks){
			part.setManipulator(b.getRootListener());
			
		}
		
		def servoHorn = ScriptingEngine.gitScriptRun(
			"https://github.com/JansenSmith/Luna_Parameterization.git", // git location of the library
			"PrintedHorn.groovy" , // file to load
			)
		myMovedLinks.add(servoHorn)
		

		return myMovedLinks;
	}
};
