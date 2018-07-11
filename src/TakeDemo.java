import net.jini.space.JavaSpace05;
import java.util.ArrayList;
import java.util.Collection;

public class TakeDemo {

	// A demo to show how the JavaSpace05 allows a "take" operation that
	// takes a collection of objects from the space all at the same time.
	// The take method is passed a collection of templates, and returns
	// all objects that match any of these templates.

	private final static int FIVE_SECONDS = 1000 * 5; // that's 5000 Milliseconds
	private final static int NUMBER_OF_OBJECTS_TO_RETURN = 100;

	public static void main(String[] args) {

		// Get a reference to the space, and cast it to a JavaSpace05
		JavaSpace05 space = (JavaSpace05) SpaceUtils.getSpace();
		if (space == null) {
			System.err.println("JavaSpace not found.");
			System.exit(1);
		}

		// write some objects to the space
		// for this demo we write 10 "SObj" objects
		for (int i = 0; i < 10; i++) {
			Sobj s = new Sobj("Object " + i);
			try {
				space.write(s, null, FIVE_SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// now read them all back at once

		// the function requires a list of templates
		// For this demo we will set up the Collection of templates, but add
		//     just one template to that list
		Collection<Sobj> templates = new ArrayList<>();

		Sobj template = new Sobj();
		templates.add(template);

		try {
			// take the matching objects
			// the parameters to the take command are:
			// the collection of templates, transaction, timeout, maximum number
			//     of objects to retrieve
			Collection<?> results = space.take(templates, null, FIVE_SECONDS, NUMBER_OF_OBJECTS_TO_RETURN);

			// print out the results
			for (Object result : results) {
				Sobj s = (Sobj) result;
				System.out.println(s.contents);
			}

			// That's it, so close down
            System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
