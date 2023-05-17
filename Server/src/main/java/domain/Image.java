package domain;

import java.awt.image.BufferedImage;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
/**
 * A class to save images into the database
 * @author a-rasines
 *
 */
@PersistenceCapable(detachable = "true")
public class Image {
	/**
	 * An enum to define where the image belongs, relative to image ctx in HTTP exchanges
	 *
	 */
	public enum ImageType {
		HOTEL_ICON
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private long id;
	private BufferedImage image;
	private String format;
	private ImageType type;
	private String oid;
	/**
	 * Generates a handler for an image in the database
	 * @param image the image itself
	 * @param format format to decode to
	 * @param type place where the image belongs
	 * @param oid id of the object related to the image
	 */
	public Image(BufferedImage image, String format, ImageType type, String oid) {
		this.image = image;
		this.format = format;
		this.type = type;
		this.oid = oid;
	}
	public void setImage(BufferedImage image, String format) {
		this.image = image;
		this.format = format;
	}
	/**
	 * Get the id in the database
	 * @return id of the row in the database
	 */
	public long getId() {
		return id;
	}
	/**
	 * Get the image data
	 * @return the image in {@linkplain java.awt.image.BufferedImage BufferedImage} format.
	 * 
	 * To know in which format decode it, use {@link domain.Image#getFormat() getFormat()}
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * The format in which the image from {@link domain.Image#getImage() getImage()} is encoded
	 * @return
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * Place where the image should be displayed. Linked to image ctx in HTTP exchanges
	 * @return
	 */
	public ImageType getType() {
		return type;
	}
	/**
	 * Id of the object related to this object, use it with {@link domain.Image#getType() getType()} as objects from multiple tables may share id
	 * @return
	 */
	public String getOid() {
		return oid;
	}
}
