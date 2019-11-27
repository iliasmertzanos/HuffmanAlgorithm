package padTree;

import padList.LinkedList;
import padInterfaces.Valued;
import padTree.BinTree.BinTreeNode;

public class HuffmanTree extends BinTree<Valued> implements Comparable<HuffmanTree> {
	/**
	 * class for Huffman nodes 
	 */
	public class HuffmanTreeNode extends BinTreeNode<Valued>{
		/**
		 * default constructor, initializes empty node  
		 */
		public HuffmanTreeNode(){}
		
		public HuffmanTreeNode(Valued obj){
			this.data=obj;
			this.lson=null;
			this.rson=null;
			this.parent=null;
		}
		public HuffmanTreeNode(double obj){
			super(obj);
			this.lson=null;
			this.rson=null;
			this.parent=null;
		}
		public String toString(){
			return "Value of the node: "+this.data;
		}
		/**
		 * get method
		 * @return data of the node  
		 */
		public HuffmanTreeNode getNode(){
			return (HuffmanTreeNode) this.data;
		}
	}
	
	/*** data ******************************************************/
	private HuffmanTreeNode free;
	/**
	 * constants for the method advance and append
	 */
	final int UP=1,LEFT=2,RIGHT=3;
	
	/*** constructors **********************************************/
		/**
		 * default constructor, initializes empty tree  
		 */
		public HuffmanTree(Valued obj) {
			this.dummy=new HuffmanTreeNode();
			dummy.lson=new HuffmanTreeNode(obj);
			dummy.lson.parent=dummy;
		}		
		/**
		 *constructor, that takes two other trees and bind them together into a new tree.
		 * Their parent's value is the sum of their values. It also intialise the new node parent with an empty LinkedList
		 *   
		 */
		public HuffmanTree(HuffmanTree obj1,HuffmanTree obj2){
			this.dummy=new HuffmanTreeNode();
			this.dummy.lson=new HuffmanTreeNode(new HuffmanToken(obj1.getRoot().data.doubleValue() + obj2.getRoot().data.doubleValue(),new LinkedList()));
			this.dummy.lson.lson=obj1.getRoot();
			this.dummy.lson.rson=obj2.getRoot();
			this.dummy.lson.parent=dummy;
			obj1.getRoot().parent=dummy.lson;
			obj2.getRoot().parent=dummy.lson;
		}
	/*** methods*****************************************************/
	/**
	 * compare method for HuffmanTree objects. Compares objects according to their Frequency.
	 * Overrides compareTo() method of interface Comparable<T>
	 *@return 1 if the given object is bigger than the one inside the brackets,-1 if  smaller and if both equal 0
	 */	
	@Override
	public int compareTo(HuffmanTree arg0) {		
		if(this.getRoot().data.doubleValue()>arg0.getRoot().data.doubleValue()) {
			return 1;}
		else if (this.getRoot().data.doubleValue()<arg0.getRoot().data.doubleValue()){
			return -1;}
		else{
			return 0;}
	}
	/**
	 * checks if the given Huffman tree is consistent,if every parent has two kinds and has a value equal to the sum of it's kinds
	 * @return true or false 
	 */
	public boolean checkTree() {
		return isHFTree((HuffmanTreeNode) this.getRoot());
	}
	/**
	 * recursive helper method for checkTree
	 * if it finds a node that doesnt hold the rules prints it and  it's children
	 * @param node a given node
	 * @return
	 */
	private boolean isHFTree(HuffmanTreeNode node) {
    	boolean consistency=true;
        if (!node.isLeaf()){
        	if(node.rson==null){
        		System.out.println("Inconstistency on: "+node.data+",left child: "+node.lson.data);
        		consistency=false;
        	}
        	else if(node.lson==null){
        		System.out.println("Inconstistency on: "+node.data+",right child: "+node.rson.data);
        		consistency=false;
        	} 
        	else{
        	if(node.data.doubleValue()!=(node.lson.data.doubleValue()+node.rson.data.doubleValue())){
        		System.out.println("Inconstistency on: "+node.data+",left child: "+node.lson.data+",right child: "+node.rson.data);
        		consistency=false;}
        	 }
        }
        if (node.lson!=null){
        	isHFTree((HuffmanTreeNode) node.lson);}
        if(node.rson!=null){
        	isHFTree((HuffmanTreeNode) node.rson);}
        return consistency;
    }	
	/**
	 * method that is made to be used in compress procedure. It calls reset(),sets free on curr and walks up the tree to the root
	 * inserting every time true or false in LinkedList<Boolean>.
	 */
	public void encode(){
		this.reset();
		if (this.getSize()==1){
			((HuffmanToken) this.curr.data).getBitcode().insert(true);
		}
		else{
			while(!this.isAtEnd()){			
				if(this.isLeaf() && ((HuffmanToken) this.curr.getData()).getBitcode().isEmpty()){				
					this.freeToCurr();
					while(!this.freeAtRoot()){
						if(freeAtLeftChild()) ((HuffmanToken) this.curr.data).getBitcode().insert(true);
						else ((HuffmanToken) this.curr.data).getBitcode().insert(false);
						this.advance(UP);
					}
				}
				this.increment();
			}
		}
	}
	/*** methods for free*****************************************************/
	/**
	 * set free at the node that curr currently points
	 */
	public void freeToCurr(){
		free=(HuffmanTreeNode) curr;
	}
	/**
	 * set free at the root of the given tree
	 */
	public void freeToRoot(){
		free=(HuffmanTreeNode) getRoot();
	}
	/**
	 * checks if free is pointing at the root of the given tree
	 */
	public boolean freeAtRoot(){
		return free.isRoot();
	}
	/**
	 * checks if free is pointing at a leaf of the given tree
	 */
	public boolean freeAtLeaf(){
		return free.isLeaf();
	}
	/**
	 * checks if free is pointing at a left child
	 */
	public boolean freeAtLeftChild(){
		return free.isLeftChild();
	}
	/**
	 * get method
	 * @return the node that free is currently pointing
	 */
	public Valued freeData(){
		return free.data;
	}
	/**
	 * method, moves free according to the given direction.
	 * At the same time checks if it is possible or not and act respectively
	 * 
	 * @param direction the given direction
	 * @return true or false 
	 */
	public boolean advance(int direction){
		if (freeAtLeaf()){
			if (direction==UP) {
				free=(HuffmanTreeNode) free.parent;
				return true;
			}
			else return false;
		}			
		else if (freeAtRoot()){
			if (freeAtLeaf()){
				if(direction==UP){
					free=(HuffmanTreeNode) free.parent;
					return true;
				}
				else return false;				
			}
			else if (direction==UP){
				free=(HuffmanTreeNode) free.parent;
				return true;
			}
			else if(direction==LEFT){
				free=(HuffmanTreeNode) free.lson;
				return true;
			}
			else{ free=(HuffmanTreeNode) free.rson;
				return true;
			}			
		}
		
		else{
			if (direction==UP){
				free=(HuffmanTreeNode) free.parent;
				return true;
			}
			else if(direction==LEFT){
				free=(HuffmanTreeNode) free.lson;
				return true;
			}
			else{ 
				free=(HuffmanTreeNode) free.rson;
				return true;
			}				
		}			
	}
	/**
	 * method, creates a new node at the given direction.
	 * At the same time checks if it is possible or not and acts respectively
	 * 
	 * @param direction the given direction
	 * @param obj a given object
	 * @return true or false
	 */
	public boolean append(HuffmanToken obj,int direction){
		if (freeAtLeaf()==true){			
			if (direction==LEFT){
				free.lson=new HuffmanTreeNode(obj);
				free.lson.parent=free;
				return true;
			}
			else {
				free.rson=new HuffmanTreeNode(obj);
				free.rson.parent=free;
				return true;
			}
		}
		else  {
			if(direction==LEFT && free.lson==null) {
				free.lson=new HuffmanTreeNode(obj);
				free.lson.parent=free;
				return true;
			}
			else if(direction==RIGHT && free.rson==null){
				free.rson=new HuffmanTreeNode(obj);
				free.rson.parent=free;
				return true;
			}
			else return false;
		}
	}
	
