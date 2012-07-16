;; Quicksort
(defn qsort [L]
  (if (empty? L)
    '()
    (let [[pivot & L2] L]
      (lazy-cat (qsort (for [y L2 :when (< y pivot)] y))
                (list pivot)
                (qsort (for [y L2 :when (>= y pivot)] y ))))))

(qsort [1000 3456 57464 36384 23648 4759 38 10374 3047 1 3946 39 540 27 3 6 6 3 9 8 67 4])

