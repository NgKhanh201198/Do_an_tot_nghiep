package nguyenkhanh.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_booked")
public class RoomBookedEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "bookingroomid", nullable = false)
	private Long bookingroomid;

	@Id
	@Column(name = "roomid", nullable = false)
	private Long roomid;

	public RoomBookedEntity() {
		super();
	}

	public Long getBookingroomid() {
		return bookingroomid;
	}

	public void setBookingroomid(Long bookingroomid) {
		this.bookingroomid = bookingroomid;
	}

	public Long getRoomid() {
		return roomid;
	}

	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}

}
