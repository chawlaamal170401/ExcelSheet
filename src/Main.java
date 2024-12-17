import javax.script.ScriptException;

public class Main {
    public static void main(String[] args) throws ScriptException {
        InMemoryExcel excel = InMemoryExcel.getInstance(10, 10);

        excel.setCell(0, 0, "5");
        excel.setCell(0, 1, "6");
        excel.setCell(0, 2, "=0,0 + 0,1");

        System.out.println("Cell(0,0): " + excel.getCellComputedValue(0, 0)); // 5
        System.out.println("Cell(0,1): " + excel.getCellComputedValue(0, 1)); // 10
        System.out.println("Cell(0,2): " + excel.getCellComputedValue(0, 2)); // 15
    }
}