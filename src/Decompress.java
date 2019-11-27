import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import padIo.BitInputFile;
import padTree.HuffmanToken;
import padTree.HuffmanTree;


public class Decompress {
	/**
	 * main method
	 * The method decode the encrypted file. First it reconstructs the Huffman tree through methods append() and advance().
	 * Afterwards the method uses this tree to decode the file. Every time free pointer is set on the root of the tree going left or right 
	 * according to the bits given in the file.
	 * @param args an array that stores the encoded file and the name of the decoded file
	 * @param INTEGER the value returned from method readInt()
	 * @param tree the new Huffmantree
	 */
	public static void main(String[] args){
		String filename = args[0];
		String name=args[1];
		File f = new File(name);
		if (args.length!=2){
			System.out.println("Two arguments exspected: the path of the file to be compressed and the name of the new file ");
		}
		else if(f.exists()){ System.out.println("The name: "+name+" is already used by an another file\nPlease choose a differente one");}
		else{
			try {
				//the file to be red
				BitInputFile bin = new BitInputFile(filename);
				//the file for the code table to be reprinted
				PrintWriter aout = new PrintWriter(new FileOutputStream(name + ".ascin"));
				//the file for the decoded text to be printed
				FileOutputStream bout=new FileOutputStream(name + ".txt");
				/***Reconstruction of Huffman tree***********************************************/
				int INTEGER;
				HuffmanTree tree=new HuffmanTree(new HuffmanToken(0));
				tree.freeToRoot();
				while((INTEGER = bin.readInt())!=666){
					aout.print("The letter: "+INTEGER+" has code: ");
					bin.beginBitMode();
					tree.freeToRoot();
					//print the table in aout file
					while(bin.bitsLeft() > 0){
						boolean bo = bin.readBit();
						if (bo==true){ tree.append(new HuffmanToken(INTEGER,0),2 );tree.advance(2);}
						else{tree.append(new HuffmanToken(INTEGER,0),3 );tree.advance(3);}
						aout.print(bo ? "0" : "1");
					}					
					aout.println();
					bin.endBitMode();			   
				}
				bin.beginBitMode();
				/***End of Reconstruction of Huffman tree***********************************************/
				/***Start decoding the encoded file***********************************************/
				tree.freeToRoot();
				//start decoding the encoded text in bout file
				while(bin.bitsLeft() > 0){
					boolean bo = bin.readBit();
					tree.advance(bo?2:3);
					if(tree.freeAtLeaf()){
						if (((HuffmanToken) tree.freeData()).getLetter()>0){
							bout.write(((HuffmanToken) tree.freeData()).getLetter());
							tree.freeToRoot();
						}
						else{
							bout.write((((HuffmanToken) tree.freeData()).getLetter()+128)+127);
							tree.freeToRoot();
						}
					}					
				}
				bin.endBitMode();
				aout.close();
				bin.close();
				bout.close();
				/***End decoding the encoded file***********************************************/

			}			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // For the binary data.
		}
	}
}
