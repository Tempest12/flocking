int shift = 20;

void setup()
{
	Core.init();

	size(Config.width, Config.height);
	//size(Config.width + 2 * shift, Config.height + 2 * shift);

	colorMode(RGB, 1.0f, 1.0f, 1.0f, 1.0f);

	background(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);
}

void draw()
{
	Core.mouseX = mouseX;
	Core.mouseY = mouseY;

	Core.update();

	/*background(0.08f, 0.08f, 0.08f, 1);
	fill(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);
	rect(0 + shift, 0 + shift, Config.width, Config.height);*/
	background(Config.bg_red, Config.bg_green, Config.bg_blue, Config.bg_alpha);

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
