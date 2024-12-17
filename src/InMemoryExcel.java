import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryExcel {

    private static InMemoryExcel instance;
    private final Map<String, String> cells;
    private final int rows;
    private final int cols;

    // Private constructor for Singleton design pattern
    private InMemoryExcel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new HashMap<>();
    }

    // Singleton instance getter
    public static InMemoryExcel getInstance(int rows, int cols) {
        if (instance == null) {
            instance = new InMemoryExcel(rows, cols);
        }
        return instance;
    }

    // Set value in a cell
    public void setCell(int row, int col, String value) {
        String key = getCellKey(row, col);
        cells.put(key, value);
    }

    // Get value from a cell (or evaluate its formula if it is a formula)
    public Object getCellComputedValue(int row, int col) throws ScriptException {
        String key = getCellKey(row, col);
        String value = cells.get(key);

        // If the value is a formula (starts with '='), evaluate it
        if (value != null && value.startsWith("=")) {
            return FormulaEvaluator.evaluate(value.substring(1), this); // Strip the "=" and evaluate
        }

        // Return the raw value
        return parseValue(value);
    }

    // Helper method to parse raw values (supports numbers or strings)
    private Object parseValue(String value) {
        try {
            return Double.parseDouble(value);  // Try to parse as a number
        } catch (NumberFormatException e) {
            return value;  // Return as string if it's not a number
        }
    }

    // Get cell key in the form of "row,col"
    private String getCellKey(int row, int col) {
        return row + "," + col;
    }
}
