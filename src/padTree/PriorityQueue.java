package padTree;

import padList.LinkedList;
import padTree.BinTree.BinTreeNode;

import java.util.NoSuchElementException;
import java.util.Random;



/**
 * priority queue using an array
 *
 * @author Martin Oellrich
*/

public class PriorityQueue<T> {

    /**
     * code for "no valid index"
     */
    private static final int UNUSED = -1 ;

    /**
     * flag for debugging
     */
    private static boolean _checkMode = false ;

    /**
     * heap as array, valid indices: 0 ... n-1
     */
    private Comparable<T>[] _objects ;

    /**
     * index of last element in array
     */
    private int _lastIndex;

    /**************  Constructors **************************/
    /**
	 *constructor
	 *@param int n is the size of the inner matrix of the Priority Queue
	 *@param _lastIndex index of last element in array 
	 *which shows that the array is initialized but not filled
	 */
    public PriorityQueue(int n){
    	_lastIndex=-1;
    	this._objects=new Comparable[n];
    }
    /**
   	 *constructor
   	 *@param other a given array with Comparable<T> objects that is being copied in the Priority Queue
   	 *@param _lastIndex index of last element in array  
   	 *which here shows that the array is filled
   	 */
    public PriorityQueue(Comparable<T>[] other ){
    	_lastIndex=other.length-1;   	
    	_objects=other.clone();
    }
   
    /**************  Methods  **************************/
    /**
    * turns array into a heap
    */
    public void createHeap() {
    	for (int i = _lastIndex; i >= 0; i--) {
    		_heapify(i);
    	}    	
    }
    /**
     * recursive helper method for createHeap method. 
     * In every method's call the lowest node are considered to be already heapified.
     * @param index the index of the cell given
     * @param currObj a variable that holds the value of the given cell
     * @param left a variable that holds the value of the left child of currObj
     * @param right a variable that holds the value of the right child of currObj
     * @param min a variable that holds the value of the smallest child of the given node in order to be moved upwards on the tree
     */
    public void _heapify(int index){
    	Comparable<T> currObj=this._objects[index];
    	int min;
    	while(true){
    		int left=_left(index);
    		int right=_right(index);
    		if (left==UNUSED)break;
    		if (right == UNUSED){
    			if(this._objects[left].compareTo((T)currObj)<0){
    				_objects[index]=_objects[left];	
    				index=left;
    				}
    			break;
    		}
   			if (_objects[left].compareTo((T)_objects[right])<0) min=left;
    		else min=right;
    		if (_objects[min].compareTo((T) currObj)>0 ){ break;}
    		_objects[index]=_objects[min];
    		index=min;
    	}
    	_objects[index]=currObj;
    }    
    /**
	 * method to find the parent of the given node
	 * @param the index of the given cell
	 * @returns the index of the parent cell
	 */
    public int _parent(int i){    	    	
    	if ((i-1)%2==0 && i<=_lastIndex){
    		return (i-1)/2;
    	}
    	else if ((i-2)%2==0 && i<=_lastIndex){
    		return (i-2)/2;
    	}
    	else return UNUSED;	
    }
    /**
	 * method to find the left child of the given node
	 * @param the index of the given cell
	 * @returns the index of the left child
	 */
    public int _left(int i){
    	return ((2*i+1)>_lastIndex?UNUSED:(2*i+1));    	
    }
    /**
	 * method to find the right child of the given node
	 * @param the index of the given cell
	 * @returns the index of the right child
	 */
    public int _right(int i){
    	return ((2*i+2)>_lastIndex?UNUSED:(2*i+2)) ;    	
    }
    /**
	 * method to get the object at the top of the heap
	 * @returns the smallest object of the heap
	 */
    public Comparable<T> top() throws NoSuchElementException{
    	if (isEmpty()==true) throw new NoSuchElementException ("There is no such element");
    	else return this._objects[0];
    }
    /**
	 * method to get the object at the top of the heap. 
	 * The method resolves this object and maintain the heap characteristics
	 * @param newObj a variable that hold the object given by top
	 * @returns the smallest object of the heap 
	 */
    public Comparable<T> pop() {
    	Comparable newObj=top();
    	_objects[0]=_objects[_lastIndex];
    	--_lastIndex;
    	if (_lastIndex>0) _heapify(0);    	
    	return newObj;
    } 
    /**
	 * method to insert a new object in the heap. 
	 * The method checks if there is enough space or not and act respectively
	 * @param newObj the new given object
	 * @param index variable that holds the value of _lastIndex before the while loop and then the index of the parent
	 */
    public void push(T newObj) {
    	int index;
    	if(_lastIndex== this._objects.length-1){ System.out.println("Priority Queue is full"); return;}
    	++_lastIndex;
    	index=_lastIndex;
    	int parent=_parent(_lastIndex);    	
    	while ((parent!=UNUSED) && (_objects[parent].compareTo((T) newObj)>0)){
    		_objects[index]=_objects[parent];
    		index=parent;
    		parent=_parent(parent);
    	}
    	_objects[index]=(Comparable<T>) newObj;    	
    }
    /**
	 * method to get the number of the current nodes in the heap 
	 * @param counter a variable that hold the number of the objects
	 * @returns the amount of the nodes
	 */
    public int size(){
    	int counter=0;
    	for(int i=0;i<=_lastIndex;i++){
    		if (this._objects[i]!=null) ++counter;
    	}
    	return counter;
    }
    /**
	 * checks if the given Priority Queue is empty		
	 *@return true or false  
	 */
    public boolean isEmpty(){
    	if (_lastIndex>0 || size()!=0){
    		return false;
    	}
    	else return true;
    }
    /**
	 * turns Priority Queue into a String		
	 *@return the string representation of the Priority Queue  
	 */
    public String toString(){
    	String string="";
    	helper(0);
    	return string;
    }    
    /**
	 * helper method for Priority Queue into a String  
	 */
    public void helper(int i){
    	if(_left(i)>0){
    		System.out.print("the cell ["+i+"] with value: "+_objects[i]+" has left child: "+_objects[_left(i)]);
    		if(_right(i)>0){
        		System.out.println(" and right child: "+_objects[_right(i)]);
        	}
    		System.out.println();
    		helper(_left(i));
        	helper(_right(i));
    	}
    	if (_left(i)<0) return;
    }
    
