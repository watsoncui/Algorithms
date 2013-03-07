package watsoncui.nkcms.algorithm.netflix;

public class ScoreTriple {
	private String movie;
	private String user;
	private int score;
	
	public ScoreTriple() {
		
	}
	
	public ScoreTriple(String movie, String user, int score) {
		this.movie = movie;
		this.user = user;
		this.score = score;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
