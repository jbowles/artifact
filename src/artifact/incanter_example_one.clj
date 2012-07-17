(use '(incanter core stats charts io datasets))
(defn -main [& args]
  (view (histogram (sample-normal 1000))))

(-main)

