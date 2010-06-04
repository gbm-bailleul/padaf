/**
 * 
 */
package net.padaf.xmpbox.type;

import java.util.List;

import net.padaf.xmpbox.XMPMetadata;



/**
 * Object representation of an Thumbnail XMP type
 * @author eric
 */
public class ThumbnailType extends ComplexPropertyContainer {
	protected static final String elementNs = "http://ns.adobe.com/xap/1.0/g/img/";
	protected XMPMetadata metadata;
	/**
	 * 
	 * @param metadata
	 * @param namespace
	 * @param prefix
	 * @param propertyName
	 */
	public ThumbnailType(XMPMetadata metadata, String namespace, String prefix,
			String propertyName) {
		super(metadata, namespace, prefix, propertyName);
		this.metadata=metadata;
		setAttribute(new Attribute(null, "rdf", "parseType", "Resource"));
	}

	/**
	 * 
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 */
	public ThumbnailType(XMPMetadata metadata, String prefix,
			String propertyName) {
		super(metadata, prefix, propertyName);
		this.metadata=metadata;
		setAttribute(new Attribute(null, "rdf", "parseType", "Resource"));
	}

	/**
	 * Give the first property found in this container with type and localname expected 
	 * @param localName
	 * @param type
	 * @return
	 */
	protected AbstractField getFirstEquivalentProperty(String localName, Class<? extends AbstractField> type){
		List<AbstractField> list=getPropertiesByLocalName(localName);
		if(list!=null){
			for (AbstractField abstractField : list) {
				//System.out.println(abstractField.getQualifiedName());
				if(abstractField.getClass().equals(type) ){
					return abstractField;
				}
			}
		}
		return null;
	}
	
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		AbstractField absProp=getFirstEquivalentProperty("height", IntegerType.class);
		if(absProp!=null){
			return ((IntegerType)absProp).getValue();
		}
		return null;
	}

	/**
	 * 
	 * @param prefix
	 * @param name
	 * @param height
	 */
	public void setHeight(String prefix, String name, Integer height) {
		this.addProperty(new IntegerType(metadata, prefix, name, height));
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		AbstractField absProp=getFirstEquivalentProperty("width", IntegerType.class);
		if(absProp!=null){
			
			return ((IntegerType)absProp).getValue();
		}
		return null;
	}

	/**
	 * 
	 * @param prefix
	 * @param name
	 * @param width
	 */
	public void setWidth(String prefix, String name, Integer width) {
		this.addProperty(new IntegerType(metadata, prefix, name, width));
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		AbstractField absProp=getFirstEquivalentProperty("image", TextType.class);
		if(absProp!=null){
			return ((TextType)absProp).getStringValue();
		}
		return null;
	}

	/**
	 * 
	 * @param prefix
	 * @param name
	 * @param image
	 */
	public void setImg(String prefix, String name, String image) {
		this.addProperty(new TextType(metadata, prefix, name, image));
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		AbstractField absProp=getFirstEquivalentProperty("format", TextType.class);
		if(absProp!=null){
			return ((TextType)absProp).getStringValue();
		}
		return null;
	}

	/**
	 * 
	 * @param prefix
	 * @param name
	 * @param format
	 */
	public void setFormat(String prefix, String name, String format) {
		this.addProperty(new TextType(metadata, prefix, name, format));
	}
	
	
}