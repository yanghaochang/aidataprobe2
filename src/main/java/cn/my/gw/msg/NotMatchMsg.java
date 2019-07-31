package cn.my.gw.msg;

public class NotMatchMsg extends MsgHead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] by = new byte[0];

	public byte[] getBy() {
		return by;
	}

	public void setBy(byte[] by) {
		this.by = by;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("NotMatchMessage:");
		buff.append(super.toString());
		buff.append(" dump:");
		for (int i = 0; i < by.length; i++)
			buff.append(Integer.toHexString(by[i] & 0xff) + " ");

		return buff.toString();
	}
}
