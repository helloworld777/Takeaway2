package bean;

public class EventBean {
	private Object data;
	public final static int CHANGE_FRAGMENT=0;
	public final static int SHOW_MENU=1;
	public final static int REMOVE_ORDER=2;
	
	public interface EventType{
		public final static int REMOVE_ORDER=2;
		public final static int ADD_ORDER=3;
	}
	private int eventType;
	public Object getData() {
		return data;
	}

	public EventBean(int eventType,Object data) {
		super();
		this.setEventType(eventType);
		this.data = data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
}
