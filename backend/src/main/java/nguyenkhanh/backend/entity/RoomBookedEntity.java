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
	@Column(name = "bookingrateid", nullable = false)
	private Long bookingrateid;

	@Id
	@Column(name = "roomid", nullable = false)
	private Long roomid;

	public RoomBookedEntity() {
		super();
	}

	public Long getBookingrateid() {
		return bookingrateid;
	}

	public void setBookingrateid(Long bookingrateid) {
		this.bookingrateid = bookingrateid;
	}

	public Long getRoomid() {
		return roomid;
	}

	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}

}
