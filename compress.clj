(ns compress
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.string :as string]))

;; Function to read frequencies
(defn read-frequencies [filename]
  (-> filename
      io/file
      slurp
      (string/split #" ")))

;; Function for compressed filename
(defn compressed-filename [filename]
  (str filename ".ct"))
 
;; Function for processing punctuations 
(defn punctuation-processing [line]
  (clojure.string/replace line #"[(.,@!?;:)\[\]]" #(str " " % " ")))

;; Function to compress words using the frequency.txt file
(defn word-compression [word frequency-map output]
  (let [lower-case-word (string/lower-case word)
        index (get frequency-map lower-case-word)]
    (if index
      (swap! output #(str % index " "))
      (if (re-matches #"\d+" word)
        (swap! output #(str % " @" word "@ "))
        (swap! output #(str % word " "))))))

;; Function for compressing line
(defn line-compression [line frequency-map output]
  (let [modified-line (punctuation-processing line)
        words (string/split modified-line #" ")]
    (defn words-compression [words]
      (when (seq words)
        (word-compression (first words) frequency-map output)
        (recur (rest words))))
    (words-compression words)))

;; function for compressing file
(defn file-compression []
  (print "Please enter the name of the file you want to compress: ")
  (flush)
  (let [filename (read-line)
        compressed-filename (compressed-filename filename)]
    (if (.exists (io/file filename))
      (let [frequencies (read-frequencies "frequency.txt")
            output (atom "")
            frequency-set (-> frequencies distinct)
            frequency-map (zipmap frequency-set (range))
            file-contents (slurp filename)]
        (line-compression file-contents frequency-map output)
        (let [f-output (clojure.string/replace @output #"\s{2,}" " ")]
          (with-open [writer (io/writer compressed-filename)]
            (.write writer f-output)))
        (println))
      (println "Oops: specified file does not exist"))))

;; Function for decompressing words
(defn words-decompression [word frequency-vector]
  (if (re-matches #"\d+" word)
    (get frequency-vector (Integer/parseInt word) word)
    word))

;; Function for processing output
(defn pro-output [output]
  (-> output
      (clojure.string/replace #"(?i)@(\w+)@" "$1")
      (clojure.string/replace #"\( " "(")
      (clojure.string/replace #"\[ " "[")
      (clojure.string/replace #"\$ " "$")
      (clojure.string/replace #"\@ " "@")
      (clojure.string/replace #"\s([,.?!)\]])" "$1")
      (clojure.string/trim)))

;; Function to capitalizing the first word
(defn capitalize-character [text]
  (clojure.string/replace text #"^.|\.\s\w" clojure.string/upper-case))

;; Function for decompressing words
(defn words-decompressions [words frequency-vector]
  (clojure.string/join " " (map #(words-decompression % frequency-vector) words)))

;; Function for file decompression
(defn file-decompression []
  (let [frequency-file "frequency.txt"
        frequencies (read-frequencies frequency-file)
        frequency-vector (vec (distinct frequencies))]
    (print "Please enter the name of the file you want to uncompress: ")
    (flush)
    (let [filename (read-line)
          output (atom "")]
      (if (.exists (io/file filename))
        (with-open [reader (io/reader filename)]
          (let [words (string/split (slurp reader) #" ")
                u-words (words-decompressions words frequency-vector)]
            (reset! output u-words)))
        (println "Oops: specified file does not exist"))
      (let [r-output (pro-output @output)
            c-output (capitalize-character r-output)]
		(println)
        (println c-output)))))

