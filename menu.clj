(ns menu
  (:require [compress :as comp]
			[clojure.java.io :as io]
			[clojure.string :as string]))

;; Function to print file list
(defn print-filelist []
  (println "\nFile list:")
  (letfn [(print-files [file-names]
            (when-not (empty? file-names)
              (let [file (first file-names)]
                (when (.isFile file)
                  (println (str "* ./" (.getName file))))
                (recur (rest file-names)))))]
    (print-files (file-seq (io/file "."))))
  (println))


;; Function to print file contents
(defn print-file-contents []
  (print "\nPlease enter a file name => ")
  (flush)
  (let [filename (read-line)]
    (if (.exists (io/file filename))
      (do
        (println )
        (with-open [reader (io/reader filename)]
		  (println)
          (println (string/join (line-seq reader)))))
      (println "Oops: specified file does not exist"))
    (println))) 

;; Function to recursively print main menu  
(defn main-menu []
  (println "\n*** Compression Menu ***")
  (println "________________________\n")
  (println "1. Display list of files")
  (println "2. Display file contents")
  (println "3. Compress a file")
  (println "4. Uncompress a file")
  (println "5. Exit\n")
  (print "Enter an option? ")
  (flush)
  (let [choice (read-line)]
    (cond
      (= choice "1") (print-filelist)
      (= choice "2") (print-file-contents)
      (= choice "3") (comp/file-compression)
      (= choice "4") (comp/file-decompression)
      (= choice "5") ((println "Exiting!!! Have a nice day") (System/exit 0))
      :else (println "Invalid choice. Please try again.")))

  (main-menu))
  
(main-menu)
