package padTree;

import padInterfaces.Valued;
import padList.LinkedList;

public class HuffmanToken implements Valued {
	
		byte Letter;
		int Frequency;
		LinkedList<Boolean> Bitcode;		
		
		/**
		 * default constructor
		 */
		public HuffmanToken(){
			Letter = 0;
			Frequency = 0;
			Bitcode = null;
		}
		
		/**
		 * constructor
		 * 
		 * @param frequency in double
		 */
		public HuffmanToken(double value){
			Frequency = (int)value;
			Letter = 0;
			Bitcode = null;
		}
		/**
		 * constructor
		 * 
		 * @param frequency in double
		 */
		public HuffmanToken(double value,LinkedList<Boolean> newBitcode){
			Frequency = (int)value;
			Letter = 0;
			Bitcode = newBitcode;
		}
		/**
		 * constructor
		 * 
		 * @param character,frequency in int,LinkedList
		 */
		public HuffmanToken(byte newLetter , int newValue, LinkedList<Boolean> newBitcode){
			Letter = newLetter;
			Frequency = newValue;
			Bitcode = newBitcode;
		}		
		/**
		 * constructor
		 * 
		 * @param character,frequency in int
		 */
		public HuffmanToken(char newLetter, int newValue){
			Letter = (byte)newLetter;
			Frequency = newValue;
			Bitcode = null;
		}
		public HuffmanToken(int newLetter, int newValue){
			Letter = (byte)newLetter;
			Frequency = newValue;
			Bitcode = null;
		}
		public HuffmanToken(int newLetter, LinkedList<Boolean> code){
			Letter = (byte)newLetter;
			Frequency = 0;
			Bitcode = code;
		}
		/**
		 * @return frequency of character
		 */
		public double doubleValue(){
			return Frequency;
		}
		/**
		 * increments the value of the frequency
		 */
		public void incrementValue(){
			this.Frequency++;
		}
		/**
		 * @return character
		 */
		public byte getLetter(){
			return Letter;
		}
		/**
		 * @return Bitcode
		 */
		public LinkedList<Boolean> getBitcode(){
			return Bitcode;
		}
		/**
		 * @return token in string
		 */
		public String toString(){
			return ("Letter " + Letter + ",Frequency: " + Frequency + ",Code: " + Bitcode );
		}
		}



