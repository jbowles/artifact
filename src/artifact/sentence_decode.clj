;; Credit: Medelis
;; https://github.com/zmedelis/ai-class/blob/master/src/code/nlp.clj

(ns artifact.sentence_decode
  (require [clojure.contrib.duck-streams :as io]
           [clojure.string :as s]))

(def az "abcdefghijklmnopqrstuvwxyz")
(def txt (s/lower-case "Esp qtcde nzyqpcpynp zy esp ezatn zq Lcetqtntlw Tyepwwtrpynp hld spwo le Olcexzfes Nzwwprp ty estd jplc"))
(def dic (io/read-lines "/usr/share/dict/words"))

(defn rotate
  ([c step] (nth az (mod (+ step (.indexOf az (int c))) 26)))
  ([c] (rotate c 1)))


