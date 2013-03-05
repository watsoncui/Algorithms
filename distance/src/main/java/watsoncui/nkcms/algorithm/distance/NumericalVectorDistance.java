package watsoncui.nkcms.algorithm.distance;

public class NumericalVectorDistance {

	
	private double[] vec1;
	private double[] vec2;
	private int length;
	
	public NumericalVectorDistance(double[] vec1, double[] vec2) {
		setVecs(vec1, vec2);
	}
	
	public double euclideanDistance() {
		double result = squaredEuclideanDistance();
		if(Double.isNaN(result)) {
			return result;
		} else {
			return Math.sqrt(result);
		}
	}
	
	public double squaredEuclideanDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		
		double[] vec = new double[length];
		for(int i = 0; i < length; i++) {
			vec[i] = vec1[i] - vec2[i];
		}
		return squaredNorm2(vec, length);
	}
	
	public double normSquaredEuclideanDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		double meanVec1 = mean(vec1, length);
		double meanVec2 = mean(vec2, length);
		
		double denominator = squaredNorm2(vec1, length) + squaredNorm2(vec2, length);
		if(denominator < Double.MIN_NORMAL) {
			return 0;
		} else {
			double[] molecular = new double[length];
			for(int i = 0; i < length; i++) {
				molecular[i] = vec1[i] - vec2[i] - meanVec1 + meanVec2;
			}
			return squaredNorm2(molecular, length)/denominator/2;
		}
	}
	
	public double manhattanDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		double[] vec = new double[length];
		for(int i = 0; i < length; i++) {
			vec[i] = vec1[i] - vec2[i];
		}
		return norm1(vec, length);
	}
	
	public double chessBoardDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		double[] vec = new double[length];
		for(int i = 0; i < length; i++) {
			vec[i] = vec1[i] - vec2[i];
		}
		return normInfinity(vec, length);
	}
	
	public double brayCurtisDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		double[] minus = new double[length];
		double[] sum = new double[length];
		for(int i = 0; i < length; i++) {
			minus[i] = vec1[i] - vec2[i];
			sum[i] = vec1[i] + vec2[i];
		}
		
		double denominator = norm1(sum, length);
		if(denominator < Double.MIN_NORMAL) {
			return 0;
		} else {
			return norm1(minus, length)/denominator;
		}
	}
	
	public double canberraDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		double result = 0;
		for(int i = 0; i < length; i++) {
			double denominator = Math.abs(vec1[i])+Math.abs(vec2[i]);
			if(denominator > Double.MIN_NORMAL) {
				result += Math.abs(vec1[i] - vec2[i])/denominator;
			}
		}
		return result;
	}
	
	public double cosineDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		double denominator = Math.sqrt(squaredNorm2(vec1, length) * squaredNorm2(vec2, length));
		if(denominator < Double.MIN_NORMAL) {
			return 0;
		} else {
			double molecular = 0;
			for(int i = 0; i < length; i++) {
				molecular += vec1[i] * vec2[i];
			}
			return 1 - molecular/denominator;
		}
	}
	
	public double correlationDistance() {
		if(!isLegal()) {
			return Double.NaN;
		}
		
		double meanVec1 = mean(vec1, length);
		double meanVec2 = mean(vec2, length);
		
		double[] normVec1 = new double[length];
		double[] normVec2 = new double[length];
		for(int i = 0; i < length; i++) {
			normVec1[i] = vec1[i] - meanVec1;
			normVec2[i] = vec2[i] - meanVec2;
		}
		double denominator = Math.sqrt(squaredNorm2(normVec1, length) * squaredNorm2(normVec2, length));
		if(denominator < Double.MIN_NORMAL) {
			return 0;
		} else {
			double molecular = 0;
			for(int i = 0; i < length; i++) {
				molecular += normVec1[i] * normVec2[i];
			}
			return 1 - molecular/denominator;
		}
	}
	
	private double normInfinity(double[] vec, int length) {
		double result = 0;
		for(int i = 0; i < length; i++) {
			if(Math.abs(vec[i]) > result) {
				result = Math.abs(vec[i]);
			}
		}
		return result;
	}
	
	private double norm1(double[] vec, int length) {
		double result = 0;
		for(int i = 0; i < length; i++) {
			result += Math.abs(vec[i]);
		}
		return result;
	}
	private double squaredNorm2(double[] vec,int length) {
		double result = 0;
		for(int i = 0; i < length; i++) {
			result += vec[i]*vec[i];
		}
		return result;
	}
	
	private double mean(double[] vec, int length) {
		if(length == 0) {
			return 0;
		} else {
			double result = 0;
			for(double v:vec) {
				result += v;
			}
			return result/length;
		}
	}
	
	private boolean isLegal() {
		if(this.length > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setVecs(double[] vec1, double[] vec2) {
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.length = -1;
		if((null != vec1) && (null != vec2)) {
			if(vec1.length == vec2.length) {
				this.length = vec1.length;
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
