
public class Node {
	
	String url;
	double sr;
	double sfr;
	double srResult;
	double sfrResult;
	int totalRequests;
	Node right;
	
	public Node ( String url, double sr, double sfr) {
		
		this.url = url;
		this.sr = sr;
		this.sfr = sfr;
		this.srResult = 0;
		this.sfrResult = 0;
		this.totalRequests = 0;
		this.right = null;
		
	}
	
	public double getSrResult() {
		return srResult;
	}

	public void setSrResult(double srResult) {
		this.srResult = srResult;
	}

	public double getSfrResult() {
		return sfrResult;
	}

	public void setSfrResult(double sfrResult) {
		this.sfrResult = sfrResult;
	}

	public int getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getSr() {
		return sr;
	}

	public void setSr(double sr) {
		this.sr = sr;
	}

	public double getSfr() {
		return sfr;
	}

	public void setSfr(double sfr) {
		this.sfr = sfr;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
	

}
