package net.atpco.ndc.filtercheck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(Config.class)
@SpringBootApplication
public class FilterCheckApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilterCheckApplication.class, args);
	}

	@Autowired
	private SuspectFinder suspectFinder;
	
	@Autowired
	private SuspectVerifier suspectVerifier;

	@Override
	public void run(String... args) throws Exception {
		if (args != null && args.length == 1) {
			String numberOfDays = args[0];
			final int n = tryParse(numberOfDays);
			if (n > 0) {
				SuspectFinder suspectFinder = new SuspectFinder();
				List<Suspect> suspects = suspectFinder.findSuspects();
				boolean foundOne = false;
				System.out.println("Found flights for:");
				for (Suspect suspect: suspects) {
					if (suspectVerifier.hasFlights(suspect)) {
						System.out.println(suspect.getCarrier() + "," + suspect.getOrigin() + ","+ suspect.getDestination());
						foundOne = true;
					}
				}
				if (!foundOne) {
					System.out.println("No flights found");
				}
			} else {
				System.out.println("The argument must be a number");
			}
		} else {
			System.out.println("Need the number of days to look back, no defaults");
		}

	}

	private int tryParse(String n) {
		try {
			return Integer.parseInt(n);
		} catch (NumberFormatException nfe) {

		}
		return 0;

	}

}
