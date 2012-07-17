(use '(incanter core stats charts io))

;Sample data provided by incanter
(def plot-data (read-dataset
                 "https://raw.github.com/liebke/incanter/master/data/iris.dat"
                 :delim \space
                 :header true))

(def plot (scatter-plot
            (sel plot-data :cols 0)
            (sel plot-data :cols 1)
            :x-label "Sepal Length"
            :y-label "Sepal Width"
            :group-by (sel plot-data :cls 4)))


