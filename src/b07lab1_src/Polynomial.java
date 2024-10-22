package b07lab1_src;
import java.io.*;
import java.util.Scanner;

// 类名称: Polynomial
public class Polynomial {
    /* ------------------------------------------------------------------------------- */
    /*                                字段区 Fields Area                                */
    /* ------------------------------------------------------------------------------- */
	// 属性(字段区)
	// 属性名称: coefficients
	// 属性特征: array of double
	private double[] coefficients;
	private int[] exponents;

    /* ---------------------------------------------------------------------------------------- */
    /*                                Constructors Area                                         */
    /* ---------------------------------------------------------------------------------------- */
	
	// 构造函数(constructor):初始化类
	public Polynomial() {
		// 初始化类的属性coefficients为[0](题目要求)
	    // set the Polynomial to default value
	    this.coefficients = new double[]{0};
	    this.exponents = new int[]{0};
	}

	public Polynomial(double[] coefficients_input, int[] exponents_input) {
		// 将属性状态设置为获取的值
		// set the Polynomial to the value given
        // 检查输入值异常
	    if (coefficients_input == null || exponents_input == null) {
	        System.out.println("Warning: Null input.");
	        return;
	    } else if (coefficients.length == 0) {
	        System.out.println("Warning: Input length is 0.");
	        return;
	    } else if (coefficients_input.length != exponents_input.length) {
	        System.out.println("Warning: Uneven input lengths.");
	        return;
	    } else {
	    	// 正常初始化
	    	this.coefficients = coefficients_input;
            this.exponents = exponents_input;
        }
	}


