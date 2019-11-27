
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import padTree.PriorityQueue;
import padList.LinkedList;
import padTree.HuffmanToken;
import padTree.HuffmanTree;
import padIo.*;

	public class Compress {
		public Compress(){}	
		/**
		 * HuffmanTree method, creates a huffman tree out of a given LinkedList filled with HuffmanTokens
		 * Creates a Priority Queue, pushes all the Tokens inside creating at the same time a tree for every each of them, 
		 * then takes the two smallest object every time, 
		 * binds them with a new one and put this tree once more inside the Priority Queue. 
		 * @param firstLowest the first object at the top of the Priority Queue picked out.
		 * @param secondLowest the second object at the top of the Priority Queue picked out.
		 * @param obj given LinkedList filled with HuffmanTokens
		 * @return the tree inside the list 
		 */
		public HuffmanTree createHuffmanTree(LinkedList<HuffmanToken> obj){
			obj.reset();
			PriorityQueue<HuffmanTree> PQ= new PriorityQueue<HuffmanTree>(obj.getSize());
			while(obj.isAtEnd()!=true){	
				PQ.push(new HuffmanTree(obj.currentData()));
				obj.increment();				
			}
			PQ.createHeap();		
			while (PQ.size()>1){
				HuffmanTree firstLowest= (HuffmanTree) PQ.pop();
				HuffmanTree secondLowest= (HuffmanTree) PQ.pop();
				PQ.push(new HuffmanTree(firstLowest,secondLowest));				
			}		
			return (HuffmanTree)PQ.top();			
		}		
		/**creates a List with all the characters of the file and their frequency
		 *@param in the file
		 *@param ch the character to be inserted in the list 
		 *@return list 
		 * 
		 */
		public LinkedList<HuffmanToken> readData(FileInputStream in) throws IOException {
			byte ch;
			LinkedList<HuffmanToken> list=new LinkedList<HuffmanToken>();
			// read characters until end of file
			while ((ch = (byte) in.read()) != -1) {
				if (list.exist(ch)==1){
					list.currentData().incrementValue() ;
				}
				else {
					list.insert(new HuffmanToken(ch,1,new LinkedList()));
				}
			}
			return list;
		 }
		/**
		 * calculates logarithm
		 * @param a a given number
		 * @param b the base of logarithm
		 * @return the logarithm of b with base a
		 */
		public static double logb( double a, double b ){
			return Math.log(a) / Math.log(b);
		}
		/**
		 * calculates logarithm
		 * @param a a given number
		 * @return the logarithm of a with base 2
		 */
		public static double log2( double a ){
			return logb(a,2);
		}
		public double huffmanRedundancy(LinkedList<HuffmanToken> list){
			double sumFrequencies=0,wordLength=0,entropie=0;
			list.reset();
			while(!list.isAtEnd()){
				sumFrequencies=sumFrequencies+list.currentData().doubleValue();
				list.increment();
			}
			list.reset();
			while(!list.isAtEnd()){
				wordLength=wordLength+(list.currentData().doubleValue()/sumFrequencies)*list.currentData().getBitcode().getSize();
				list.increment();
			}
			list.reset();
			while(!list.isAtEnd()){
				entropie=entropie+(list.currentData().doubleValue()/sumFrequencies)*log2(list.currentData().doubleValue()/sumFrequencies);
				list.increment();
			}
			return wordLength-entropie;
		}
		
		/**
		 * main method 
		 * takes a given file and opens two files of type FileInputStream, one to count the frequency and the other to encode the file.
		 * At the start it creates a list with all the characters inside the given file,gives it to the compress method and finally encodes its leaf of it.
		 * Since the leaves of the tree points at every element of the list, the objects that are encoded are the one that is inside the List. 
		 * Afterwards through the first while it runs the LinkedList to print the code table and at the second while it prints the original file in form of
		 * binary Huffman code. Between the code table and the binary encoded text this method prints "666" which is a pointer where the table ends.
		 * @param filename the file to be compressed
		 * @param name the name of the compressed file
		 * @param file,file2 the same file one to count the frequency and the other to encode the file
		 * @param Freq LinkedList with the characters, their frequency and their code
		 * @param tree the huffman tree
		 * 
		 */
		public static void main(String[] args){
			String filename = args[0];
			String name=args[1];
			File f = new File(name);
			if (args.length!=2){
				System.out.println("Two arguments exspected: the path of the file to be compressed and the name of the new file ");
			}
			else if(f.exists()){ 
				System.out.println("The name: "+name+" is already used by an another file\nPlease choose a differente one");
				}
			else{				
				try {
					//first file to count frequency 
					FileInputStream file=new FileInputStream(filename);
					//second file to encode the text 
					FileInputStream file2=new FileInputStream(filename);
					Compress compress=new Compress();					
					try {						
						LinkedList<HuffmanToken> Freq=	compress.readData(file);						
						HuffmanTree tree=compress.createHuffmanTree(Freq);
						tree.encode();
						Freq.reset();
						System.out.println("The Redundancy of the Huffman code is: "+compress.huffmanRedundancy(Freq));
						BitOutputFile bout = new BitOutputFile(name);  
						PrintWriter aout = new PrintWriter(new FileOutputStream(name + ".ascout"));
						/***BEGINN PRINTING OF THE CODE TABLE***********************************************/					
						Freq.reset();
						while (!Freq.isAtEnd()){
							Freq.currentData().getBitcode().reset();
							//prints the character on the code table
							if(Freq.currentData().getLetter()>=0){
								aout.print("The letter: "+Freq.currentData().getLetter()+" with Frequency: "+Freq.currentData().doubleValue()+" has code: ");
								bout.writeInt(Freq.currentData().getLetter());
							}
							else{
								aout.print("The letter: "+((Freq.currentData().getLetter()+128)+127)+" with Frequency: "+Freq.currentData().doubleValue()+" has code: ");
								bout.writeInt(((Freq.currentData().getLetter()+128)+127));
							}
							bout.beginBitMode();
							//starts printing the code of the character on the code table
							while(!Freq.currentData().getBitcode().isAtEnd()){
								aout.print(Freq.currentData().getBitcode().currentData()? "0" : "1");
								bout.writeBit(Freq.currentData().getBitcode().currentData());
								Freq.currentData().getBitcode().increment();						    
							}
							aout.println();
							Freq.increment();
							bout.endBitMode();
						}
						aout.println(666);
						bout.writeInt(666);
						/***END PRINTING OF THE CODE TABLE***********************************************/
						/***BEGINN PRINTING ENCODED TEXT***********************************************/
						bout.beginBitMode();
						byte letter;
						//starts reading for second time in order to encode the text
						while ((letter=(byte) file2.read())!= -1) {
							Freq.reset();
							//finds the character in the list
							while (!Freq.isAtEnd()){
								if (Freq.currentData().getLetter()==letter) break;
									Freq.increment();								
								}
							Freq.currentData().getBitcode().reset();
							//starts printing the code of the character
							while(!Freq.currentData().getBitcode().isAtEnd()){
								aout.print(Freq.currentData().getBitcode().currentData()? "0" : "1");
								bout.writeBit(Freq.currentData().getBitcode().currentData());
								Freq.currentData().getBitcode().increment();						    
							}
						}
					bout.endBitMode();
					aout.close();
					bout.close();
					/***END PRINTING ENCODED TEXT***********************************************/
					File decodedFile = new File(name);
					File originalFile = new File(filename);
					System.out.println("The storage space in prozent saved through huffman code is: "+Math.round((((double)originalFile.length()-(double)decodedFile.length())/originalFile.length())*100)+"%");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		}
	}


