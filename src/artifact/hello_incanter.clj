(use '(incanter core stats charts))

(defn -main 
  "simple function displays normal distribution histogram using incanter"
  [& args]
  (view (histogram (sample-normal 1000))))

(-main)

