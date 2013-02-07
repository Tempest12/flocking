import java.util.ArrayList;

public class Boid
{
	private static int uniqueID = 0;	

	public Vector2f position;
	public Vector2f velocity;
	public Vector2f acceleration;

	public float orientation;
	public float orientationRadians;

	public float size;

	public Colour4f colour;
	
	public int id;

	//Stuff used for drawing
	public Vector2f north;
	public Vector2f east;
	public Vector2f west;

	public Vector2f southEast;
	public Vector2f southWest;

	public Boid(Vector2f position)
	{
		this.position = position;
		this.velocity = new Vector2f();
		this.acceleration = new Vector2f();

		this.orientation = 90.0f;
		this.orientationRadians = 0.0f;

		this.size = Config.boidSize;

		this.colour = Colour4f.randomColour4f();

		this.north = new Vector2f();
		this.east = new Vector2f();
		this.west = new Vector2f();

		this.southEast = new Vector2f();
		this.southWest = new Vector2f();

		this.id = uniqueID;
		uniqueID++;
	}
		
	public void centerTheFlock()
	{
		Log.writeDebug("Centering Force:");

		int row = (int)position.x / Config.gridSize;
		int col = (int)position.y / Config.gridSize;

		int count = 0;
		Vector2f center = new Vector2f();

		ArrayList<Boid> list = null;

		//Going to try to check the contents of our grid and 8 surrounding ones:
		for(int gridRow = -1; gridRow < 2; gridRow++)
		{
			for(int gridCol = -1; gridCol < 2; gridCol++)
			{
				list = Core.spacialGrid.grid.get(Core.spacialGrid.getIndex(row + gridRow, col + gridCol));
					
				for(Boid that : list)
				{
					if(!this.equals(that))
					{
						if(toroidialDistanceSquared(that) < Config.flockingRadiusSquared)
						{
							center.add(that.position);
							count++;
						}
					}
				}
			}
		}

		

		if(count != 0)
		{
			center.divideByScalar(count);
			//Log.writeDebug("Average center: " + center);
			center.subtract(this.position);
			//Log.writeDebug("After subtraction: " + center);
			//center.normalize();
			//Log.writeDebug("Normalized: " + center);
			//center.scale(Config.centeringStrength);	
			center.clamp(0.0f, Config.centeringStrength * Config.maxVelocity);					
			this.acceleration.add(center);
		}
		else
		{
			Log.writeDebug("No Centering");
		}	
	}	

	public void collisionAvoidance()
	{
		int row = (int)position.x / Config.gridSize;
		int col = (int)position.y / Config.gridSize;

		float count = 0;
		Vector2f avoid = new Vector2f();
		Vector2f temp = new Vector2f();

		float weight = 0.0f;

		ArrayList<Boid> list = null;

		//Going to try to check the contents of our grid and 8 surrounding ones:
		for(int gridRow = -1; gridRow < 2; gridRow++)
		{
			for(int gridCol = -1; gridCol < 2; gridCol++)
			{
				list = Core.spacialGrid.grid.get(Core.spacialGrid.getIndex(row + gridRow, col + gridCol));
					
				for(Boid that : list)
				{
					if(!this.equals(that))
					{
						if(toroidialDistanceSquared(that) < Config.avoidanceDistanceSquared)
						{
							/*temp.set(this.position.x - that.position.x, this.position.y - that.position.y);
							weight = 1 / temp.magnitude();
							
							count += weight;

							temp.set(that.position);
							temp.scale(weight);*/
							
							avoid.add(that.position);
							count++;
						}
					}
				}
			}
		}

		if(count != 0)
		{
			avoid.divideByScalar(count);
			//avoid.scale(count);
			avoid = Vector2f.subtractVectors(this.position, avoid);
			//Log.writeDebug("After Subtraction: " + avoid);
			//avoid.normalize();
			//avoid.scale(Config.collisionAvoidanceStrength);
			//avoid.clamp(0.0f, Config.collisionAvoidanceStrength * Config.maxVelocity);		
			Log.writeDebug("Avoidance Force: " + avoid);			
			this.acceleration.add(avoid);
		}
	}

	public void computeDrawLocations()
	{
		orientationRadians = (float)Math.toRadians(orientation);
		north.set((float)Math.cos(orientationRadians), (float)Math.sin(orientationRadians));
		north.scale(size);

		orientationRadians = (float)Math.toRadians(orientation + 90);		
		west.set((float)Math.cos(orientationRadians), (float)Math.sin(orientationRadians));
		west.scale(size);

		orientationRadians = (float)Math.toRadians(orientation - 90);		
		east.set((float)Math.cos(orientationRadians), (float)Math.sin(orientationRadians));
		east.scale(size);

		orientationRadians = (float)Math.toRadians(orientation + 140);
		southWest.set((float)Math.cos(orientationRadians), (float)Math.sin(orientationRadians)); 
		southWest.scale(size);

		orientationRadians = (float)Math.toRadians(orientation - 140);
		southEast.set((float)Math.cos(orientationRadians), (float)Math.sin(orientationRadians)); 
		southEast.scale(size);
          
		north.add(position);
		east.add(position);
		west.add(position);
		   
		southEast.add(position);
		southWest.add(position);
	}

