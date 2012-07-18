;; Credit: Zygimantas Medelis
;; https://github.com/zmedelis/ai-class/blob/master/src/code/lr.clj

(ns artificat.incanter_example_three
  (use [incanter core charts]))

;; f(x) = x * w1 + w0
(defn f[w0 w1] (fn [x] (+ (* w1 x) w0)))

;; calculate w0 w1 minimizing quadratic loss
;; Medelis is referring to the Quadratic Loss video from AI class by Norbig and Thrun:
;; http://www.youtube.com/watch?v=wUFYzzrd6TQ
(defn w [x y]
  (let [m (count x)
        sumx (reduce + x)
        sumy (reduce + y)
        w1 (/ (- (* m (reduce + (map #(* %1 %2) x y)))
                 (* sumx sumy))
              (- (* m (reduce + (map #(* % %) x)))
                 (* sumx sumy)))
        w0 (- (* (/ 1 m) sumy)
              (* (/ w1 m) sumx))]
    [w0 w1]))

;; plot linear regression along initial points
;; x -x point vector
;; y - y point vector
(defn plot [x y]
  (let [[w0 w1] (w x y)
        func (f w0 w1)
        fplot (function-plot func
                             (-> x sort first) (-> x sort last)
                             :x-label "x-axis" :y-label "y-axis" :legend true
                             :series-label (str "w0: " w0 "w1: " w1))]
    (view (add-points fplot x y))))

(plot [0 1 2 3 4 5 6] [4 5 3 6 7 8 9])
