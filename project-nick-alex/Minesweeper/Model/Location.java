package Minesweeper.Model;
/**
 * basic implementation of Location class
 * @author Nick Kariotis
 * @author Alex Blondale 
 */
public class Location{
    /**
     * row number of location
     */
    private int row;
    /**
     * col number of location
     */
    private int col;
    
    /**
     * initializes the location class
     * @param row - the row number of the location
     * @param col - the column number of the location
     */
    public Location(int row, int col){
        this.row = row;
        this.col = col;
        
    }

    /**
     * returns the row of the location
     */
    public int getRow(){ return this.row;}
    /**
     * returns the row of the location
     */
    public int getCol(){ return this.col;}
    /**
     * equals funcion to check to see if 
     * two functions are equal
     * @param o - the object checked too
     */
    @Override
    public boolean equals(Object o){
        Location object = (Location) o;
        return this.row == object.getRow() && this.col == object.getCol();
    }
    /**
     * toStringfunction to return string
     * of loction
     */
    @Override
    public String toString(){
        return "["+this.row + ", " + this.col + "]";
    }
    /**
     * hash  fucntion for location
     */
    @Override
    public int hashCode(){
        return (int)(Math.ceil((double)1000000/(row+1) + 10000/(col+1)));
    }


}