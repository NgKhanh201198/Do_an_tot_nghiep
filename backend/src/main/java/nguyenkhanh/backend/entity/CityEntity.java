package nguyenkhanh.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "citys")
public class CityEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cityid")
	private Long id;

	@Column(name = "cityname")
	private String cityName;

	@Column(name = "description")
	private String description;

	@Column(name = "image")
	private String image;

	@OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<HotelEntity> hotels = new HashSet<HotelEntity>();
	
	
	public CityEntity() {
		super();
	}

	public CityEntity(String cityName, String description, String image) {
		super();
		this.cityName = cityName;
		this.description = description;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
