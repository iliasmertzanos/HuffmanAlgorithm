package padTree;

	
	/**
	* abstract base class for all sorts of binary trees
	*
	*/
	abstract class BinTree<T> {
		
		/**
		 * class for tree nodes
		 */
		public class BinTreeNode<T> {
			T data; 
			BinTreeNode<T> lson;
			BinTreeNode<T> rson;
			BinTreeNode<T> parent;
	
			public BinTreeNode() {
				this.lson=null;
				this.rson=null;
				this.data=null;
			}			
			public BinTreeNode(T obj) {
				this.data=obj;
				this.lson=null;
				this.rson=null;
			}
			public BinTreeNode(Double obj) {
				this.data=(T)obj;
				this.lson=null;
				this.rson=null;
			}
			/**
			 * checks if the given node is a leaf			
			 *@return true or false 
			 * 
			 */
			public boolean isLeaf() {
				if (lson==null && rson==null) return true;
				else return false;
			}
			/**
			 * checks if the given node is a root			
			 *@return true or false 
			 * 
			 */
			public boolean isRoot() {
				if (this.parent==dummy) return true;
				else return false;
			}
			/**
			 * checks if the given node is a left child			
			 *@return true or false 
			 * 
			 */
			public boolean isLeftChild() {
				if (this.isRoot()) {
					return true;
				}
				else if (this.parent!=null && this.parent.lson==this){
					return true;
				}
				else return false;
			}
			/**
			 * get method to get the left child of the given node			
			 *@return the node that is the left child
			 * 
			 */
			public BinTreeNode<T> getLeftChild() {
				return this.lson;
			}
			/**
			 * get method to get the right child of the given node			
			 *@return the node that is the right child
			 * 
			 */
			public BinTreeNode<T> getRightChild() {
				return this.rson;
			}
			/**
			 * get method to get the parent of the given node			
			 *@return the node that is the parent
			 * 
			 */
			public BinTreeNode<T> getParent() {
				return this.parent;// get parent
			}
			/**
			 * get method to get the data variable of the given node			
			 *@return the variable data
			 * 
			 */
			public T getData(){
				return this.data;
			}
		} // class BinTreeNode
	/*** data ******************************************************/
		protected BinTreeNode<T> dummy; 
		protected BinTreeNode<T> curr;
		protected Origin origin;
		
	/*** constructors **********************************************/
		/**
		 * default constructor, initializes empty tree
		 *@param T obj is been given and through that is being created a tree with a single node
		 * 
		 */
		public BinTree() {
			this.dummy=new BinTreeNode<T>();
			this.dummy.lson=null;
			this.dummy.rson=null;					
		}
		/**
		 * constructor
		 *@param T obj is been given and through that is being created a tree with a single node
		 * 
		 */
		public BinTree(T obj){
			this.dummy=new BinTreeNode<T>();
			dummy.lson=new BinTreeNode<T>(obj);
			dummy.lson.parent=dummy;
		}
	
	/*** methods ***********************************************/
		/**
		 * checks if the given tree is empty		
		 *@return true or false  
		 */
		public boolean isEmpty() { 
			return dummy.getLeftChild()==null;
		}
		/**
		 * get method to get the root of the given tree			
		 *@return the left child of node dummy
		 */
		public BinTreeNode<T> getRoot() {
			return this.dummy.lson;
		}
		/**
		 * get method to get the current number of tree nodes			
		 *@return the current number of tree nodes
		 */
		public int getSize() {
			return(size(getRoot())); 
		}
		/**
		 * recursive help method for getSize			
		 *@return the current number of tree nodes
		 */
		private int size(BinTreeNode<T> node) { 
		  if (node == null) return(0); 
		  else { 
		    return(size(node.getLeftChild()) + 1 + size(node.getRightChild())); 
		  } 
		} 
		/**
		 * get method for the height of the tree			
		 *@return the distance from root to the leaf that is at the longest way 
		 *from top to this node
		 */
		public int getHeight() {
			return _height(getRoot());
		}
		/**
		 *recursive help method for getHeight			
		 *@return the distance from root to the leaf that is at the longest way 
		 *from top to this node
		 */
		public int _height(BinTreeNode<T> node){
			if (node==null) return -1;
			int height1=_height(node.getLeftChild());
			int height2=_height(node.getRightChild());
			return (height1>height2? height1:height2) +1;
		}

	/*** methods for current node **********************************/
		/**
		 * method that sets pointer curr at the most left node of the tree
		 * through while loop resets curr until there is no other left node
		 * @origin enum with three directions:Right,Left,Up
		 * it is provided in padtree package
		 */
		public void reset() {
			origin=Origin.Left;
			curr=getRoot();
			while(curr.getLeftChild()!=null){
				curr=curr.lson;
			}
		}
		/**
		 *checks if pointer curr is on dummy node		
		 *@return true or false
		 */
		public boolean isAtEnd() {
			return curr==dummy;
		}
		/**
		 * void method that move curr according to the LWR way of traversing a binary tree
		 */
		public void increment() {
			if (origin==Origin.Left) {//(1)
				if(curr.rson==null){
					origin=Origin.Right;
				}
				else{
					curr=curr.rson;
					origin=Origin.Above;
				}
			}
			else if (origin==Origin.Above) {//(2)
				if(curr.lson==null){
					origin=Origin.Left;									
				}
				else curr=curr.lson;
			}
			else if (origin==Origin.Right) {//(3)
				if (curr.isLeftChild()){					
					origin=Origin.Left;
				}
				else origin=Origin.Right;
				curr=curr.parent;
			}			
		}
		/**
		 * get method for the data of the node that is being pointed from curr 		
		 *@return the object referenced by current node
		 */		
		public T currentData() {
			return (T) curr;
		}	
		/**
		 * checks if pointer curr is on a leaf node		
		 *@return true or false
		 */
		public boolean isLeaf() {
			return curr.lson==null && curr.rson==null;
		}
	
	/*** conversion methods ****************************************/
		/**
		 * method that change the tree into a string 
		 * 
		 * @param node
		 * @param string
		 */
		public String toString() {
		    StringBuilder string = new StringBuilder("[");
		    helpToString(getRoot(), string);
		    string.append("]\n");
		    return string.toString();
		}

		/**
		 * Recursive help method for toString. 
		 * 
		 * @param node
		 * @param string
		 */
		private void helpToString(BinTreeNode<T> node, StringBuilder string) {
		    if (node == null)
		        return; // Tree is empty, so leave.
		    if (node.lson != null) {
		        helpToString(node.lson, string);
		        string.append(", "+"\n");
		    }
		    string.append(node.data);
		    if (node.rson != null) {
		        string.append(", "+"\n");
		        helpToString(node.rson, string);
		    }
		}
}
