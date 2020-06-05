package net.atpco.ndc.filtercheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suspect {
	private String carrier;
	private String origin;
	private String destination;
}
