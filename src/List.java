
public class List {
	
	Node header;
	int size;
	
	public List () {
		this.header = null;
		this.size = 0;
	}

	public void addNode ( String url, double sr, double sfr ) {
		
		Node node = new Node (url, sr, sfr);
		
		if ( this.header == null ) {
			this.header = node;
		} else {
			
			Node aux = this.header;
			while ( aux.right != null ) {
				aux = aux.right;
			}
			
			aux.right = node;
			size++;
			
		}
		
	}
	
	public Node getHeader() {
		return header;
	}

	public void setHeader(Node header) {
		this.header = header;
	}
	
}
