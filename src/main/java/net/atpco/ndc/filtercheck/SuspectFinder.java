package net.atpco.ndc.filtercheck;

import java.util.ArrayList;
import java.util.List;

public class SuspectFinder {

	public List<Suspect> findSuspects() {
		// TODO Auto-generated method stub
		List<Suspect> suspects =  new ArrayList<>();
		suspects.add(new Suspect("AC","YQB" , "YUL"));
		return suspects;
	}

}
