(ns filereader
  (:import (java.io BufferedReader FileReader)))
(use 'clojure.pprint) ; just for this documentation
(use 'opennlp.nlp)
(use 'opennlp.treebank) ; treebank chunking, parsing and linking lives here

(def tokenize (make-tokenizer "data/en-token.bin"))
(def get-sentences (make-sentence-detector "data/en-sent.bin"))


(defn proc-file [file-name]
  (with-open [rdr (BufferedReader. (FileReader. file-name))]
    (doseq [line (line-seq rdr)] (println line))))
;(proc-file "data/lslog.txt")


(defn process-file [file-name line-func line-acc]
  (with-open [rdr (BufferedReader. (FileReader. file-name))]
    (reduce line-func line-acc (line-seq rdr))))

(defn proc-line [acc line]
  (+ acc 1))

(defn process-line [acc line]
  (reduce #(assoc %1 %2 (+ (get %1 %2 0) 1)) acc (.split line "\n")))

;;count lines
;(prn (process-file "data/small-log.txt" proc-line 0))

;; create a hash map then iterate through and tokenize
;(def mapped (process-file "data/tiny-log.txt" process-line (hash-map)))
;(doseq [entry (keys mapped)]
;   (println (tokenize entry)))

;; create a hash map
;(process-file "data/tiny-log.txt" process-line (hash-map))

;(str (proc-file "data/tiny-log.txt"))

;(def parse-sent (get-sentences "This is a sentence. This is another"))
;(pprint parse-sent)


