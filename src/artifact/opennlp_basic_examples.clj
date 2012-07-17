;; Credit: Lee Hinman (dakrone on github)
;; https://github.com/dakrone/clojure-opennlp

(use 'clojure.pprint) ; just for this documentation
(use 'opennlp.nlp)
(use 'opennlp.treebank) ; treebank chunking, parsing and linking lives here

(def get-sentences (make-sentence-detector "data/en-sent.bin"))
(def tokenize (make-tokenizer "data/en-token.bin"))
;(def detokenize (make-detokenizer "data/english-detokenizer.xml")) ;no file to be found at http://opennlp.sourceforge.net/models-1.5/
(def pos-tag (make-pos-tagger "data/en-pos-maxent.bin"))
(def name-find (make-name-finder "data/en-ner-person.bin"))
(def chunker (make-treebank-chunker "data/en-chunker.bin"))


(def sample-sentences "First sentence. Second sentence determined by periond and space. Third sentence start but can the Function deal with ... Followed by a space and capitalized letter (third sentence end)? His name is John and mine is Lee John-Smith, while his is Gabobi Al-Abdul Jihamad and hers is Leea John")

(def parsed-sample-sentences (get-sentences sample-sentences))
(pprint parsed-sample-sentences)

(def tokenized-sample-sentences (tokenize sample-sentences))
(pprint tokenized-sample-sentences)

(def tagged-tokenized-sample-sentences (pos-tag tokenized-sample-sentences))
(pprint tagged-tokenized-sample-sentences)

(name-find tokenized-sample-sentences) ;does not do so well: missed Gabobi Al-Abdul Jihamad

;; Treebank chunking utilized here
(pprint (chunker tagged-tokenized-sample-sentences))
;;just the phrases
(phrases (chunker tagged-tokenized-sample-sentences))
;;just the strings
(phrase-strings (chunker tagged-tokenized-sample-sentences))

;; Get the probabilities
(meta parsed-sample-sentences)

