package watsoncui.nkcms.algorithm.distance;

public class StringVectorDistance {

	private byte[] vec0;
	private byte[] vec1;
	private int vec0length;
	private int vec1length;
	
	public StringVectorDistance() {
		this.vec0length = -1;
		this.vec1length = -1;
	}
	
	public StringVectorDistance(byte[] vec0, byte[] vec1) {
		setVecs(vec0, vec1);
	}
	
	public int editDistance() {
		if(!isLegal()) {
			return Integer.MIN_VALUE;
		}
		
		int[][] matrix = new int[vec0length + 1][vec1length + 1];
		for(int i = 0; i < vec0length + 1; i++) {
			matrix[i][0] = i;
		}
		for(int j = 1; j < vec1length + 1; j++) {
			matrix[0][j] = j;
		}
		
		for(int i = 1; i < vec0length + 1; i++) {
			for(int j = 1; j < vec1length + 1; j++) {
				matrix[i][j] = min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, matrix[i - 1][j - 1] + charDistance(vec0[i - 1], vec1[j - 1]));
			}
		}

		for(int i = 0; i < vec0length + 1; i++) {
			for(int j = 0; j < vec1length + 1; j++) {
				System.out.print(matrix[i][j]);
				System.out.print('\t');
			}
			System.out.println();
		}
		
		return matrix[vec0length][vec1length];
	}
	
	public int OptimalStringAlignmentDistance() {
		if(!isLegal()) {
			return Integer.MIN_VALUE;
		}
		
		int[][] matrix = new int[vec0length + 1][vec1length + 1];
		for(int i = 0; i < vec0length + 1; i++) {
			matrix[i][0] = i;
		}
		for(int j = 1; j < vec1length + 1; j++) {
			matrix[0][j] = j;
		}
		
		for(int i = 1; i < vec0length + 1; i++) {
			for(int j = 1; j < vec1length + 1; j++) {
				matrix[i][j] = min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, matrix[i - 1][j - 1] + charDistance(vec0[i - 1], vec1[j - 1]));
				if((i > 1) && (j > 1)) {
					if((vec0[i - 1] == vec1[j - 2]) && (vec0[i - 2] == vec1[j - 1])) {
						int transposition = matrix[i - 2][j - 2] + transpositionDistance(vec0[i - 1], vec1[j - 1]);
						if(matrix[i][j] > transposition) {
							matrix[i][j] = transposition;
						}
					}
				}
			}
		}
		
		for(int i = 0; i < vec0length + 1; i++) {
			for(int j = 0; j < vec1length + 1; j++) {
				System.out.print(matrix[i][j]);
				System.out.print('\t');
			}
			System.out.println();
		}
		
		return matrix[vec0length][vec1length];
	}
	
	private int transpositionDistance(byte b0, byte b1) {
		return charDistance(b0, b1);
	}
	
	private int charDistance(byte b0, byte b1) {
		if(b0 == b1) {
			return 0;
		} else {
			return 1;
		}
	}
	
	private int min(int i0, int i1, int i2) {
		int result = i0;
		if(i1 < result) {
			result = i1;
		}
		if(i2 < result) {
			result = i2;
		}
		return result;
	}
	
	private boolean isLegal() {
		if ((this.vec0length < 0) || (this.vec1length < 0)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void setVecs(byte[] vec0, byte[] vec1) {
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
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str1 = "ac";
		String str2 = "ca";
		StringVectorDistance svd = new StringVectorDistance(str1.getBytes(), str2.getBytes());
		System.out.println(svd.editDistance());
		System.out.println(svd.OptimalStringAlignmentDistance());
	}

}
