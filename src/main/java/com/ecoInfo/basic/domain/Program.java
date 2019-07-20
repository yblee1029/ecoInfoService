package com.ecoInfo.basic.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"regionMapping"})
public class Program implements Serializable {

	private static final long serialVersionUID = 7358242943473807765L;

	@Id
	@GenericGenerator(name = "id_generator", strategy = "com.ecoInfo.basic.domain.generator.IdGenerator", parameters = {
			@Parameter(name = "table_name", value = "program_id_generator"),
			@Parameter(name = "value_column_name", value = "key_number"),
			@Parameter(name = "segment_column_name", value = "name"),
			@Parameter(name = "segment_value", value = "names_key"), @Parameter(name = "prefix_key", value = "prg") })
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
	private String id;

	@NotEmpty
	@Column(nullable = false)
	private String prgmName;

	@NotEmpty
	@Column(nullable = false)
	private String theme;

	@NotEmpty
	@Column(nullable = false)
	private String serviceRegion;

	@Column(length = 1000)
	private String prgmIntro;

	@Lob
	private String prgmDetails;
	
	@Transient
	private String region_id;

	@ManyToMany()
	@JoinTable(name = "program_mapping", joinColumns = @JoinColumn(name = "program_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "region_id", referencedColumnName = "id"))
	Set<Region> regionMapping;
}