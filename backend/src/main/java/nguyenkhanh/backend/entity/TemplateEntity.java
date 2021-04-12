package nguyenkhanh.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "templates")
public class TemplateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "templateid")
	private Long templateID;

	@Column(name = "descriptions")
	private String descriptions;

	@Column(name = "contents")
	private String contents;

	public TemplateEntity() {
		super();
	}

	public TemplateEntity(Long templateID, String descriptions, String contents) {
		super();
		this.templateID = templateID;
		this.descriptions = descriptions;
		this.contents = contents;
	}

	public Long getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Long templateID) {
		this.templateID = templateID;
	}

	public String getDescription() {
		return descriptions;
	}

	public void setDescription(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
