package initializeFramework;

import org.example.test.Initialize;

public class InitializeFramework
{
	public static Initialize init = new Initialize("./Data/configuration");

	public static void Reinitialize()
	{
		init = new Initialize("./Data/configuration");
	}
}
