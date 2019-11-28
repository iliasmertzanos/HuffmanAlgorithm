# HuffmanAlgorithm

## Implementation of the Huffman algorithm for compressing and decompressing files

More information here:<br>
https://en.wikipedia.org/wiki/Huffman_coding

For german speakers in the task_description in src folder.<br>
And of course check source comments to understand what is going on.<br>
This java code was developed during my master degree studies within a project team. <br>

The main classes that perform the encoding and decoding of a file are :

- `Compress` in src folder(encoding)<br>
Two arguments expected from the main method:<br>
   - the path of the file to be compressed 
   - the name of the new file<br>
      It reads the given file that is parsed into the argument of the main method, in order to count the frequeny of each letter and to encode it. Then stores the frequencies in a `LinkedList<HuffmanToken>` and initilizes a Huffman tree using the constructor of the class  `HuffmanTree` (src/padtree).<br> Then it prints the table of the encoded characters and the encoded text in a file. The class `HuffmanToken` serves as a wrapper to save a letter, its frequency and the bitcode (01001) as an internal `LinkedList<Boolean>`. The bitcode of every token is being determined when the method `HuffmanTree.encode()` is invoked.
- `Decompress` in src folder(decoding)
    Has a main method that expects two arguments in the parameter `String[] args`:
    - the encoded file that was compressed from the class `Compress`
    - the name of the file that the class `Decompress` should create after decompressing the encoded file<br>
    It reconstructs the huffman tree following this steps:
      - First reads the table with the encoded characters, that class `Compress` has already stored and reconstructs the Huffman tree
      - Then using the tree it decodes every letter following the the step according to a letters code.<br>
      Example: for a given bit sequence 0100011 starting from top it goes left if bit=1 or right if bit=0 until it finds a leaf.<br>
      Once a leaf is found it prints it and starts again from the top.

