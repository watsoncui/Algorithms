package watsoncui.nkcms.algorithm.distance;

public class BooleanVectorDistance {

	private boolean[] vec0;
	private boolean[] vec1;
	private int vec0length;
	private int vec1length;
	private int n10;
	private int n11;
	private int n00;
	private int n01;
	
	public BooleanVectorDistance() {
		this.vec0length = -1;
		this.vec1length = -1;
	}
	
	public BooleanVectorDistance(boolean[] vec0, boolean[] vec1) {
		setVecs(vec0, vec1);
	}
	
	public double JaccardDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		
		if((n10+n01+n11) > 0) {
			return ((double)(n10+n01))/((double)(n10+n01+n11));
		} else {
			return 0;
		}
	}
	
	public double MatchingDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		if((n10+n01+n11+n00) > 0) {
			return ((double)(n10+n01))/((double)(n10+n01+n11+n00));
		} else {
			return 0;
		}
	}
	
	public double DiceDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		if((n10+n01+n11) > 0) {
			return ((double)(n10+n01))/((double)(n10+n01+2*n11));
		} else {
			return 0;
		}
	}
	
	public double RogersTanimotoDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		if((n10+n01+n11+n00) > 0) {
			return ((double)((n10+n01)*2))/((double)(2*(n10+n01)+n11+n00));
		} else {
			return 0;
		}
	}
	
	public double RussellRaoDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		if((n10+n01+n11+n00) > 0) {
			return ((double)(n10+n01+n00))/((double)(n10+n01+n11+n00));
		} else {
			return 0;
		}
	}
	
	public double SokalSneathDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		if((n10+n01+n11) > 0) {
			return ((double)((n10+n01)*2))/((double)(2*(n10+n01)+n11));
		} else {
			return 0;
		}
	}
	
	public double YuleDissimilarity() {
		if(!islegal()) {
			return Double.NaN;
		}
		if((n10+n01+n11+n00) > 0) {
			return ((double)n10*(double)n01*2)/((double)n10*(double)n01+(double)n11*(double)n00);
		} else {
			return 0;
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
	
	private void calN10N11N01N00() {
		int length = minOfLengths();
		int n10 = 0;
		int n11 = 0;
		int n00 = 0;
		int n01 = 0;
		for(int i = 0; i < length; i++) {
			if((vec0[i]) && (vec1[i])) {
				n11++;
			}
			if((vec0[i]) && (!vec1[i])) {
				n10++;
			}
			if((!vec0[i]) && (!vec1[i])) {
				n00++;
			}
			if((!vec0[i]) && (vec1[i])) {
				n01++;
			}
		}
		if(vec0length > length) {
			for(int i = length; i < vec0length; i++) {
				if(vec0[i]) {
					n10++;
				} else {
					n00++;
				}
			}
		} else {
			for(int i = length; i< vec1length; i++) {
				if(vec1[i]) {
					n01++;
				} else {
					n00++;
				}
			}
		}
		
		this.n00 = n00;
		this.n01 = n01;
		this.n10 = n10;
		this.n11 = n11;
	}

	private int minOfLengths() {
		return (vec0length > vec1length)?vec1length:vec0length;
	}
	
	public void setVecs(boolean[] vec0, boolean[] vec1) {
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
			this.vec0length = -1;
		}
		
		if(islegal()) {
			calN10N11N01N00();
		}
	}
}
