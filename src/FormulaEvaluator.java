import javax.script.ScriptException;

public class FormulaEvaluator {

    public static Object evaluate(String formula, InMemoryExcel excel) throws ScriptException {
        if (formula == null || formula.trim().isEmpty()) {
            throw new IllegalArgumentException("Formula cannot be empty");
        }

        // Remove spaces for easier parsing
        formula = formula.replaceAll("\\s+", "");

        // Handling cell references like "0,0" or "0,1"
        if (formula.contains(",")) {
            // For example: "0,0 + 0,1" or "0,0"
            return evaluateCellReferenceFormula(formula, excel);
        }

        // Handle simple arithmetic operations
        if (formula.contains("+")) {
            return evaluateAddition(formula, excel);
        } else if (formula.contains("-")) {
            return evaluateSubtraction(formula, excel);
        } else if (formula.contains("*")) {
            return evaluateMultiplication(formula, excel);
        } else if (formula.contains("/")) {
            return evaluateDivision(formula, excel);
        } else {
            // If no operator, treat it as a number
            try {
                return Double.parseDouble(formula); // Assuming numeric values
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid formula: " + formula, e);
            }
        }
    }

    // Method to evaluate formulas with cell references (e.g., "0,0 + 0,1")
    private static Object evaluateCellReferenceFormula(String formula, InMemoryExcel excel) throws ScriptException {
        String[] parts = formula.split("\\+");

        double operand1 = getCellValue(parts[0], excel); // First operand
        double operand2 = getCellValue(parts[1], excel); // Second operand

        return operand1 + operand2;
    }

    // Method to extract the value of a cell from the formula like "0,0"
    private static double getCellValue(String reference, InMemoryExcel excel) throws ScriptException {
        String[] coordinates = reference.split(",");
        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);

        Object cellValue = excel.getCellComputedValue(row, col);
        if (cellValue instanceof Number) {
            return ((Number) cellValue).doubleValue();
        } else {
            throw new IllegalArgumentException("Invalid cell reference or value at " + reference);
        }
    }

    private static Object evaluateAddition(String formula, InMemoryExcel excel) {
        String[] parts = formula.split("\\+");
        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[1]);
        return operand1 + operand2;
    }

    private static Object evaluateSubtraction(String formula, InMemoryExcel excel) {
        String[] parts = formula.split("-");
        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[1]);
        return operand1 - operand2;
    }

    private static Object evaluateMultiplication(String formula, InMemoryExcel excel) {
        String[] parts = formula.split("\\*");
        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[1]);
        return operand1 * operand2;
    }

    private static Object evaluateDivision(String formula, InMemoryExcel excel) {
        String[] parts = formula.split("/");
        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[1]);

        if (operand2 == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }

        return operand1 / operand2;
    }
}
