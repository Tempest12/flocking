import java.util.Random;

/**
 * Vector2f is a class that represents a floating point Vector in 3 dimensions
 * @author Shane del Solar
 */
public class Vector2f
{
    public float x;
    public float y;
    

	public Vector2f()
	{
		this(0.0f);
	}
    /**
     * Constructor that takes in a float value, and then assign xzy to all be that value
     * @param all 
     */
    public Vector2f(float all)
    {
        this.x = all;
        this.y = all;
    }
    /**
     * Constructor for the Vectors that simply takes in it's x, y, and z values respectively
     * @param x float - the X value
     * @param y float - the Y value
     */
    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }        
    
    /**
     * Constructor that takes in another vector object and performs a deep copy
     * @param that Vector2f the vector to clone
     */    
    public Vector2f(Vector2f that)
    {
        this.x = that.x;
        this.y = that.y;
    }
    

	public void clamp(float min, float max)
	{
		float length = this.magnitudeSquared();

		min = min * 2;
		max = max * 2;

		if(length > max)
		{
			this.normalize();
			this.scale(max);
		}
		else if(length < min)
		{
			this.normalize();
			this.scale(min);
		} 
	}
    /**
     * Normalizes the vector
     */
    public void normalize()
    {
        float length = this.magnitude();

		if(length != 0.0f)
		{
	        this.x /= length;
        	this.y /= length;
		}
    }
    
    /**
     * Returns the length/magnitude of the vector
     * @return float - vector's Length/magnitude
     */
    public float magnitude()
    {
        return (float)Math.sqrt((x * x) + (y * y));
    }
    
    /**
     * Returns the magnitude of the Vector Squared
     * @return float - square of the vector magnitude
     */
    public float magnitudeSquared()
    {
        return (x * x) + (y * y);
    }
    
    /**
     * Performs a Dot Product Operation using this vector as the first operand and the given as the second.
     * @param that Vector2f - operand 2
     * @return float - dot(this, that)
     */
    public float dotProduct(Vector2f that)
    {
        return (this.x * that.x) + (this.y * that.y);
    } 
    
    /**
     * Performs a Dot Product Operation using this vector as the first operand and the given as the second. And then squares it.
     * @param that Vector2f - operand 2
     * @return float - dot(this, that)^2 
     */
    public float dotProductSquared(Vector2f that)
    {
        float num = (this.x * that.x) + (this.y * that.y);
        return num * num;
    }

	public void divideByScalar(float scalar)
	{
		this.x /= scalar;
		this.y /= scalar;
	}

    /**
     * Adds the 2 Vectors together and stores the results in this vector
     * @param that Vector2f - 2nd operand
     */
    public void add(Vector2f that)
    {
        this.x += that.x;
        this.y += that.y;
    }

    /**
     * Subtracts the two vectors and stores the results in this vector
     * @param that Vector2f - 2nd operand
     */
    public void subtract(Vector2f that)
    {        
        this.x -= that.x;
        this.y -= that.y;
    }
    
    /**
     * Scales this vector by a constant term. Stores the results in this vector
     * @param scalar float - constant Term
     */
    public void scale(float scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
    }    
    
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void set(Vector2f that)
	{
		this.x = that.x;
		this.y = that.y;
	}
    
	public boolean equals(Vector2f that)
    {
        return (this.x == that.x) && (this.y == that.y);
    }        
	
	public String toString()
	{
		return "X: " + this.x + " Y: " + this.y;
	}

    ////////////////////////////////////////////////////////////////////////////
    /////           Static Functions                                       /////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Performs a Dot Product Operation using the two given Vector.
     * @param one Vector2f -> operand 1
     * @param two Vector2f -> operand 2
     * @return float - dot(one, two) 
     */
    public static float dotProduct(Vector2f one, Vector2f two)
    {
        return (one.x * two.x) + (one.y * two.y);
    } 
    
    /**
     * Performs a Dot Product Operation using the two given Vector. And then squares it.
     * @param one Vector2f -> operand 1
     * @param two Vector2f -> operand 2
     * @return float - dot(one, two)^2 
     */
    public static float dotProductSquared(Vector2f one, Vector2f two)
    {
        float num = (one.x * two.x) + (one.y * two.y);
        return num * num;
    }
    
    /**
     * Adds the two vector together and returns the resulting vector
     * @param one Vector2f -> operand 1
     * @param two Vectpr2f -> operand 2
     * @return Vector2f -> the resulting vector one + two
     */
    public static Vector2f addVectors(Vector2f one, Vector2f two)
    {
        return new Vector2f(one.x + two.x, one.y + two.y);
    }
    
    /**
     * Subtracts two from one and returns the resulting vector.
     * @param one Vector2f -> operand 1
     * @param two Vector2f -> operand 2
     * @return  Vector2f -> the resulting Vector one - two
     */
    public static Vector2f subtractVectors(Vector2f one, Vector2f two)
    {
        return new Vector2f(one.x - two.x, one.y - two.y);
    }
    
    /**
     * Scales the vector by the given constant and returns the resulting vector
     * @param vector Vector2f -> The Vector to be scaled.
     * @param scalar float -> the constant term
     * @return Vector2f -> scalar * Vector
     */
    public static Vector2f scale(Vector2f vector, float scalar)
    {
        return new Vector2f(vector.x * scalar, vector.y * scalar);
    }

	public static float distance(Vector2f one, Vector2f two)
	{
		return (float)Math.sqrt((one.x - two.x) * (one.x - two.x) + (one.y - two.y) * (one.y - two.y));
	}

	public static float distanceSquared(Vector2f one, Vector2f two)
	{
		return (one.x - two.x) * (one.x - two.x) + (one.y - two.y) * (one.y - two.y);
	}

	private static Random random = new Random();
	public static Vector2f randomDirection()
	{
		Vector2f randomVector = new Vector2f(random.nextFloat(), random.nextFloat());
		
		if(random.nextBoolean())
		{
			randomVector.x *= -1; 
		}
		if(random.nextBoolean())
		{
			randomVector.y *= -1;
		}

		return randomVector;
	}
		
	/**
	 * Okay so basically we want to calculate the toroidial position of boids in limbo(the area off the screen they have to travel before being teleport to the other side of the screen).
	 */
	public static Vector2f toroidialPosition(Vector2f position)
	{
		Vector2f toroidialPosition = new Vector2f(position);		

		if(position.x < 0)
		{
			toroidialPosition.x = Config.width - position.x;
		}
		else if(position.x > Config.width)
		{
			toroidialPosition.x = -(position.x - Config.width);
		}

		if(position.y < 0)
		{
			toroidialPosition.y = Config.height - position.y;
		}
		else if(position.y > Config.height)
		{
			toroidialPosition.y = -(position.y - Config.height);
		}

		return toroidialPosition;
		
	}
}
