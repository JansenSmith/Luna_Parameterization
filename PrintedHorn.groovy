import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine

// create larger servo horn geometry
// modify GeneratedServoSpline?
// import GeneratedServoSpline as its own geometry (as a negative?)
// set to generate shape files from release versions
// release v0.1.1

// call a script from another library
def servoShaft = ScriptingEngine.gitScriptRun(
			"https://github.com/JansenSmith/Luna_Parameterization.git", // git location of the library
			"GeneratedServoSpline.groovy" , // file to load
			)

return servoShaft