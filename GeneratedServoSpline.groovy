import eu.mihosoft.vrl.v3d.Cube
import eu.mihosoft.vrl.v3d.Cylinder

double computeGearPitch(double diameterAtCrown,double numberOfTeeth){
	return ((diameterAtCrown/2)*((360.0)/numberOfTeeth)*Math.PI/180)
}

double diameterAtCrown = 4.7
double thicknessGear = 2.3
 
// call a script from another library
def bevelGears = ScriptingEngine.gitScriptRun(
            "https://github.com/madhephaestus/GearGenerator.git", // git location of the library
            "bevelGear.groovy" , // file to load
            // Parameters passed to the function
            [	  20,// Number of teeth gear a
	            20,// Number of teeth gear b
	            thicknessGear,// thickness of gear A
	            computeGearPitch(diameterAtCrown,20),// gear pitch in arc length mm
	            0,// shaft angle, can be from 0 to 100 degrees
	            0// helical angle, only used for 0 degree bevels
            ]
            )

//Print parameters returned by the script
println "Bevel gear axil center to center " + bevelGears.get(2)
println "Bevel gear axil Height " + bevelGears.get(3)
println "Bevel angle " + bevelGears.get(4)
println "Bevel tooth face length " + bevelGears.get(5)
println "Gear B computed thickness " + bevelGears.get(6)
println "Gear Ratio " + bevelGears.get(7)
println "Mesh Interference calculated: " + bevelGears.get(9)
// capture the CSG parts
singleGear = bevelGears.get(0)
singleGear = singleGear.toZMin()

thicknessHorn = thicknessGear + 2
widthHorn = 8
//servoSpline = new Cube(widthHorn, 10, thicknessHorn).toCSG()
//servoSpline = servoSpline.toZMin()
//servoSpline = servoSpline.difference(singleGear)
setScrewHoleWidth = 2.5
seScrewHeadWidth = setScrewHoleWidth + 2
setScrewHoleClearance = new Cylinder(setScrewHoleWidth/2, thicknessHorn).toCSG()
setScrewHoleClearance = setScrewHoleClearance.toZMin()
servoShaft = singleGear.union(setScrewHoleClearance)
return servoShaft