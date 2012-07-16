;;;; Credit: Maik Schunemann
;;;; http://kimavcrp.blogspot.com.au/2012/05/porting-paip-to-clojure-chapter-2_29.html
;;;; Original Credit: Peter Norvig 'Paradigms of Artificial Intelligence Programming'


;;; Static (naive) Model for Sentence Generation

;; Common Lisp
;(defun sentence ()    (append (noun-phrase) (verb-phrase)))
;(defun noun-phrase () (append (Article) (Noun)))
;(defun verb-phrase () (append (Verb) (noun-phrase)))
;(defun Article ()     (one-of '(the a)))
;(defun Noun ()        (one-of '(man ball woman table)))
;(defun Verb ()        (one-of '(hit took saw liked)))

;; Clojure
(declare sentence noun-phrase verb-phrase article noun verb one-of)

(defn one-of [coll]
  (if (seq coll)
    [(rand-nth coll)]))

(defn sentence[] (concat (noun-phrase) (verb-phrase)))
(defn noun-phrase[] (concat (article) (noun)))
(defn verb-phrase[] (concat (verb) (noun-phrase)))
(defn article[] (one-of ["the" "a"]))
(defn noun[] (one-of ["man" "ball" "woman" "table"]))
(defn verb[] (one-of ["hit" "took" "saw" "liked"]))

(take 10 (repeatedly sentence))

;;; Data-driven Model for Sentence Generation
;;; see rubygems treetop and/or citrus for similar
;;; This can also act a validation system with sets of validations per rule-set/set-inclusion
;; Common Lisp
; (defparameter *simple-grammar*
; '((sentence -> (noun-phrase verb-phrase))
;  (noun-phrase -> (Article Noun))
;  (verb-phrase -> (Verb noun-phrase))
;  (Article -> the a)
;  (Noun -> man ball woman table)
;   (Verb -> hit took saw liked))
;"A grammar for a trivial subset of English.")

;;; TODO: 
(def simple-grammar
  {:sentence [:noun-phrase :verb-phrase]
   :noun-phrase [:Article :Noun]
   :verb-phrase [:Verb :noun-phrase]
   :Article #{"the" "a"}
   :Noun #{"man" "ball" "woman" "table"}
   :Verb #{"hit" "took" "saw" "liked" }})

(def big-grammar
  {:sentence [:noun-phrase :verb-phrase]
   :noun-phrase #{[:Article :Adj* :Noun :PP*] :Name :Pronoun}
   :verb-phrase [:Verb :noun-phrase :PP*]
   :PP* #{[] [:PP :PP*]}
   :Adj* #{[] [:Adj :Adj*]}
   :Prep #{"to" "in" "by" "with" "on"}
   :Adj #{"big" "little" "blue" "green" "unseemly"}
   :Article #{"the" "a"}
   :Name #{"Pat" "Kim" "Lee" "Terry" "Robin"}
   :Noun #{"man" "ball" "woman" "table"}
   :Verb #{"hit" "took" "saw" "liked" }
   :Pronoun #{"he" "she" "it" "these" "those" "that"}})


;; dynamic global reference for our grammars
(def ^:dynamic *s-grammar* simple-grammar)
(def ^:dynamic *b-grammar* big-grammar)

(defn generate [phrase]
  (cond
    (get *grammar* phrase) (generate (get *grammar* phrase))
    (sequential? phrase) (mapcat generate phrase)
    (set? phrase) (generate (rand-nth (seq phrase)))
    :else [phrase]))

(generate *grammar*)
(generate :noun-phrase)
(generate :sentence)
(take 10 (repeatedly #(generate :sentence)))

;; Generate the full phrase structure, use a vector to house sentence instead of the original tutorial which used a list
(defn generate-all [phrase]
  (cond (get *grammar* phrase) (vector phrase (generate-all (get *grammar* phrase)))
             (sequential? phrase) (mapcat generate-all phrase)
             (set? phrase) (generate-all (rand-nth (seq phrase)))
             :else phrase))

(generate-all :sentence)
(def random-sent (take 10 (repeatedly #(generate-all :sentence))))
(class (first random-sent))
(last random-sent)
(count random-sent)
(generate-all :noun-phrase)