	public static void main(String[] args){
		HuffmanTree tree1=new HuffmanTree(new HuffmanToken(15));
		HuffmanTree tree2=new HuffmanTree(new HuffmanToken(10));
		HuffmanTree tree3=new HuffmanTree(new HuffmanToken(17));
		HuffmanTree tree4=new HuffmanTree(new HuffmanToken(8));
		tree1.freeToRoot();
//		System.out.println(tree1.advance(2));
//		System.out.println(tree1.append(new HuffmanToken(10) ,tree1.LEFT));
//		System.out.println(tree1.append(new HuffmanToken(10) ,tree1.LEFT));

//		System.out.println(tree1);
//		for (int i=0;i<=5;i++){
//			tree1.freeToRoot();
		for (int j=0;j<=10;j++){
			int bo = (int)(Math.random() * 10) % 2 != 0 ? 0: 1;
			System.out.println("bo: "+bo);
			if (bo==0) {
				System.out.println("b0==0");
				tree1.append(new HuffmanToken(20),2);
				tree1.advance(2);
			}
			else{
				System.out.println("else");
				tree1.append(new HuffmanToken(21),3);
				tree1.advance(3);
			}
		}
//		}
//		tree1.checkTree();
		System.out.println(tree1);
//		System.out.println(tree1.dummy.lson.data);
//		HuffmanTree tree=new HuffmanTree(tree1,tree2);
////		tree.checkTree();
//		tree.encode();
//		System.out.println(tree);
//		tree.reset();
//		tree.freeToCurr();
//		System.out.println(tree.freeData());
//		System.out.println(tree.freeAtLeftChild());
//		tree.append(node1, 2);
//		tree.append(node4, 3);
//		tree.reset();
//		for(tree.reset();tree.increment();tree.isAtEnd())
//		while(tree.isAtEnd()==false){						
////			System.out.println(tree.isAtEnd());
//			System.out.println(tree.curr);
//			tree.increment();
//		}
	}
	
}
