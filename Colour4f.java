public class Colour4f
{
    public float red;
    public float green;
    public float blue;
    public float alpha;

    public Colour4f(float red, float green, float blue, float alpha)
    {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.alpha = alpha;
    }

    public Colour4f(float red, float green, float blue)
    {            
		 this(red, green, blue, 1.0f);
    }

    public Colour4f(Colour4f that)
    {
        this.red = that.red;
        this.green = that.green;
        this.blue = that.blue;
        this.alpha = that.alpha;
    }

	public static Colour4f randomColour4f()
	{
		return new Colour4f(Core.random.nextFloat(), Core.random.nextFloat(), Core.random.nextFloat(), 1.0f);
	}
}
