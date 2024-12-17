public class Cell {
    private String value;
    private Object computedValue;
    private boolean isDirty;

    public synchronized void setValue(String value) {
        this.value = value;
        this.isDirty = true;
    }

    public synchronized String getValue() {
        return value;
    }

    public synchronized Object getComputedValue() {
        return computedValue;
    }

    public synchronized void setComputedValue(Object computedValue) {
        this.computedValue = computedValue;
        this.isDirty = false;
    }

    public synchronized boolean isDirty(){
        return isDirty;
    }


}
