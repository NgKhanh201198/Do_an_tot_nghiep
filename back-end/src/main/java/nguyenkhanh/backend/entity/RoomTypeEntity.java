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
@Table(name = "room_type")
public class RoomTypeEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomtypeid")
	private Long roomTypeID;

	@Column(name = "roomtypename")
	private String roomTypeName;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<RoomEntity> rooms = new HashSet<RoomEntity>();

	public Long getRoomTypeID() {
		return roomTypeID;
	}

	public void setRoomTypeID(Long roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
