/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for History
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class History {
    private List<HistoryItem> array;
    private int pointer;

    /**
     * Creates instance of History.
     */
    public History() {
        this.array = new ArrayList<>();
        this.pointer = 0;
    }

    /**
     * Adds item to History.
     * @param item Item being added.
     */
    public void add(HistoryItem item){
        int array_size = this.array.size();
        if (this.pointer <= array_size){
            clear();
        }
        this.array.add(item);
        this.pointer++;
    }

    /**
     * Cleans whole History.
     */
    private void clear(){
        this.array.subList(this.pointer, this.array.size()).clear();
    }

    /**
     * moves pointer in History back.
     * @return HistoryItem on current index.
     */
    public HistoryItem undo(){
        int array_size = this.array.size();
        if (!(array_size < 1)){
            return this.array.get(--this.pointer);
        }
        return null;
    }

    /**
     * moves pointer in History forward.
     * @return HistoryItem on current index.
     */
    public HistoryItem redo(){
        int array_size = this.array.size();
        if (this.pointer < array_size){
            return this.array.get(this.pointer++);
        }
        return null;
    }
}
