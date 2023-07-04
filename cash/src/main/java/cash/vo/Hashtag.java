package cash.vo;

public class Hashtag {
	
	public Hashtag() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Hashtag(int cashbookNo, String word, String createdate, String updatedate) {
		super();
		this.cashbookNo = cashbookNo;
		this.word = word;
		this.createdate = createdate;
		this.updatedate = updatedate;
	}

	private int cashbookNo;
	private String word;
	private String createdate;
	private String updatedate;
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	@Override
	public String toString() {
		return "Hashtag [cashbookNo=" + cashbookNo + ", word=" + word + ", createdate=" + createdate + ", updatedate="
				+ updatedate + "]";
	}
	
}
