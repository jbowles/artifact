;; Credit: David Edgar Liebke
;; http://data-sorcery.org/2009/10/18/lda-qda/
;;
;; LDA = Linear Discriminant Analysis
;; QDA = Quadratic Discriminant Analysis
;; Chapter 4 of EoSL [Elements of Statistical Learning](http://www-stat.stanford.edu/~tibs/ElemStatLearn/)

;; The task is to classify each of the N observations as one of the (K=11) vowel sounds using the (p=10) predictors.


(use '(incanter core stats charts io datasets))

(def training (to-matrix
                (read-dataset "http://bit.ly/464h4h"
                              :header true)))

;training
;(class training) ; incanter.Matrix

(def testing (to-matrix
                (read-dataset "http://bit.ly/1btCei"
                              :header true)))
;testing
;(class testing) ;incanter.Matrix

(def K 11)
(def p 10)
(def N (nrow training))
;N 528
;(def group-counts (map nrow (group-by training 1)))
;(def group-counts (map nrow training))
(def group-counts (map nrow (group-by nrow training)))
;(class group-counts)
;group-counts
(def N-mat (matrix [N]))
(class N-mat)
(def N-vec [N])
(def prior-probs (div group-counts N-vec))
;(def A (matrix [[1 2 3] [3 2 4] [5 6 7]]))
;(div A A)
prior-probs
(def cluster-centroids
  (matrix
    (for [x_k (:group-by training (:cols (range 2 12)))]
      (map mean (trans x_k)))))

(def cluster-cov-mat
  (let [groups (:group-by training (:cols (range 2 12)))]
    (plus
            (map (fn [group centroid]
                   (reduce (plus
                           (map #(:div
                                   (mmult (trans (minus % centroid))
                                          (minus % centroid))
                                   (- N K))
                                group))))
                 groups cluster-centroids group-counts))))

(def inv-cluster-cov-mat (:solve cluster-cov-mat))

(defn ldf [x Sigma-inv mu_k pi_k]
  (+ (mmult x Sigma-inv (trans mu_k))
     (- (mult 1/2 (mmult mu_k Sigma-inv (trans mu_k))))
     (log pi_k)))

(defn calculate-scores 
  ([data inv-cov-mat centroids priors]
   (matrix
     (pmap (fn [row]
             (pmap (partial ldf row inv-cov-mat)
                   centroids
                   priors))
           (sel data :cols (range 2 12))))))

(def training-lda-scores
  (calculate-scores training
                    inv-cluster-cov-mat
                    cluster-centroids
                    prior-probs))

(defn max-index
  ([x]
   (let [max-x (reduce max x)
         n (length x)]
     (loop [i 0]
       (if (= (nth x i) max-x)
         i
         (recur (inc i)))))))

;(map max-index training-lda-scores)

(defn error-rate [data scores]
  (/ (sum (map #(if (= %1 %2) 0 1)
               (sel data :cols 1)
               (plus 1 (map max-index scores))))
     (nrow data)))

(error-rate training training-lda-scores)

(def testing-lda-scores
  (calculate-scores testing
                    inv-cluster-cov-mat
                    cluster-centroids
                    prior-probs))

(error-rate testing testing-lda-scores)


