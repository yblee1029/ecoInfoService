package com.ecoInfo.basic.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDTO {

	private String program;

	@NotEmpty
	private String prgmName;

	@NotEmpty
	private String theme;

	@NotEmpty
	private String serviceRegion;

	@NotEmpty
	private String prgmIntro;

	@NotEmpty
	private String prgmDetails;

	@Data
	@EqualsAndHashCode(callSuper = false)
	@ToString(callSuper = true)
	@JsonPropertyOrder({ "number", "prgmName", "theme", "serviceRegion", "prgmIntro", "prgmDetails" })
	public static class ProgramCsvDTO extends ProgramDTO {
		private String number;
	}

}
