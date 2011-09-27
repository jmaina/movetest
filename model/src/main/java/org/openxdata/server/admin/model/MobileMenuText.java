package org.openxdata.server.admin.model;

/**
 * This class contains text for a given mobile menu item in a particular locale.
 * With the help of this class, we can translate mobile menu text at runtime
 * as they come from the database instead of having to be compiled in the application.
 */
public class MobileMenuText extends AbstractEditable{
	
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -1374321447951545934L;

	/** The database identifier for the menu text. */
	private int menuTextId = 0;
	
	/** The locale key for the menu text. */
	private String localeKey;
	
	/** The unique identifier for the menu item. eg Exit=1, Close=2, Cancel=3, and more. */
	private short menuId;
	
	/** The text for the menu item in the locale as given by the localeKey field. */
	private String menuText;
	
	/**
	 * Constructs a new mobile menu text object.
	 */
	public MobileMenuText(){
		
	}
	
	public int getMenuTextId() {
		return menuTextId;
	}
	
	@Override
	public int getId() {
		return menuTextId;
	}
	
	public void setMenuTextId(int menuTextId) {
		this.menuTextId = menuTextId;
	}
	
	public String getLocaleKey() {
		return localeKey;
	}
	
	public void setLocaleKey(String localeKey) {
		this.localeKey = localeKey;
	}
	
	public short getMenuId() {
		return menuId;
	}
	
	public void setMenuId(short menuId) {
		this.menuId = menuId;
	}
	
	public String getMenuText() {
		return menuText;
	}
	
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
}
