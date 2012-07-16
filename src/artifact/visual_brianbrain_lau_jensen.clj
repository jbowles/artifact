;; Credit: lau jensen
;; http://www.bestinclass.dk/index.clj/2009/10/brians-functional-brain.html

(import 
  '(javax.swing JFrame JPanel)
  '(java.awt Color Graphics)
  '(java.awt.image BufferedImage))

; 90x90 dimension board
(def dim-board [90 90])
; 600x600 dimension screen
(def dim-screen [600 600])
(def dim-scale (vec (map / dim-screen dim-board)))

;; helper to force map again
(defn fmap [f coll] (doall (map f coll)))

(defn render-cell [#^Graphics g cell]
  (let [[state x y] cell
        x (inc (* x (dim-scale 0)))
        y (inc (* y (dim-scale 1)))]
    (doto g
      (.setColor (if (= state :dying) Color/GRAY Color/WHITE))
      (.fillRect x y (dec (dim-scale 0)) (dec (dim-scale 1))))))

(defn render [g img bg stage]
  (.setColor bg Color/BLACK)
  (.fillRect bg 0 0 (dim-screen 0) (dim-screen 1))
  (fmap (fn [col]
          (fmap #(when (not= :off (% 0))
                   (render-cell bg %)) col)) stage)
  (.drawImage g img 0 0 nil))


; walk board, x y values, randomly set state on/off with 50 threshold
(def board
  (for [x (range (dim-board 0))]
    (for [y (range (dim-board 1))]
      [(if (< 50 (rand-int 100)) :on :off) x y])))

(defn active-neighbors [above [left _ right] below]
  (count
    (filter #(= :on (% 0))
            (concat above [left right] below))))

(defn torus-window [coll]
  (partition 3 1 (concat [(last coll)] coll [(first coll)])))

;(torus-window [123])

(defn rules [above current below]
  (let [[self x y] (second current)]
    (cond
      (= :on self)     [:dying x y]
      (= :dying self)  [:off x y]
      (= 2 (active-neighbors above current below)) [:on x y]
      :else [:off x y])))

(defn step [board]
  (doall
    (pmap (fn [window]
            (apply #(doall (apply map rules %&))
                   (doall (map torus-window window))))
          (torus-window board))))
;; static board with this function. Use function above
;(defn step [board]
;  (map (fn [window]
;         (apply #(apply map rules %&) (map torus-window window)))
;       (torus-window board)))

;(map inc (range 10))           ;; fully lazy
;(doall (map inc (range 10)))   ;; fully eager

;(map #(apply * %) (range 100000))  ;; sequential computation
;(pmap #(apply * %) (range 100000)) ;; parallelized computation

(defn activity-loop [surface stage]
  (while true
    (swap! stage step)
    (.repaint surface)))

(let [stage (atom board)
      frame (JFrame.)
      img   (BufferedImage. (dim-screen 0) (dim-screen 1) (BufferedImage/TYPE_INT_ARGB))
      bg    (.getGraphics img)
      panel (doto (proxy [JPanel] [] (paint [g] (render g img bg @stage))))]
  (doto frame (.add panel) .pack (.setSize (dim-screen 0) (dim-screen 1)) .show
    (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE))
  (future (activity-loop panel stage)))

