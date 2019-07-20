package com.ecoInfo.basic.domain;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@EqualsAndHashCode(callSuper = false, exclude = {"programs"})
public class Region implements Serializable {

	private static final long serialVersionUID = 6086699216926702556L;

	@Id
	@GenericGenerator(name = "id_generator", strategy = "com.ecoInfo.basic.domain.generator.IdGenerator", parameters = {
			@Parameter(name = "table_name", value = "region_id_generator"),
			@Parameter(name = "value_column_name", value = "key_number"),
			@Parameter(name = "segment_column_name", value = "name"),
			@Parameter(name = "segment_value", value = "names_key"), @Parameter(name = "prefix_key", value = "reg") })
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
	private String id;

	@NotEmpty
	@Column(nullable = false)
	private String regionName;

	private String rootName;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "regionMapping")
	Set<Program> programs;

	public String getFullRegionName() {
		return Optional.ofNullable(getRootName()).map(s -> s + " ").orElse("") + getRegionName();
	}

}
