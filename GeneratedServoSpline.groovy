double computeGearPitch(double diameterAtCrown,double numberOfTeeth){
	return ((diameterAtCrown/2)*((360.0)/numberOfTeeth)*Math.PI/180)
}
// call a script from another library
def bevelGears = ScriptingEngine.gitScriptRun(
            "https://github.com/madhephaestus/GearGenerator.git", // git location of the library
            "bevelGear.groovy" , // file to load
            // Parameters passed to the funcetion
            [	  20,// Number of teeth gear a
	            20,// Number of teeth gear b
	            4.3,// thickness of gear A
	            computeGearPitch(4.63,20),// gear pitch in arc length mm
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
// return the CSG parts
return [bevelGears.get(0)]
