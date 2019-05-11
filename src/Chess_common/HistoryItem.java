/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import Chess_pieces.Pawn;
import Chess_pieces.Piece;

/**
 * Class for History Item
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class HistoryItem {
    private Field from;
    private Field to;
    private Piece target_to;
    private Piece exchange;

    /**
     * Created instance of Item in History.
     * @param from from to move to
     * @param to where to mov to
     * @param target_to piece on TO field
     */
    public HistoryItem(Field from, Field to, Piece target_to) {
        this.from = from;
        this.to = to;
        this.target_to = target_to;
        this.exchange = null;
    }

    /**
     * Getter for from field in HistoryItem.
     * @return Field from.
     */
    public Field getFrom() {
        return from;
    }

    /**
     * Getter for to field in HistoryItem.
     * @return Field to.
     */
    public Field getTo() {
        return to;
    }

    /**
     * Getter for pointer on piece placed on field from.
     * @return pointer to piece.
     */
    public Piece getTarget_to() {
        return target_to;
    }

    /**
     * Setter for exchange.
     * @param piece Piece to exchange for
     */
    public void setExchange(Piece piece){
        this.exchange = piece;
    }

    /**
     * Getter for exchange.
     * @return Pointer on piece is being exchanged.
     */
    public Piece getExchange() {
        return exchange;
    }
}
