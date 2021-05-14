package nguyenkhanh.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CityDTO {

	@NotBlank(message = "{CityName.NotBlank}")
	@Size(max = 18, message = "{CityName.Size}")
	private String cityName;

	@NotBlank(message = "{Description.NotBlank}")
	@Size(max = 255, message = "{Description.Size}")
	private String description;

	public CityDTO() {
		super();
	}

	public CityDTO(String cityName, String description) {
		super();
		this.cityName = cityName;
		this.description = description;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
