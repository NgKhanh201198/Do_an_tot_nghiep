package nguyenkhanh.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomTypeDTO {
	@NotBlank(message = "{RoomTypeName.NotBlank}")
	@Size(max = 18, message = "{RoomTypeName.Size}")
	private String roomTypeName;

	@NotBlank(message = "{Description.NotBlank}")
	@Size(max = 255, message = "{Description.Size}")
	private String description;

	public RoomTypeDTO() {
		super();
	}

	public RoomTypeDTO(String roomTypeName, String description) {
		super();
		this.roomTypeName = roomTypeName;
		this.description = description;
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
