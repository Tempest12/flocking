int shift = 20;

boolean oneUp = true;
boolean twoUp = true;
boolean threeUp = true;
boolean fourUp = true;

boolean spaceUp = true;
boolean sUp = true;
boolean pUp = true;
boolean cUp = true;
boolean aUp = true;
boolean rUp = true;

boolean paused = false;
boolean clearWindow = true;

void setup()
{
	Core.init();

	size(Config.width, Config.height);
	//size(Config.width + 2 * shift, Config.height + 2 * shift);

	colorMode(RGB, 1.0f, 1.0f, 1.0f, 1.0f);

	background(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);
}

void handleKeyBoardEvents()
{
	Core.mouseDown = mousePressed;

	Core.mousePosition.set(mouseX, mouseY);

	if(!keyPressed)
	{
		oneUp = true;
		twoUp = true;
		threeUp = true;
		fourUp = true;

		spaceUp = true;
		sUp = true;
		pUp = true;
		cUp = true;
		aUp = true;
		rUp = true;
	}
	else if(key == ' ' && spaceUp)
	{
		spaceUp = false;
		paused = !paused;
	}
	else if(key == '1' && oneUp)
	{
		Core.centerFlock = !Core.centerFlock;
		oneUp = false;
		printForces();
	}
	else if(key == '2' && twoUp)
	{
		Core.matchVelocities = !Core.matchVelocities;
		twoUp = false;
		printForces();
	}
	else if(key == '3' && threeUp)
	{
		Core.avoidBoids = !Core.avoidBoids;
		threeUp = false;
		printForces();
	}
	else if(key == '4' && fourUp)
	{
		Core.wanderForce = !Core.wanderForce;
		fourUp = false;
		printForces();
	}
	else if((key == 's' || key == 'S') && sUp)
	{
		Core.scatter();
		sUp = false;
	}
	else if(key == '+')
	{
		Core.addBoid();
	}
	else if(key == '-')
	{
		Core.removeBoid();
	}
	else if((key == 'p' || key == 'P') && pUp)
	{
		clearWindow = !clearWindow;
		pUp = false;
	}
	else if((key == 'c' || key == 'C') && cUp)
	{
		background(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);
		cUp = false;
	}
	else if((key == 'a' || key == 'A') && aUp)
	{
		Core.attract = true;
		Core.repulse = false;
		aUp = false;
	}
	else if((key == 'r' || key == 'R') && rUp)
	{
		Core.attract = false;
		Core.repulse = true;
		rUp = false;
	}
}

void draw()
{
    handleKeyBoardEvents();

	if(!paused)
	{
		Core.update();
	}
   
	/*background(0.08f, 0.08f, 0.08f, 1);
	fill(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);
	rect(0 + shift, 0 + shift, Config.width, Config.height);*/
	if(clearWindow)
	{
		background(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);
	}

	drawBoids();

	//drawGoal();
}

void drawBoids()
{
	float x = 0.0f;
	float y = 0.0f;

	float arcAngle = radians(90);

	noFill();

	for(Boid boid : Core.boidList)
	{	
		stroke(boid.colour.red, boid.colour.green, boid.colour.blue, boid.colour.alpha );
		/*line(boid.west.x + shift, boid.west.y + shift, boid.east.x + shift, boid.east.y + shift);

		beginShape();
		curveVertex(boid.north.x + shift, boid.north.y + shift);
		curveVertex(boid.north.x + shift, boid.north.y + shift);
		curveVertex(boid.position.x + shift, boid.position.y + shift);
        curveVertex(boid.southEast.x + shift, boid.southEast.y + shift);
		curveVertex(boid.southEast.x + shift, boid.southEast.y + shift);
		endShape();

		beginShape();
		curveVertex(boid.north.x + shift, boid.north.y + shift);
		curveVertex(boid.north.x + shift, boid.north.y + shift);
		curveVertex(boid.position.x + shift, boid.position.y + shift);
        curveVertex(boid.southWest.x + shift, boid.southWest.y + shift);
		curveVertex(boid.southWest.x + shift, boid.southWest.y + shift);
		endShape();*/	

		line(boid.west.x, boid.west.y, boid.east.x, boid.east.y);

		beginShape();
		curveVertex(boid.north.x, boid.north.y);
		curveVertex(boid.north.x, boid.north.y);
		curveVertex(boid.position.x, boid.position.y);
        curveVertex(boid.southEast.x, boid.southEast.y);
		curveVertex(boid.southEast.x, boid.southEast.y);
		endShape();

		beginShape();
		curveVertex(boid.north.x, boid.north.y);
		curveVertex(boid.north.x, boid.north.y);
		curveVertex(boid.position.x, boid.position.y);
        curveVertex(boid.southWest.x, boid.southWest.y);
		curveVertex(boid.southWest.x, boid.southWest.y);
		endShape();

		if(Config.displayFlockingBoundary)
		{
			stroke(1,1,1,1);
			ellipse(boid.position.x, boid.position.y, Config.flockingRadius, Config.flockingRadius);
		}

		if(Config.displayAvoidanceBoundary)
		{
			stroke(1,1,0,1);
			ellipse(boid.position.x, boid.position.y, Config.avoidanceDistance, Config.avoidanceDistance); 		
		}
	}
}

void drawGoal()
{
	stroke(1, 1, 1);
	ellipse(Core.goal.x, Core.goal.y, Core.goalSize, Core.goalSize);
	line(Core.goal.x - (Core.goalSize - 1), Core.goal.y, Core.goal.x + (Core.goalSize - 1), Core.goal.y);
	line(Core.goal.x, Core.goal.y - (Core.goalSize - 1) , Core.goal.x, Core.goal.y + (Core.goalSize - 1));
}

void printForces()
{
	StringBuilder temp = new StringBuilder("Centering Force: ");

	if(Core.centerFlock)
	{
		temp.append("On ");
	}
	else
	{
		temp.append("Off ");
	}
	
	temp.append("Velocity Matching: ");

	if(Core.matchVelocities)
	{
		temp.append("On ");
	}
	else
	{
		temp.append("Off ");
	}

	temp.append("Collision Avoidance: ");

	if(Core.avoidBoids)
	{
		temp.append("On ");
	}
	else
	{
		temp.append("Off ");
	}

	temp.append("Wandering Force: ");

	if(Core.wanderForce)
	{
		temp.append("On ");
	}
	else
	{
		temp.append("Off ");
	}

	temp.append("\n");
	
	println(temp.toString());
}
