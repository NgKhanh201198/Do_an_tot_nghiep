package nguyenkhanh.backend.dto;

public class CountDTO {
	private long customer;
	private long order;
	private long hotel;
	private long room;

	public CountDTO() {
		super();
	}

	public CountDTO(long customer, long order, long hotel, long room) {
		super();
		this.customer = customer;
		this.order = order;
		this.hotel = hotel;
		this.room = room;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public long getHotel() {
		return hotel;
	}

	public void setHotel(long hotel) {
		this.hotel = hotel;
	}

	public long getRoom() {
		return room;
	}

	public void setRoom(long room) {
		this.room = room;
	}

}
