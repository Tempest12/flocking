import java.util.ArrayList;
import java.util.Random;

public class Core
{
	public static ArrayList<Boid> boidList;
	public static Grid spacialGrid;

	public static ArrayList<Obstacle> obstacleList;	

	public static Random random;

	public static Vector2f goal;
	public static float goalSize;

	//Mouse Stuff
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static Vector2f mousePosition;

	public static void init()
	{
		Log.init(Config.logLevel);

		checkOptions();

		Config.numberOfSquares = Config.width / Config.gridSize;

		boidList = new ArrayList<Boid>();
		obstacleList = new ArrayList<Obstacle>();

		random = new Random();

		for(int index = 0; index < Config.startingFlockSize; index++)
		{
			boidList.add(new Boid(new Vector2f(random.nextInt(Config.width + 1), random.nextInt(Config.height + 1))));
		}

		goal = new Vector2f();
		respawnGoal();
		goalSize = Config.goalSize;
	
		mousePosition = new Vector2f();

		spacialGrid = new Grid();
	}

	public static void handleKeyboardInput()
	{

	}

	public static void handleMouseInput()
	{

	}
	
	public static void update()
	{
		mousePosition.set(mouseX, mouseY);

		for(int index = 0; index < boidList.size(); index++)
		{
			spacialGrid.placeInGrid(boidList.get(index));
		}

		for(int index = 0; index < boidList.size(); index++)
		{
			boidList.get(index).update(1.0f);
			//System.out.println(boidList.get(index).toString());
		}

		for(int index = 0; index < boidList.size(); index++)
		{
			if(collideCircles(goal, goalSize, boidList.get(index).position, boidList.get(index).size))
			{
				respawnGoal();
				break;
			}
		}
	
		spacialGrid.clearGrid();
	}

	public static void checkOptions()
	{
		if(Config.width % Config.gridSize != 0)
		{
			System.out.println("Grid size must divide evenly into window size!");
			System.exit(1);
		}
	}

	public static boolean collideCircles(Vector2f one, float oneSize, Vector2f two, float twoSize)
	{
		float distanceSquared = Vector2f.distanceSquared(one, two);
		float sum = oneSize + twoSize;
		sum *= 2;

		return distanceSquared <= sum;	 
	}

	public static void respawnGoal()
	{
		goal.set(random.nextInt(Config.width), random.nextInt(Config.height));
	}
}
