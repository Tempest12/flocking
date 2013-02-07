public class Config
{
	//Program Stuff
	public static int logLevel = 0;
	
	//Window Stuff
	public static int width = 800;
	public static int height = 800;

	//Display
	public static boolean displayFlockingBoundary = false;
	public static boolean displayAvoidanceBoundary = false;

	//Goal Stuff
	public static float goalSize = 7.0f;

	//Boid Stuff
	public static float maxVelocity = 3.5f;
	public static float minVelocity = 1.0f;

	public static float boidSize = 5.0f;

	public static int startingFlockSize = 100;

		//Boid Radii
		public static float flockingRadius = 70.0f;
		public static float flockingRadiusSquared = flockingRadius * flockingRadius;

		public static float avoidanceDistance = 25.0f;
		public static float avoidanceDistanceSquared = avoidanceDistance * avoidanceDistance;
		
		public static float wanderingAngleMax = 5.0f;
	
		//Boid Force Strengths
		public static float centeringStrength = 0.20f;
		public static float randomForceStrength = 0.05f;
		public static float velocityMatchingStrength = 0.20f;
		public static float collisionAvoidanceStrength = 0.55f;

		//Velocity Control Strengths
		public static float oldVelocity = 0.65f;
		public static float newAcceleration = 0.35f;
		

	//Grid Stuff
	public static int gridSize = 100;
	public static int numberOfSquares = width / gridSize;


	//Pillar Stuff
	public static float radius = 7.0f;
	
	//Colours
	public static float bg_red = 0.0f;
	public static float bg_green = 0.0f; 
	public static float bg_blue = 0.15f;
	public static float bg_alpha = 1.0f;
}