	// 基于文件的构造函数
    public Polynomial(File file) throws FileNotFoundException {
        // 检查文件是否存在且可读
        if (file.exists() && file.canRead()) {
            Scanner scanner = new Scanner(file);  // 创建Scanner类对象读取文件
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();  // 读取文件中的第一行
                getFilePolynomial(line);             // 调用helper method处理多项式
            }
            scanner.close();
        } else {
            // 文件不存在或不可读，直接return
            System.out.println("Warning: File not found or not readable.");
            return;
        }
    }

    /* -------------------------------------------------------------------------------- */
    /*                                方法区 Methods Area                                */
    /* -------------------------------------------------------------------------------- */
 
	// 方法add()
	public Polynomial add(Polynomial other) {
	    // 暴力法限制多项式的最大项数
	    int maxLength = this.coefficients.length + other.coefficients.length;
	    double[] resultCoefficients = new double[maxLength];
	    int[] resultExponents = new int[maxLength];
	    int i = 0; // 追踪结果数组的位置
	    int j = 0; // 遍历 this 多项式的指针
	    int k = 0; // 遍历 other 多项式的指针

	    // 遍历两个多项式并按升幂顺序合并
	    while (j < this.exponents.length && k < other.exponents.length) {
	        if (this.exponents[j] < other.exponents[k]) {
	            // 如果 this 的指数小于 other 的当前指数，加入 this 的项
	            resultCoefficients[i] = this.coefficients[j];
	            resultExponents[i] = this.exponents[j];
	            j++;
	        } else if (this.exponents[j] > other.exponents[k]) {
	            // 如果 other 的指数小于 this 的当前指数，加入 other 的项
	            resultCoefficients[i] = other.coefficients[k];
	            resultExponents[i] = other.exponents[k];
	            k++;
	        } else {
	            // 如果指数相同，合并系数
	            double sum = this.coefficients[j] + other.coefficients[k];
	            if (sum != 0) {
	                resultCoefficients[i] = sum;
	                resultExponents[i] = this.exponents[j]; // 或 other.exponents[k]
	            }
	            j++;
	            k++;
	        }
	        i++; // 每次循环结束后推进结果数组的位置
	    }

		// 处理剩余的项（如果操作对象已经遍历完成，另一个多项式还有多余的更高次幂）
	    // 检查另一个多项式是否遍历完成
	    while (k < other.exponents.length) {
	        resultCoefficients[i] = other.coefficients[k];
	        resultExponents[i] = other.exponents[k];
	        k++;
	        i++;
	    }

	    // 截取数组，过滤掉不需要的长度
	    double[] finalCoefficients = new double[i];
	    int[] finalExponents = new int[i];
	    // 使用简单循环将数据复制到最终数组中
	    for (int m = 0; m < i; m++) {
	        finalCoefficients[m] = resultCoefficients[m];
	        finalExponents[m] = resultExponents[m];
	    }

		Polynomial finalPolynomial = new Polynomial(finalCoefficients, finalExponents);
		return finalPolynomial;
	}


	// 方法evaluate()
	public double evaluate(double x) {
	    double result = 0.0; // 初始化结果为 0
	    // 遍历所有系数和指数
	    for (int i = 0; i < coefficients.length; i++) {
	        // 计算每一项的值：系数 * (x 的指数次方)
	        result += coefficients[i] * Math.pow(x, exponents[i]);
	    }
	    return result;
	}


	// 方法hasRoot()
	public boolean hasRoot(double x) {
		// 确定x是否为多项式的解
		return this.evaluate(x) == 0;
	}


	// 方法 multiply()
	public Polynomial multiply(Polynomial other) {
	    // 初始化结果多项式为 0（使用默认构造函数）
	    Polynomial result = new Polynomial();

	    // 遍历 this 多项式的每一项
	    for (int i = 0; i < this.coefficients.length; i++) {
	        // 创建临时数组存储 this 的当前项与 other 多项式所有项的乘积
	        double[] tempCoefficients = new double[other.coefficients.length];
	        int[] tempExponents = new int[other.exponents.length];

	        // 遍历 other 多项式的每一项
	        for (int j = 0; j < other.coefficients.length; j++) {
	            tempCoefficients[j] = this.coefficients[i] * other.coefficients[j]; //乘积的系数
	            tempExponents[j] = this.exponents[i] + other.exponents[j]; //乘积的指数
	        }

	        // 创建一个新多项式，表示当前乘积的结果
	        Polynomial tempPolynomial = new Polynomial(tempCoefficients, tempExponents);

	        // 将当前乘积结果与累积的结果相加
	        result = result.add(tempPolynomial);
	    }
	    return result;
	}


    // saveToFile() 方法 Polynomial -> File method
    public void saveToFile(String fileName) {
    	    StringBuilder polynomialBuild = new StringBuilder();  // 创建 StringBuilder 对象

    	    // 构建polynomialBuild串    	    
    	    for (int i = 0; i < coefficients.length; i++) {
    	    	// 系数部分 coefficients
    	        if (coefficients[i] == 0) {		// 判定系数是否 = 0
    	            continue;					// 系数是0，跳过余下的命令，继续遍历coefficients
    	        }
    	        // 为正数添加正号
    	        if (i > 0 && 						// 判定为非coefficients[0](第一项)，因为第一项即使是正数页不需要额外的正号
    	        		coefficients[i] > 0) {		// 判定coefficients[i] > 0, 是正数
    	            polynomialBuild.append("+");  	// 加正号"+"
    	        }
   
    	        polynomialBuild.append(coefficients[i]);  // 在"+"后面(如果有)，添加当前指向的系数 (eg. -3, 0r 5)

    	        
    	        // 指数部分 exponents
    	        if (exponents[i] != 0) {
    	            polynomialBuild.append("x").append(exponents[i]);  // 当指数 != 0, 添加 "x" 和当前非零指数 到polynomialBuild (eg. x2 <- 添加了 "x"和指数 "2")
    	            													// 指数 = 0 时，代表当前项是常数项，polynomialBuild无需添加 "x" 和 指数
    	        }
    	    }

    	    // 将拼接好的多项式写入文件
    	    try (FileWriter writer = new FileWriter(fileName)) {		// 创建一个以 fileName 为地址的 FileWriter类 的 writer对象
    	        writer.write(polynomialBuild.toString()); 				// 将 polynomialBuild 从StringBuilder类 转化为 String类，使用FileWriter类的.write()写入 fileName
    	        System.out.println("Polynomial saved to file: " + fileName);
    	    } catch (IOException e) {
    	        System.out.println("Error writing to file: " + e.getMessage());
    	    }
    	}
    /* ------------------------------------------------------------------------------------------ */
    /*                                辅助方法区 Helper Methods Area                                */
    /* ------------------------------------------------------------------------------------------ */

    // 辅助方法：解析多项式字符串
    private void getFilePolynomial(String polynomial) {
        // 示例：解析字符串 "5-3x2+7x8"
        String[] terms = polynomial.split("(?=[+-])");  // terms -> [5, -3x2, +7x8]
        // 暴力法创建数组，设置长度
        coefficients = new double[terms.length];		// terms.length = 3
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {  	// 追踪terms数组当前的指针
            String term = terms[i]; 				// 当前指针指向的string
            if (term.contains("x")) {				// 没有x的常数项排除
                String[] parts = term.split("x");	// 第一个有x的term = -3x2， parts -> [-3, 2] ** split("x")会把分割用的x关键字去除 **
                									// parts[0] 代表系数， parts[1]代表指数
                coefficients[i] = parts[0].isEmpty() || parts[0].equals("+") ? 1 :		// 如果当前指针的符号仅为"+"，代表忽略掉的系数=1，赋值1
                        								parts[0].equals("-") ? -1 :		// 如果当前指针的符号仅为"-"，代表忽略掉的系数=-1，赋值-1
                        								Double.parseDouble(parts[0]); 	// 把当前指针的值赋值给coefficients[i]但先转化Sting -> Double
                exponents[i] = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;		// 当前指针的长度>1才，代表有指数，Intger.parseInt(parts[1])赋值给指数数组
                																		// 如果没有指数，即指数=1，赋值1
            } else {	// term[0] -> 5, parts = 5.split("x") -> 没有x的情况
                coefficients[i] = Double.parseDouble(term);		// 用Java标准库Double.parseDouble()转换数字为双精度浮点类进行coefficients赋值
                exponents[i] = 0;								// 指数为0
            }
        }
    }

}

