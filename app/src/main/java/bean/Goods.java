package bean;

import java.io.Serializable;



public class Goods implements Serializable{
	public Goods(int id,int number){
		this._id=id;
		this._number=number;
	}
	public Goods(String _title,String _img_url, String _content, int _click, int _number,
			boolean isHave, int _id,  String _sell_price) {
		super();
		this._img_url = _img_url;
		this._content = _content;
		this._click = _click;
		this._number = _number;
		this.isHave = isHave;
		this._id = _id;
		this._title = _title;
		this._sell_price = _sell_price;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**��ƷͼƬ����*/
	private String _img_url;
	/**��Ʒ����*/
	private String _content;
	private int _click;
	/**��Ʒ��������*/
	private int _number;
	/**�Ƿ�����Ʒ*/
	private boolean isHave;
	public Goods() {
	}
	/**��Ʒ���*/
	private int _id;
	/**��Ʒ����*/
	private String _title;
	/**��Ʒ�۸�*/
	private String _sell_price;
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_sell_price() {
		return _sell_price;
	}

	public void set_sell_price(String _sell_price) {
		this._sell_price = _sell_price;
	}

	public String get_img_url() {
		return _img_url;
	}

	public void set_img_url(String _img_url) {
		this._img_url = _img_url;
	}

	public String get_content() {
		return _content;
	}

	public void set_content(String _content) {
		this._content = _content;
	}

	public int get_click() {
		return _click;
	}

	public void set_click(int _click) {
		this._click = _click;
	}

	public int get_number() {
		return _number;
	}

	public void set_number(int _number) {
		this._number = _number;
	}

	public boolean isHave() {
		return isHave;
	}

	public void setHave(boolean isHave) {
		this.isHave = isHave;
	}

	

	

}
