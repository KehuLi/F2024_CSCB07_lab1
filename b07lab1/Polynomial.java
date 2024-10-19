package b07lab1;

// 类名称: Polynomial
public class Polynomial {
	// 状态名称: coefficients
	// 状态特征: array of double
	private double[] coefficients;


	// 构造函数(constructor):初始化类
	public Polynomial() {
		// 初始化类的coefficients为[0](题目要求)
	    // set the Polynomial to default value
	    coefficients = new double[]{0};
	}

	public Polynomial(double[] coefficients_input) {
		// set the Polynomial to the value given
		this.coefficients = coefficients_input;
	}

	// 方法add()
	public Polynomial add(Polynomial other) {
		// 确定多项式如何相加...
		// 如果两个多项式长度不等，则返回值应以长的为准
		
		// 找出MaxLength
		int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
		// 创建长度为MaxLength的系数数组。
		double[] coefficients_result = new double[maxLength];
		
		// 开始相加...
		// 需要遍历两个数组，一一相加，如果length到头了，补0，直到遍历完成
		
		// 设置遍历规则
		for (int i = 0; i < maxLength; i++) {
			double thisCoef = i < this.coefficients.length ? this.coefficients[i] : 0;
			double otherCoef = i < other.coefficients.length ? other.coefficients[i] : 0;
			coefficients_result[i] = thisCoef + otherCoef;
		}
		
		Polynomial result = new Polynomial(coefficients_result);
		return result;
	}

	// 方法evaluate()
	public double evaluate(double x) {
		// 确定多项式如何运算
		// 代入x值
		// 遍历系数数组，按照系数的顺序乘以次方，乘以当前系数
		
		//局部变量*必须*手动初始化
		double result = 0.0;
		for (int i=0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, i);
		}
		return result;
	}

	// 方法hasRoot()
	public boolean hasRoot(double x) {
		// 确定方法如何运算
		// 确定x是否为多项式的解
		// 代入x进入多项式看是否为0
		
		return this.evaluate(x) == 0;
	}

}

