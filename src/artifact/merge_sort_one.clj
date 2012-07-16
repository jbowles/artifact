(defn merge* [left right]
  (cond (nil? left) right
        (nil? right) left
        true (let [[l & *left] left
                   [r & *right] right]
               (if (<= l r) (cons l (merge* *left right))
                            (cons r (merge* left *right))))))

(defn merge-sort [L]
  (let [[l & *L] L]
    (if (nil? *L)
      L
      (let [[left right] (split-at (/ (count L) 2) L)]
        (merge* (merge-sort left) (merge-sort right))))))


(def unsorted-data [10 4 2 345 34 21 23 45 6 54 7 2 9 78 6789 7654 345678 7 6 5456 32 4567 890 36 478 5764 669 484 39 959])
(merge-sort unsorted-data)

