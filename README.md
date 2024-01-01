# Text Compression and Decompression in Clojure

## Overview

This Clojure application allows users to compress and decompress simple text files using a basic word frequency-based compression algorithm. The program provides a menu with options to display the list of files, display file contents, compress a file, and decompress a previously compressed file.

## Features

### 1. Display List of Files (Option 1)
- Displays a list of files in the current folder.
- Utilizes Clojure's `file-seq` function for easy directory listing.

### 2. Display File Contents (Option 2)
- Prompts the user for a file name and displays its content.
- Uses Clojure's `slurp` function for reading file contents.

### 3. Compress a File (Option 3)
- Compresses the text file by converting words into numbers based on word frequency.
- Word frequencies are derived from a provided `frequency.txt` file containing the 10,000 most common English words.

### 4. Decompress a File (Option 4)
- Decompresses a previously compressed file, displaying the original text.
- Applies basic English text formatting rules during decompression.

## Usage

1. **Clone the repository:**

   ```bash
   git clone https://github.com/vikramsinghbrahm/clojure-compress-decompress.git
   cd your-repository
   ```
2. **Run the Clojure program:**

  ```bash
  clojure compress.clj
  ```
Follow the on-screen menu for different options.

### Example Input & Output
Input:
```bash
The experienced man, named Oz, representing the 416 area code - and his (principal) assistant - are not in the suggested @list [production, development]. Is that actually the correct information?
```
Output:
```bash
0 1686 374 , 402 Oz , 2280 0 @416@ 148 617 - 2 18 ( 2156 ) 4103 - 14 23 4 0 957 @ 734 [ 250 , 230 ] . 6 8 759 0 1524 295 ?
```

### Additional Notes
Punctuation symbols are included in their original form for proper English sentence reading.
Special cases like integers and symbols are handled with specific notations.
Decompression functions apply basic rules of English text formatting to restore readable text.
### License
This project is licensed under the MIT License.
