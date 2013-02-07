public class BoidUpdateThread implements Runnable
{
	//Indexes to start and stop at in the Boid list
	public int start;
	public int end;
		
	public BoidUpdateThread(int start, int end)
	{
		this.start = start;
		this.end = end;
	}

	public void run()
	{
		for(int index = start; index < end; index++)
		{
			Core.boidList.get(index).update();
		}
	}
}
