package main;

public class WriteRank implements Comparable<WriteRank> {
	
	String name;
	

	int score;
	
	WriteRank(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	

	@Override
	public int compareTo(WriteRank o) {
		// TODO Auto-generated method stub
		return this.score > o.score ? 1 : 0;
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


	
	

}
