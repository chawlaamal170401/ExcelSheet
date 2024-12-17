import java.util.concurrent.ConcurrentHashMap;

public class Sheet {
    private final int rows;
    private final int columns;
    private final ConcurrentHashMap<String, Cell> cells;

    public Sheet(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = new ConcurrentHashMap<>();
        initializeCells();
    }

    private void initializeCells() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                String cellKey = getCellKey(i, j);
                cells.put(cellKey, new Cell());
            }
        }
    }

    public Cell getCell(int row, int column) {
        return cells.get(getCellKey(row, column));
    }

    public String getCellKey(int row, int column) {
        return row + "," + column;
    }

    public void setCell(int row, int column, String value){
        Cell cell = getCell(row, column);
        cell.setValue(value);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}