    /*************  debugging methods  ******************************************/
    /**
     * switch debugging mode
     */
    public static void setCheck ( boolean mode )
    {
	_checkMode = mode ;
    }

    /**
      * check consistency of heap property in entire tree
      *
      * @return is heap property consistent in queue?
      */
    public boolean _checkHeap ( )
    {
	boolean retval = true ;

	if ( _checkMode )                                    // checking wanted
	    for ( int i = _lastIndex ;  i > 0 ;  --i )
	    {
		Comparable<T> obj = _objects[ i ] ;
		Comparable<T> par = _objects[ _parent( i ) ] ;

	        if ( obj.compareTo((T)par) < 0 )
	        {
		    System.err.println(   "PriorityQueue: Inkonsistenz "
				        + obj + " < " + par
				        + " bei Index " + i + " gefunden.") ;
		    retval = false ;
		}
	    }   // for ( i )

	return retval ;

    }  // _checkHeap()
    
    
    /**************  Main Method **************************/
    public static void main(String[] args){  
    	
    	if (args.length==0){ 
    			System.out.println("There is no arguments given.Expected: Length of an array. This array is to be sorted through Heap sorting ");
    	}
    	else{
    		int n= Integer.parseInt(args[0]);
    		//--------------test for integers----------------------------------------
//        	PriorityQueue<Integer> newPQ=new PriorityQueue<Integer>(n);
//        	for (int i=0;i<n;i++){
//    			Random randomGenerator=new Random();
//    			newPQ.push(randomGenerator.nextInt(50));
//    		}
//        	for (int i=0;i<n;i++){
//        		System.out.println(newPQ._objects[i]);
//        	}
//        	System.out.println("---------------------------------------------------");
//        	newPQ.push(15);
//        	System.out.println(newPQ);
//        	newPQ.createHeap();
//        	System.out.println(newPQ);
    		//---------test for trees with HuffmanTree values-------------------------//
//    		Comparable<HuffmanTree>[] array=new Comparable[n]; 
    		PriorityQueue<HuffmanTree> newPQ=new PriorityQueue<HuffmanTree>(n);
//    		System.out.println("Hey");
    		for (int i=0;i<n;i++){
    			Random randomGenerator=new Random();
    			newPQ.push(new HuffmanTree(new HuffmanToken(randomGenerator.nextInt(100))));
    		}
    		System.out.println(); 
//    		PriorityQueue<HuffmanTree> newPQ=new PriorityQueue<HuffmanTree>(array);
    		
    		System.out.println(newPQ);    		
    		System.out.println();
        	newPQ.createHeap();
        	System.out.print(newPQ);
        	//---------end of tests ----------------------------------------------------------
//        	System.out.println();
//        	System.out.println("The smallest object is cell with value : "+ newPQ.pop());
//        	System.out.println(newPQ);
//        	System.out.println("The smallest object is cell with value : "+ newPQ.pop());
//        	System.out.println(newPQ);
//        	Random randomGenerator=new Random();        	
////        	newPQ.push(new HuffmanTree(new HuffmanToken(randomGenerator.nextInt(100))));
//        	System.out.println(newPQ);
    	}   	
    }
}  // class PriorityQueue








