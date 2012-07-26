(use '(incanter core io stats))

;; define vectore
(def A (matrix [1 2 3 4 5 6]))
A

;; select row
(sel A :rows 3)

;; define 3 row matrix
(def B (matrix [[1 0] [2 5] [3 1]]))
B

;; Scalar math
(plus 5 B)
(minus 5 B)
(mult 5 B)
(div B 5)

;; Matrix math (need same dimensions)
(def C (matrix [[1 8] [12 5] [4 10]]))
C
(plus B C)
(minus B C)
(mult B C)

;; an Excercise




