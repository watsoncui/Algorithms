package watsoncui.nkcms.algorithm.distance;

public class BooleanVectorDistance {

	private boolean[] vec0;
	private boolean[] vec1;
	private int vec0length;
	private int vec1length;
	
	public BooleanVectorDistance() {
		this.vec0length = -1;
		this.vec1length = -1;
	}
	
	public BooleanVectorDistance(boolean[] vec0, boolean[] vec1) {
		this.vec0 = vec0;
		this.vec1 = vec1;
		if(null != vec0) {
			this.vec0length = vec0.length;
		} else {
			this.vec0length = -1;
		}
		if(null != vec1) {
			this.vec1length = vec1.length;
		} else {
			this.vec1length = -1;
		}
	}
	
	public double JaccardDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private boolean islegal() {
		if ((this.vec0length < 0) || (this.vec1length < 0)) {
			return false;
		} else {
			return true;
		}
	}

	public void setVec0(boolean[] vec0) {
		this.vec0 = vec0;
		if(null != vec0) {
			this.vec0length = vec0.length;
		} else {
			this.vec0length = -1;
		}
	}

	public void setVec1(boolean[] vec1) {
		this.vec1 = vec1;
		if(null != vec1) {
			this.vec1length = vec1.length;
		} else {
			this.vec0length = -1;
		}
	}
}
