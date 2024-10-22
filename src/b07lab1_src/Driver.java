package b07lab1_src;

import java.io.File;
import java.io.FileNotFoundException;

public class Driver {

	public static void main(String[] args) {
        // 1. 使用默认构造函数创建多项式 p
        Polynomial p = new Polynomial();
        System.out.println(
        	    "测试 evaluate() 中\n" + 
        	    "Expected output = 0.0\n" + 
        	    "p(3) = " + p.evaluate(3) + "\n" + 
        	    "The test result is: " + (p.evaluate(3) == 0)
        	);
        // 2. 使用数组构造函数创建两个多项式 p1 和 p2
        double[] c1 = {6, -2, 5};  // 系数数组
        int[] e1 = {0, 1, 3};      // 对应指数数组 (6 - 2x + 5x^3)
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-2, -9};   // 系数数组
        int[] e2 = {1, 4};        // 对应指数数组 (-2x - 9x^4)
        Polynomial p2 = new Polynomial(c2, e2);
 
        // 3. 测试 add() 方法
        Polynomial sum = p1.add(p2);
        System.out.println(
                "测试 add() 方法\n" +
                "Expected sum evaluated at 0.1 = 5.6041" + sum.evaluate(0.1) + "\n" +
                "The test result is: " + (sum.evaluate(0.1) == 5.6041)
            );

        // 4. 测试 hasRoot() 方法
        System.out.println(
                "测试 hasRoot() 方法\n" +
                "Checking if 1 is a root\n" +
                (sum.hasRoot(1) ? "1 is a root of the sum." : "1 is not a root of the sum.")
            );

        // 5. 测试 multiply() 方法
        Polynomial product = p1.multiply(p2);
        System.out.println(
            "测试 multiply() 方法\n" +
            "Product evaluated at 2 = " + product.evaluate(2)
        );

        // 6. 将 sum 多项式保存到文件
        String fileName = "polynomial.txt";
        sum.saveToFile(fileName);

        // 7. 使用文件构造函数读取保存的多项式
        try {
            Polynomial pFromFile = new Polynomial(new File(fileName));
            System.out.println("Polynomial from file evaluated at 1: " + pFromFile.evaluate(1));
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
	}

}