	private static final Vector2f xAxis = new Vector2f(1, 0);
	public void computeOrientation()
	{
		Vector2f temp = new Vector2f(velocity);
		temp.normalize();
		
		orientation = (float)Math.toDegrees(Math.acos(temp.dotProduct(xAxis)));
		//System.out.println(Math.acos(temp.dotProduct(xAxis)));


		if(velocity.y < 0)
		{
			orientation *= -1;
		}
		//System.out.println(orientation);
	}

	public boolean equals(Boid that)
	{
		return this.id == that.id;
	}
	
	public String toString()
	{
		return "Boid " + this.id + " Position " + this.position;
	}

	public float toroidialDistance(Boid that)
	{
		return (float)Math.sqrt(this.toroidialDistanceSquared(that));
	}	

	public float toroidialDistanceSquared(Boid that)
	{
		float xDistance = Math.abs(this.position.x - that.position.x);
		float yDistance = Math.abs(this.position.y - that.position.y);

		xDistance = Math.min(xDistance, Config.width - xDistance);
		yDistance = Math.min(yDistance, Config.height - yDistance);

		xDistance *= xDistance;
		yDistance *= yDistance;

		return xDistance + yDistance;
	}

	public void velocityMatching()
	{
		int row = (int)position.x / Config.gridSize;
		int col = (int)position.y / Config.gridSize;

		int count = 0;
		Vector2f match = new Vector2f();

		ArrayList<Boid> list = null;

		//Going to try to check the contents of our grid and 8 surrounding ones:
		for(int gridRow = -1; gridRow < 2; gridRow++)
		{
			for(int gridCol = -1; gridCol < 2; gridCol++)
			{
				list = Core.spacialGrid.grid.get(Core.spacialGrid.getIndex(row + gridRow, col + gridCol));
					
				for(Boid that : list)
				{
					if(!this.equals(that))
					{
						if(toroidialDistanceSquared(that) < Config.flockingRadiusSquared)
						{
							match.add(that.velocity);
							count++;
						}
					}
				}
			}
		}

		if(count != 0)
		{
			match.divideByScalar(count);
			match.subtract(this.velocity);
			//match.normalize();
			//match.scale(Config.velocityMatchingStrength);
			match.clamp(0.0f, Config.velocityMatchingStrength * Config.maxVelocity);	
			Log.writeDebug("Matching Force: " + match);		
			this.acceleration.add(match);
		}
	}

	public void update(float timeStep)
	{
		Log.writeDebug("Boid " + id + " before update");
		//Log.writeDebug("Position: " + position + " Velocity: " + velocity);		

		acceleration.set(0.0f, 0.0f);		

		centerTheFlock();
		wander();
		velocityMatching();
		collisionAvoidance();

		acceleration.normalize();
		acceleration.scale(Config.newAcceleration);
		velocity.normalize();
		velocity.scale(Config.oldVelocity);

		//Log.writeDebug("After Update:");
		//Log.writeDebug("Acceleration: " + acceleration.toString());

		velocity.add(acceleration);
		//Log.writeDebug("Velocity: " + velocity.toString());

		velocity.clamp(Config.minVelocity, Config.maxVelocity);

		position.add(velocity);
		
		wrap();

		Log.writeDebug("New Position: " + position.toString() + "\n");

		computeOrientation();

		computeDrawLocations();	

		Log.writeDebug("");
	}
	
	public void wander()
	{
		//float newAngle = (float)Math.toRadians(orientation + Core.random.nextInt((int)Config.wanderingAngleMax * 2) - Config.wanderingAngleMax);
		//Vector2f randomHeading = new Vector2f((float)Math.cos(newAngle), (float)Math.sin(newAngle));
		
		Vector2f randomHeading = Vector2f.randomDirection();

		randomHeading.scale(Config.randomForceStrength);
		Log.writeDebug("Wanering Force: " + randomHeading);	

		acceleration.add(randomHeading);
	}


	public void wrap()
	{
		//Left
		if(position.x > (Config.width + this.size))
		{
			position.x = 0;
		}	

		//Right
		if(position.x < (0 - this.size))
		{
			position.x = Config.width + this.size;
		}

		//Top
		if(position.y < (0 - this.size))
		{
			position.y = Config.height + this.size;
		}

		//Bottom
		if(position.y > (Config.height + this.size))
		{
			position.y = 0;
		}
	}
}