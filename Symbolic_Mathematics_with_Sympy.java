import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;

import java.math.BigDecimal;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) {
        // Number of program 1
        BigDecimal squareRoot2 = sqrt(new BigDecimal(2));
        System.out.println(squareRoot2);

        // Number of program 2
        Fraction half = Fraction.ONE_HALF;
        Fraction third = new Fraction(1, 3);
        Fraction sumResult = half.add(third);
        System.out.println(sumResult);

        // Number of program 3
        Symbol varX = new Symbol("x");
        Symbol varY = new Symbol("y");
        PolynomialExpression binomialExpr = varX.add(varY).pow(6);
        PolynomialExpression expandedExpr = binomialExpr.expand();
        System.out.println(expandedExpr);

        // Number of program 4
        Symbol varX = new Symbol("x");
        TrigonometricExpression trigExpr = TrigonometricExpression.sin(varX).divide(TrigonometricExpression.cos(varX));
        TrigonometricExpression simplifiedExpr = trigExpr.simplify();
        System.out.println(simplifiedExpr);

        // Number of program 5
        Symbol varX = new Symbol("x");
        Equation equation = (TrigonometricExpression.sin(varX).subtract(varX)).divide(varX.pow(3));
        Set<BigDecimal> solutionSet = equation.solve(varX);
        System.out.println(solutionSet);

        // Number of program 6
        Symbol varX = new Symbol("x");
        FunctionExpression logExpr = FunctionExpression.log(varX);
        FunctionExpression logDerivative = logExpr.derivative(varX);
        BigDecimal logDerivativeValue = logDerivative.value(varX);
        System.out.println("Derivative of log(x) with respect to x: " + logDerivative);
        System.out.println("Value of the derivative: " + logDerivativeValue);

        FunctionExpression inverseExpr = new FunctionExpression(BigDecimal.ONE).divide(varX);
        FunctionExpression inverseDerivative = inverseExpr.derivative(varX);
        BigDecimal inverseDerivativeValue = inverseDerivative.value(varX);
        System.out.println("Derivative of 1/x with respect to x: " + inverseDerivative);
        System.out.println("Value of the derivative: " + inverseDerivativeValue);

        TrigonometricExpression sinExpr = TrigonometricExpression.sin(varX);
        TrigonometricExpression sinDerivative = sinExpr.derivative(varX);
        BigDecimal sinDerivativeValue = sinDerivative.value(varX);
        System.out.println("Derivative of sin(x) with respect to x: " + sinDerivative);
        System.out.println("Value of the derivative: " + sinDerivativeValue);

        TrigonometricExpression cosExpr = TrigonometricExpression.cos(varX);
        TrigonometricExpression cosDerivative = cosExpr.derivative(varX);
        BigDecimal cosDerivativeValue = cosDerivative.value(varX);
        System.out.println("Derivative of cos(x) with respect to x: " + cosDerivative);
        System.out.println("Value of the derivative: " + cosDerivativeValue);

        // Number of program 7
        Symbol varX = new Symbol("x");
        Symbol varY = new Symbol("y");
        Equation equation1 = varX.add(varY).subtract(2).eq(BigDecimal.ZERO);
        Equation equation2 = varX.multiply(2).add(varY).eq(BigDecimal.ZERO);
        try {
            RealVector solutionVector = equation1.solve(varX, varY);
            BigDecimal xValue = solutionVector.getEntry(0);
            BigDecimal yValue = solutionVector.getEntry(1);
            System.out.println("x = " + xValue);
            System.out.println("y = " + yValue);
        } catch (SingularMatrixException e) {
            System.out.println("No unique solution exists for the system of equations.");
        }

        // Number of program 8
        Symbol varX = new Symbol("x");
        PolynomialExpression integratedExpr1 = varX.pow(2).integrate(varX);
        System.out.println("Integration of x^2: " + integratedExpr1);

        TrigonometricExpression integratedExpr2 = TrigonometricExpression.sin(varX).integrate(varX);
        System.out.println("Integration of sin(x): " + integratedExpr2);

        TrigonometricExpression integratedExpr3 = TrigonometricExpression.cos(varX).integrate(varX);
        System.out.println("Integration of cos(x): " + integratedExpr3);

        // Number of program 9
        Symbol varX = new Symbol("x");
        FunctionExpression functionF = new FunctionExpression("f").of(varX);
        Equation differentialEquation = functionF.derivative(varX, varX).add(new BigDecimal(9).multiply(functionF)).eq(BigDecimal.ONE);
        Solution solution = differentialEquation.solve(functionF);
        System.out.println(solution);

        // Number of program 10
        Symbol varX = new Symbol("x");
        Symbol varY = new Symbol("y");
        Symbol varZ = new Symbol("z");
        RealMatrix coefficientMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {3, 7, -12},
                {4, -2, -5}
        });
        RealVector constants = MatrixUtils.createRealVector(new double[]{0, 0});
        try {
            RealVector solutionVector = coefficientMatrix.solve(constants);
            BigDecimal xValue = solutionVector.getEntry(0);
            BigDecimal yValue = solutionVector.getEntry(1);
            BigDecimal zValue = solutionVector.getEntry(2);
            System.out.println("x = " + xValue);
            System.out.println("y = " + yValue);
            System.out.println("z = " + zValue);
        } catch (SingularMatrixException e) {
            System.out.println("No unique solution exists for the system of equations.");
        }
    }

    private static BigDecimal sqrt(BigDecimal value) {
        BigDecimal sqrtValue = BigDecimal.ZERO;
        BigDecimal x0 = new BigDecimal(Math.sqrt(value.doubleValue()));
        while (!sqrtValue.equals(x0)) {
            sqrtValue = x0;
            x0 = value.divide(x0, MathContext.DECIMAL128);
            x0 = x0.add(sqrtValue);
            x0 = x0.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
        }
        return sqrtValue;
    }
}
